package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.VehicleCriteria;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.Notification;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.enumeration.ENotification;
import com.academico.garagem.model.service.VehicleService;
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
public class VehicleController {

    public class ImageList {

        private List<Image> imageList;

        public List<Image> getImageList() {
            return imageList;
        }

        public void setImageList(List<Image> imageList) {
            this.imageList = imageList;
        }

    }

    @RequestMapping(value = "/vehicle", method = RequestMethod.GET)
    public ModelAndView vehicleGET(HttpSession session) {
        ModelAndView mv = new ModelAndView("vehicle/list");
        Notification notification = new Notification();

        try {
            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(VehicleCriteria.USER_ID_EQ, user.getId());
            List<Vehicle> vehicleList = ServiceLocator.getInstance().getVehicleService().findByCriteria(criteria, 0, 0);

            mv.addObject("vehicleList", vehicleList);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            mv.addObject("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/vehicle/view-vehicle/{id}", method = RequestMethod.GET)
    public ModelAndView vehicleViewVehicleGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("vehicle/view");

        try {
            VehicleService service = ServiceLocator.getInstance().getVehicleService();
            Vehicle vehicle = service.findEntity(id);

            User _user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (vehicle != null) {
                if (vehicle.getUserId().equals(_user)) {
                    mv = new ModelAndView("redirect:/vehicle/edit/" + vehicle.getId());
                }
                mv.addObject("vehicle", vehicle);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/vehicle/new", method = RequestMethod.GET)
    public ModelAndView vehicleNewGET() {
        ModelAndView mv = new ModelAndView("vehicle/index");

        return mv;
    }

    @RequestMapping(value = "/vehicle/new", method = RequestMethod.POST)
    public ModelAndView vehicleNewPOST(RedirectAttributes redir, HttpSession session,
            String plate, String type, String manufacturer, String model, String year, String color, String chassis,
            @ModelAttribute("imageList") ImageList imageList) {
        ModelAndView mv = new ModelAndView("redirect:/vehicle");
        Notification notification = new Notification();

        try {
            List<Image> images = imageList.getImageList();

            Map<String, Object> fields = new HashMap<>();

            VehicleService service = ServiceLocator.getInstance().getVehicleService();
            Map<String, String> errors = new HashMap<>(); //service.validate(fields);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (errors.isEmpty() && user != null) {

                Vehicle vehicle = new Vehicle();
                vehicle.setPlate(plate);
                vehicle.setType(type);
                vehicle.setManufacturer(manufacturer);
                vehicle.setModel(model);
                vehicle.setYear(year);
                vehicle.setColor(color);
                vehicle.setChassis(chassis);
                vehicle.setUserId(user);

                service.create(vehicle);

                if (images != null && images.size() > 0) {
                    for (Image image : images) {
                        image.setVehicleId(vehicle);
                        ServiceLocator.getInstance().getImageService().edit(image);
                    }
                }

                notification.setType(ENotification.SUCCESS);
                notification.setText("Veículo cadastrado com sucesso");
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

    @RequestMapping(value = "/vehicle/edit/{id}", method = RequestMethod.GET)
    public ModelAndView vehicleEditGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("vehicle/index");

        try {
            VehicleService service = ServiceLocator.getInstance().getVehicleService();
            Vehicle vehicle = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (vehicle != null && user.equals(vehicle.getUserId())) {
                mv.addObject("vehicle", vehicle);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/vehicle/edit/{id}", method = RequestMethod.POST)
    public ModelAndView vehicleEditPOST(@PathVariable Integer id, RedirectAttributes redir, HttpSession session,
            String plate, String type, String manufacturer, String model, String year, String color, String chassis) {
        ModelAndView mv = new ModelAndView("redirect:/vehicle/edit/" + id);
        Notification notification = new Notification();

        try {
            Map<String, Object> fields = new HashMap<>();

            VehicleService service = ServiceLocator.getInstance().getVehicleService();
            Map<String, String> errors = new HashMap<>(); //service.validate(fields);

            Vehicle vehicle = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (errors.isEmpty() && user.equals(vehicle.getUserId())) {
                vehicle.setPlate(plate);
                vehicle.setType(type);
                vehicle.setManufacturer(manufacturer);
                vehicle.setModel(model);
                vehicle.setYear(year);
                vehicle.setColor(color);
                vehicle.setChassis(chassis);
                vehicle.setUserId(user);

                service.edit(vehicle);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Veículo editado com sucesso");
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

    @RequestMapping(value = "/vehicle/delete/{id}", method = RequestMethod.GET)
    public ModelAndView vehicleDeleteGET(@PathVariable Integer id, RedirectAttributes redir, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("redirect:/vehicle");
        Notification notification = new Notification();

        try {
            VehicleService service = ServiceLocator.getInstance().getVehicleService();
            Vehicle vehicle = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user.equals(vehicle.getUserId())) {
                service.destroy(id);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Veículo apagado com sucesso");
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
