package com.academico.garagem.util;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.RentGarageCriteria;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.enumeration.EImage;
import com.academico.garagem.model.service.RentGarageService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

    @ResponseBody
    @RequestMapping(value = "/notification", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String notification(HttpSession session) throws Exception {
        User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

        RentGarageService service = ServiceLocator.getInstance().getRentGarageService();

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(RentGarageCriteria.ADVERTISEMENT_ID_GARAGE_ID_USER_ID_EQ, user.getId());
        criteria.put(RentGarageCriteria.IS_AUTH_EQ, null);
        List<RentGarage> rentGarageList = service.findByCriteria(criteria, 0, 0);

        JsonArray array = new JsonArray();
        if (!rentGarageList.isEmpty()) {
            for (RentGarage rentGarage : rentGarageList) {
                JsonObject rent = new JsonObject();
                JsonObject vehicle = new JsonObject();
                JsonObject advertisement = new JsonObject();
                JsonObject _user = new JsonObject();
                rent.add("vehicle", vehicle);
                rent.add("advertisement", advertisement);
                rent.addProperty("dateTime", rentGarage.getDateTime().toString());

                advertisement.addProperty("id", rentGarage.getAdvertisementId().getId());

                vehicle.add("user", _user);
                vehicle.addProperty("plate", rentGarage.getVehicleId().getPlate());
                vehicle.addProperty("manufacturer", rentGarage.getVehicleId().getManufacturer());
                vehicle.addProperty("model", rentGarage.getVehicleId().getModel());

                _user.addProperty("name", rentGarage.getVehicleId().getUserId().getName());
                _user.addProperty("lastName", rentGarage.getVehicleId().getUserId().getLastName());
                for (Image image : rentGarage.getVehicleId().getUserId().getImageList()) {
                    if (image.getType() == EImage.USER_PHOTO.getType()) {
                        _user.addProperty("image", image.getSrc());
                    }
                }

                array.add(rent);
            }
        }

        JsonObject data = new JsonObject();
        data.add("data", array);

        return data.toString();
    }

}
