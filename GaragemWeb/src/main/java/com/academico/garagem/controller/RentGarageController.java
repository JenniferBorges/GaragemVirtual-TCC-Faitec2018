package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.RentGarageCriteria;
import com.academico.garagem.model.criteria.VehicleCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Notification;
import com.academico.garagem.model.entity.Rating;
import com.academico.garagem.model.entity.RatingPK;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.enumeration.ENotification;
import com.academico.garagem.model.service.AdvertisementService;
import com.academico.garagem.model.service.RatingService;
import com.academico.garagem.model.service.RentGarageService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RentGarageController {

    @RequestMapping(value = "/rent-garage/request", method = RequestMethod.GET)
    public ModelAndView requestGarageRequestGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("rent-garage/list");
        Notification notification = new Notification();

        try {
            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(RentGarageCriteria.VEHICLE_ID_USER_ID_EQ, user.getId());
            List<RentGarage> rentGarageList = ServiceLocator.getInstance().getRentGarageService().findByCriteria(criteria, 0, 0);

            mv.addObject("rentGarageList", rentGarageList);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            mv.addObject("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/received", method = RequestMethod.GET)
    public ModelAndView requestGarageReceivedGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("rent-garage/list");
        Notification notification = new Notification();

        try {
            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(RentGarageCriteria.ADVERTISEMENT_ID_GARAGE_ID_USER_ID_EQ, user.getId());
            List<RentGarage> rentGarageList = ServiceLocator.getInstance().getRentGarageService().findByCriteria(criteria, 0, 0);

            mv.addObject("rentGarageList", rentGarageList);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            mv.addObject("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/{advertisementId}", method = RequestMethod.GET)
    public ModelAndView rentGarageGET(@PathVariable Integer advertisementId, HttpSession session, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("rent-garage/index");

        AdvertisementService service = ServiceLocator.getInstance().getAdvertisementService();
        Advertisement advertisement = service.findEntity(advertisementId);

        User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

        if (user == null) {
            mv = new ModelAndView("redirect:/login");
        } else if (user.equals(advertisement.getGarageId().getUserId())) {
            mv = new ModelAndView("redirect:/advertisement/edit/" + advertisementId);
        } else {
            mv.addObject("advertisement", advertisement);

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(VehicleCriteria.USER_ID_EQ, user.getId());
            List<Vehicle> vehicleList = ServiceLocator.getInstance().getVehicleService().findByCriteria(criteria, 0, 0);
            mv.addObject("vehicleList", vehicleList);
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/{advertisementId}", method = RequestMethod.POST)
    public ModelAndView rentGaragePOST(@PathVariable Integer advertisementId, RedirectAttributes redir, HttpSession session,
            Integer vehicleId, @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date dateTime) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/rent-garage/request");
        Notification notification = new Notification();

        try {
            Map<String, Object> fields = new HashMap<>();
            fields.put("advertisement", advertisementId);
            fields.put("vehicle", vehicleId);
            fields.put("dateTime", dateTime);

            RentGarageService service = ServiceLocator.getInstance().getRentGarageService();
            Map<String, String> errors = service.validate(fields);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);
            if (errors.isEmpty() && user != null) {
                RentGarage rentGarage = new RentGarage();
                rentGarage.setDateTime(dateTime);

                rentGarage.setAdvertisementId(new Advertisement(advertisementId));
                rentGarage.setVehicleId(new Vehicle(vehicleId));

                service.create(rentGarage);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Aluguel solicitado com sucesso");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else {
                mv = new ModelAndView("redirect:/rent-garage/" + advertisementId);
                notification.setType(ENotification.ERROR);
                notification.setText("Erro ao solicitar garagem");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
                redir.addFlashAttribute("errors", errors);
                redir.addFlashAttribute("rentGarage", fields);
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/confirm/{id}", method = RequestMethod.GET)
    public ModelAndView rentGarageConfirmGET(@PathVariable Integer id, RedirectAttributes redir, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/rent-garage/received");
        Notification notification = new Notification();

        try {
            RentGarageService service = ServiceLocator.getInstance().getRentGarageService();

            RentGarage rentGarage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(rentGarage.getAdvertisementId().getGarageId().getUserId())) {
                rentGarage.setIsAuth(true);

                service.edit(rentGarage);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Solicitação confirmada com successo!");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/cancel/{id}", method = RequestMethod.GET)
    public ModelAndView rentGarageCancelGET(@PathVariable Integer id, RedirectAttributes redir, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/rent-garage/received");
        Notification notification = new Notification();

        try {
            RentGarageService service = ServiceLocator.getInstance().getRentGarageService();

            RentGarage rentGarage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(rentGarage.getAdvertisementId().getGarageId().getUserId())) {
                rentGarage.setIsAuth(false);

                service.edit(rentGarage);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Solicitação cancelada com successo!");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else if (user.equals(rentGarage.getVehicleId().getUserId())) {
                service.destroy(id);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Solicitação apagada com successo!");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);

                mv = new ModelAndView("redirect:/rent-garage/request");
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/start/{id}", method = RequestMethod.GET)
    public ModelAndView rentGarageStartGET(@PathVariable Integer id, RedirectAttributes redir, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/rent-garage/received");
        Notification notification = new Notification();

        try {
            RentGarageService service = ServiceLocator.getInstance().getRentGarageService();

            RentGarage rentGarage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(rentGarage.getAdvertisementId().getGarageId().getUserId())) {
                if (rentGarage.getIsAuth() && rentGarage.getFinalDateTime() == null) {
                    rentGarage.setInitialDateTime(new Date());

                    service.edit(rentGarage);

                    notification.setType(ENotification.SUCCESS);
                    notification.setText("Cobrança iniciada com successo!");
                    notification.setTime(5000);
                    redir.addFlashAttribute("notification", notification);
                } else if (rentGarage.getFinalDateTime() != null) {
                    notification.setType(ENotification.ERROR);
                    notification.setText("Não é possivel iniciar uma cobrança já finalizada!");
                    notification.setTime(5000);
                    redir.addFlashAttribute("notification", notification);
                } else {
                    notification.setType(ENotification.ERROR);
                    notification.setText("Confirme a solicitação antes de iniciar a cobrança!");
                    notification.setTime(5000);
                    redir.addFlashAttribute("notification", notification);
                }
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/finish/{id}", method = RequestMethod.GET)
    public ModelAndView rentGarageFinishGET(@PathVariable Integer id, RedirectAttributes redir, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/rent-garage/received");
        Notification notification = new Notification();

        try {
            RentGarageService service = ServiceLocator.getInstance().getRentGarageService();

            RentGarage rentGarage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(rentGarage.getAdvertisementId().getGarageId().getUserId())) {
                if (rentGarage.getInitialDateTime() != null) {
                    rentGarage.setFinalDateTime(new Date());

                    service.edit(rentGarage);

                    notification.setType(ENotification.SUCCESS);
                    notification.setText("Cobrança finalizado com successo!");
                    notification.setTime(5000);
                    redir.addFlashAttribute("notification", notification);
                } else {
                    notification.setType(ENotification.ERROR);
                    notification.setText("Inicie a conbrança antes de finalizar!");
                    notification.setTime(5000);
                    redir.addFlashAttribute("notification", notification);
                }
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/rent-garage/rating/{id}", method = RequestMethod.POST)
    public ModelAndView rentGarageRatingGET(@PathVariable Integer id, Integer rating, String message, RedirectAttributes redir, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView();
        Notification notification = new Notification();

        try {
            RatingService service = ServiceLocator.getInstance().getRatingService();

            RentGarage rentGarage = ServiceLocator.getInstance().getRentGarageService().findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(rentGarage.getVehicleId().getUserId()) || user.equals(rentGarage.getAdvertisementId().getGarageId().getUserId())) {
                Rating r = new Rating();
                r.setRating(rating);
                r.setMessage(message);
                r.setRatingPK(new RatingPK(id, user.getId()));
                r.setRentGarage(rentGarage);

                service.create(r);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Avaliação enviada com successo!");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);

                if (user.equals(rentGarage.getVehicleId().getUserId())) {
                    mv = new ModelAndView("redirect:/rent-garage/request");
                } else if (user.equals(rentGarage.getAdvertisementId().getGarageId().getUserId())) {
                    mv = new ModelAndView("redirect:/rent-garage/received");
                }
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

}
