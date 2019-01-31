package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.criteria.GarageCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Disponibility;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Notification;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.enumeration.ENotification;
import com.academico.garagem.model.service.AdvertisementService;
import com.academico.garagem.model.service.DisponibilityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdvertisementController {

    public class DisponibilityList {

        private List<Disponibility> disponibilityList;

        public List<Disponibility> getDisponibilityList() {
            return disponibilityList;
        }

        public void setDisponibilityList(List<Disponibility> disponibilityList) {
            this.disponibilityList = disponibilityList;
        }

    }

    @RequestMapping(value = "/advertisement", method = RequestMethod.GET)
    public ModelAndView advertisementGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("advertisement/list");
        Notification notification = new Notification();

        try {
            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(AdvertisementCriteria.GARAGE_ID_USER_ID_EQ, user.getId());
            List<Advertisement> advertisementList = ServiceLocator.getInstance().getAdvertisementService().findByCriteria(criteria, 0, 0);

            mv.addObject("advertisementList", advertisementList);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            mv.addObject("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/advertisement/list", method = RequestMethod.GET)
    public ModelAndView advertisementListGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("advertisement/list");
        Notification notification = new Notification();

        try {
            List<Advertisement> advertisementList = ServiceLocator.getInstance().getAdvertisementService().findAll();
            mv.addObject("advertisementList", advertisementList);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            mv.addObject("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/advertisement/new", method = RequestMethod.GET)
    public ModelAndView advertisementNewGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("advertisement/index");
        Notification notification = new Notification();

        try {
            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(GarageCriteria.USER_ID_EQ, user.getId());
            List<Garage> garageList = ServiceLocator.getInstance().getGarageService().findByCriteria(criteria, 0, 0);

            mv.addObject("garageList", garageList);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            mv.addObject("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/advertisement/new", method = RequestMethod.POST)
    public ModelAndView advertisementNewPOST(RedirectAttributes redir, HttpSession session,
            String title, String description, double price, String currency, Integer garageId,
            @ModelAttribute("disponibilityList") DisponibilityList disponibilityList) {
        ModelAndView mv = new ModelAndView("redirect:/advertisement");
        Notification notification = new Notification();

        try {
            List<Disponibility> disponibilitys = disponibilityList.getDisponibilityList();

            Map<String, Object> fields = new HashMap<>();

            AdvertisementService service = ServiceLocator.getInstance().getAdvertisementService();
            Map<String, String> errors = new HashMap<>(); //service.validate(fields);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (errors.isEmpty() && user != null) {

                Advertisement advertisement = new Advertisement();
                advertisement.setTitle(title);
                advertisement.setDescription(description);
                advertisement.setPrice(price);
                advertisement.setCurrency(currency);

                advertisement.setGarageId(new Garage(garageId));

                service.create(advertisement);

                DisponibilityService disponivilityService = ServiceLocator.getInstance().getDisponibilityService();
                if (disponibilitys != null && disponibilitys.size() > 0) {
                    for (Disponibility disponibility : disponibilitys) {
                        disponibility.setAdvertisementId(advertisement);
                        disponivilityService.create(disponibility);
                    }
                }

                notification.setType(ENotification.SUCCESS);
                notification.setText("Anúncio cadastrado com sucesso");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
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

    @RequestMapping(value = "/advertisement/edit/{id}", method = RequestMethod.GET)
    public ModelAndView advertisementEditGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("advertisement/index");

        try {
            AdvertisementService service = ServiceLocator.getInstance().getAdvertisementService();
            Advertisement advertisement = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (advertisement != null && user.equals(advertisement.getGarageId().getUserId())) {
                Map<Integer, Object> criteria = new HashMap<>();
                criteria.put(GarageCriteria.USER_ID_EQ, user.getId());
                List<Garage> garageList = ServiceLocator.getInstance().getGarageService().findByCriteria(criteria, 0, 0);

                mv.addObject("garageList", garageList);
                mv.addObject("advertisement", advertisement);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/advertisement/edit/{id}", method = RequestMethod.POST)
    public ModelAndView advertisementEditPOST(@PathVariable Integer id, RedirectAttributes redir, HttpSession session,
            String title, String description, double price, String currency, Integer garageId,
            @ModelAttribute("disponibilityList") DisponibilityList disponibilityList) {
        ModelAndView mv = new ModelAndView("redirect:/advertisement/edit/" + id);
        Notification notification = new Notification();

        try {
            List<Disponibility> disponibilitys = disponibilityList.getDisponibilityList();

            AdvertisementService service = ServiceLocator.getInstance().getAdvertisementService();
            Advertisement advertisement = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(advertisement.getGarageId().getUserId())) {

                advertisement.setTitle(title);
                advertisement.setDescription(description);
                advertisement.setPrice(price);
                advertisement.setCurrency(currency);

                advertisement.setGarageId(new Garage(garageId));

                service.edit(advertisement);

                DisponibilityService disponibilityService = ServiceLocator.getInstance().getDisponibilityService();
                disponibilityService.destroyByAdvertisementId(id);
                if (disponibilitys != null && disponibilitys.size() > 0) {
                    for (Disponibility disponibility : disponibilitys) {
                        if (disponibility.getDay() > 0) {
                            disponibility.setAdvertisementId(advertisement);
                            disponibilityService.create(disponibility);
                        }
                    }
                }

                notification.setType(ENotification.SUCCESS);
                notification.setText("Anúncio editado com sucesso");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
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

    @RequestMapping(value = "/advertisement/delete/{id}", method = RequestMethod.GET)
    public ModelAndView advertisementDeleteGET(@PathVariable Integer id, RedirectAttributes redir, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("redirect:/advertisement");
        Notification notification = new Notification();

        try {
            AdvertisementService service = ServiceLocator.getInstance().getAdvertisementService();
            Advertisement advertisement = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(advertisement.getGarageId().getUserId())) {
                service.destroy(id);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Anúncio apagado com sucesso");
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
            ex.printStackTrace();
        }

        return mv;
    }

}
