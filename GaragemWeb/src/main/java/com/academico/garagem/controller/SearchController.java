package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Notification;
import com.academico.garagem.model.enumeration.ENotification;
import com.academico.garagem.model.service.AddressService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchController {

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchGET(RedirectAttributes redir, HttpSession session, String address, @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date dateTime) throws Exception {
        ModelAndView mv = new ModelAndView("home/map");
        Notification notification = new Notification();

        try {
            AddressService service = ServiceLocator.getInstance().getAddressService();

            if (address != null) {
                JsonObject location = service.getLatLngFromAddress(address);
                mv.addObject("location", new Gson().fromJson(location, Map.class));
            }

            JsonArray array = null;
            if (dateTime == null) {
                array = getAdvertisementByDate(new Date());
            } else {
                Calendar now = Calendar.getInstance();
                now.set(Calendar.SECOND, 0);
                now.set(Calendar.MILLISECOND, 0);
                if (!dateTime.before(now.getTime())) {
                    array = getAdvertisementByDate(dateTime);
                }
            }
            mv.addObject("garageArray", array.toString());
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/search/date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String searchByDate(@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date dateTime) throws Exception {
        JsonObject response = new JsonObject();
        Notification notification = new Notification();

        try {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            if (!dateTime.before(now.getTime())) {
                JsonArray array = getAdvertisementByDate(dateTime);
                response.add("data", array);
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            ex.printStackTrace();
        }

        return response.toString();
    }

    private JsonArray getAdvertisementByDate(Date dateTime) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(AdvertisementCriteria.DISPONIBILITY_LIST_DAY_EQ, dayOfWeek);
        criteria.put(AdvertisementCriteria.DISPONIBILITY_LIST_STARTS_AT_LT, dateTime);
        criteria.put(AdvertisementCriteria.DISPONIBILITY_LIST_ENDS_AT_GT, dateTime);
        List<Advertisement> advertisementList = ServiceLocator.getInstance().getAdvertisementService().findByCriteria(criteria, 0, 0);
        JsonArray array = new JsonArray();
        if (advertisementList != null && !advertisementList.isEmpty()) {
            for (Advertisement advertisement : advertisementList) {
                JsonObject obj = new JsonObject();
                obj.addProperty("id", advertisement.getId());
                obj.addProperty("title", advertisement.getTitle());

                JsonObject local = new JsonObject();
                local.addProperty("lat", advertisement.getGarageId().getAddressId().getLatitude());
                local.addProperty("lng", advertisement.getGarageId().getAddressId().getLongitude());
                obj.add("location", local);

                array.add(obj);
            }
        }

        return array;
    }

}
