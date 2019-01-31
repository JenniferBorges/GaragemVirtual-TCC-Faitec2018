package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.criteria.UserCriteria;
import com.academico.garagem.model.criteria.VehicleCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminGET(HttpSession session, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("admin/home");

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(UserCriteria.IS_AUTH_EQ, false);
        List<User> userList = ServiceLocator.getInstance().getUserService().findByCriteria(criteria, 0, 0);

        criteria.clear();
        criteria.put(VehicleCriteria.IS_AUTH_EQ, false);
        List<Vehicle> vehicleList = ServiceLocator.getInstance().getVehicleService().findByCriteria(criteria, 0, 0);

        criteria.clear();
        criteria.put(AdvertisementCriteria.ACTIVE_EQ, false);
        List<Advertisement> advertisementList = ServiceLocator.getInstance().getAdvertisementService().findByCriteria(criteria, 0, 0);

        mv.addObject("userList", userList);
        mv.addObject("vehicleList", vehicleList);
        mv.addObject("advertisementList", advertisementList);

        return mv;
    }

    @RequestMapping(value = "/admin/advertisement", method = RequestMethod.GET)
    public ModelAndView adminAdvertisementGET(HttpSession session, HttpServletResponse response, Integer pag) throws Exception {
        ModelAndView mv = new ModelAndView("admin/advertisement");

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(AdvertisementCriteria.ACTIVE_EQ, false);
        List<Advertisement> advertisementList = ServiceLocator.getInstance().getAdvertisementService().findByCriteria(criteria, 0, 0);

        mv.addObject("advertisementList", advertisementList);

        return mv;
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public ModelAndView adminUsersGET(HttpSession session, HttpServletResponse response, Integer pag) throws Exception {
        ModelAndView mv = new ModelAndView("admin/user");

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(UserCriteria.IS_AUTH_EQ, false);
        List<User> userList = ServiceLocator.getInstance().getUserService().findByCriteria(criteria, 0, 0);

        mv.addObject("userList", userList);

        return mv;
    }

    @RequestMapping(value = "/admin/vehicle", method = RequestMethod.GET)
    public ModelAndView adminVehicleGET(HttpSession session, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("admin/vehicle");

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(VehicleCriteria.IS_AUTH_EQ, false);
        List<Vehicle> vehicleList = ServiceLocator.getInstance().getVehicleService().findByCriteria(criteria, 0, 0);

        mv.addObject("vehicleList", vehicleList);

        return mv;
    }

    @RequestMapping(value = "/admin/report", method = RequestMethod.GET)
    public ModelAndView adminReportGET(HttpSession session, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("admin/report");

        return mv;
    }

}
