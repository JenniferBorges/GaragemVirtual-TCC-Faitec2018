package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.GarageCriteria;
import com.academico.garagem.model.entity.Address;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.Notification;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.enumeration.ENotification;
import com.academico.garagem.model.service.GarageService;
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
public class GarageController {

    public class ImageList {

        private List<Image> imageList;

        public List<Image> getImageList() {
            return imageList;
        }

        public void setImageList(List<Image> imageList) {
            this.imageList = imageList;
        }

    }

    @RequestMapping(value = "/garage", method = RequestMethod.GET)
    public ModelAndView garageGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("garage/list");
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

    @RequestMapping(value = "/garage/view-garage/{id}", method = RequestMethod.GET)
    public ModelAndView garageViewGarageGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("garage/view");

        try {
            GarageService service = ServiceLocator.getInstance().getGarageService();
            Garage garage = service.findEntity(id);

            User _user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (garage != null) {
                if (garage.getUserId().equals(_user)) {
                    mv = new ModelAndView("redirect:/garage/edit/" + garage.getId());
                }
                mv.addObject("garage", garage);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/garage/new", method = RequestMethod.GET)
    public ModelAndView garageNewGET() {
        ModelAndView mv = new ModelAndView("garage/index");

        return mv;
    }

    @RequestMapping(value = "/garage/new", method = RequestMethod.POST)
    public ModelAndView garageNewPOST(RedirectAttributes redir, HttpSession session,
            double height, double width, double length, String access, boolean hasRoof, boolean hasCam, boolean hasIndemnity, boolean hasElectronicGate,
            String street, String number, String neighborhood, String city, String state, String zip, double longitude, double latitude,
            @ModelAttribute("imageList") ImageList imageList) {
        ModelAndView mv = new ModelAndView("redirect:/garage");
        Notification notification = new Notification();

        try {
            List<Image> images = imageList.getImageList();

            Map<String, Object> fields = new HashMap<>();

            GarageService service = ServiceLocator.getInstance().getGarageService();
            Map<String, String> errors = new HashMap<>(); //service.validate(fields);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (errors.isEmpty() && user != null) {

                Address address = new Address();
                address.setStreet(street);
                address.setNumber(number);
                address.setNeighborhood(neighborhood);
                address.setCity(city);
                address.setState(state);
                address.setZip(zip);
                address.setLongitude(longitude);
                address.setLatitude(latitude);

                ServiceLocator.getInstance().getAddressService().create(address);

                Garage garage = new Garage();
                garage.setHeight(height);
                garage.setWidth(width);
                garage.setLength(length);
                garage.setAccess(access);
                garage.setHasRoof(hasRoof);
                garage.setHasCam(hasCam);
                garage.setHasIndemnity(hasIndemnity);
                garage.setHasElectronicGate(hasElectronicGate);
                garage.setAddressId(address);
                garage.setUserId(user);

                service.create(garage);

                if (images != null && images.size() > 0) {
                    for (Image image : images) {
                        image.setGarageId(garage);
                        ServiceLocator.getInstance().getImageService().edit(image);
                    }
                }

                notification.setType(ENotification.SUCCESS);
                notification.setText("Garagem cadastrada com sucesso");
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

    @RequestMapping(value = "/garage/edit/{id}", method = RequestMethod.GET)
    public ModelAndView garageEditGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("garage/index");

        try {
            GarageService service = ServiceLocator.getInstance().getGarageService();
            Garage garage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (garage != null && user.equals(garage.getUserId())) {
                mv.addObject("garage", garage);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/garage/edit/{id}", method = RequestMethod.POST)
    public ModelAndView garageEditPOST(@PathVariable Integer id, RedirectAttributes redir, HttpSession session,
            double height, double width, double length, String access, boolean hasRoof, boolean hasCam, boolean hasIndemnity, boolean hasElectronicGate,
            String street, String number, String neighborhood, String city, String state, String zip, double longitude, double latitude) {
        ModelAndView mv = new ModelAndView("redirect:/garage/edit/" + id);
        Notification notification = new Notification();

        try {
            Map<String, Object> fields = new HashMap<>();

            GarageService service = ServiceLocator.getInstance().getGarageService();
            Map<String, String> errors = new HashMap<>(); //service.validate(fields);

            Garage garage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (errors.isEmpty() && user.equals(garage.getUserId())) {

                Address address = garage.getAddressId();
                address.setStreet(street);
                address.setNumber(number);
                address.setNeighborhood(neighborhood);
                address.setCity(city);
                address.setState(state);
                address.setZip(zip);
                address.setLongitude(longitude);
                address.setLatitude(latitude);

                ServiceLocator.getInstance().getAddressService().edit(address);

                garage.setHeight(height);
                garage.setWidth(width);
                garage.setLength(length);
                garage.setAccess(access);
                garage.setHasRoof(hasRoof);
                garage.setHasCam(hasCam);
                garage.setHasIndemnity(hasIndemnity);
                garage.setHasElectronicGate(hasElectronicGate);
                garage.setAddressId(address);
                garage.setUserId(user);

                service.edit(garage);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Garagem editada com sucesso");
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

    @RequestMapping(value = "/garage/delete/{id}", method = RequestMethod.GET)
    public ModelAndView garageDeleteGET(@PathVariable Integer id, RedirectAttributes redir, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("redirect:/garage");
        Notification notification = new Notification();

        try {
            GarageService service = ServiceLocator.getInstance().getGarageService();
            Garage garage = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(garage.getUserId())) {
                service.destroy(id);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Garagem apagada com sucesso");
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

}
