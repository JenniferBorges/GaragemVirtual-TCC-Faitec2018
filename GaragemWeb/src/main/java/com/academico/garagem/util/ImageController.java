package com.academico.garagem.util;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.ImageCriteria;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import com.academico.garagem.model.enumeration.EImage;
import com.academico.garagem.model.util.Config;
import com.google.gson.JsonObject;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    @ResponseBody
    @RequestMapping(value = "/photo/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String savePhoto(MultipartFile file, int type, Integer id) throws Exception {
        JsonObject response = new JsonObject();
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String src = new Date().getTime() + "." + extension;

            Map<Integer, Object> criteria = new HashMap<>();
            criteria.put(ImageCriteria.TYPE_EQ, type);

            Image image = new Image();
            image.setType(type);
            image.setSrc(src);

            if (id != null) {
                List<Image> imageList;
                switch (EImage.valueOf(type).getTable()) {
                    case "garage":
                        Garage garage = ServiceLocator.getInstance().getGarageService().findEntity(id);
                        image.setGarageId(garage);
                        break;
                    case "user":
                        User user = ServiceLocator.getInstance().getUserService().findEntity(id);
                        image.setUserId(user);
                        criteria.put(ImageCriteria.USER_ID_EQ, user.getId());
                        imageList = ServiceLocator.getInstance().getImageService().findByCriteria(criteria, 0, 0);
                        if (image.getType() == EImage.USER_PHOTO.getType()
                                || image.getType() == EImage.USER_DOC_FRONT.getType()
                                || image.getType() == EImage.USER_DOC_FRONT.getType()
                                || image.getType() == EImage.USER_DOC_HAB.getType()) {
                            for (Image i : imageList) {
                                if (i.getType() == image.getType()) {
                                    ServiceLocator.getInstance().getImageService().destroy(i.getSrc());
                                    ServiceLocator.getInstance().getImageService().deletePhoto(Config.getInstance().getPicturesPath() + i.getSrc());
                                    break;
                                }
                            }
                        }
                        break;
                    case "vehicle":
                        Vehicle vehicle = ServiceLocator.getInstance().getVehicleService().findEntity(id);
                        image.setVehicleId(vehicle);
                        criteria.put(ImageCriteria.VEHICLE_ID_EQ, vehicle.getId());
                        imageList = ServiceLocator.getInstance().getImageService().findByCriteria(criteria, 0, 0);
                        if (image.getType() == EImage.VEHICLE_DOC.getType()) {
                            for (Image i : imageList) {
                                if (i.getType() == image.getType()) {
                                    ServiceLocator.getInstance().getImageService().destroy(i.getSrc());
                                    ServiceLocator.getInstance().getImageService().deletePhoto(Config.getInstance().getPicturesPath() + i.getSrc());
                                    break;
                                }
                            }
                        }
                        break;
                }
            }

            ServiceLocator.getInstance().getImageService().create(image);
            ServiceLocator.getInstance().getImageService().savePhoto(file.getBytes(), Config.getInstance().getPicturesPath() + src);

            JsonObject img = new JsonObject();
            img.addProperty("src", src);
            img.addProperty("type", type);
            response.add("image", img);
        } catch (Exception e) {
            e.printStackTrace();
            response.addProperty("error", e.getMessage());
        }

        return response.toString();
    }

    @RequestMapping(value = "/photo/{src:.+}", method = RequestMethod.GET)
    public void streamImage(@PathVariable String src, HttpServletResponse response) throws Exception {
        try {
            File file = ServiceLocator.getInstance().getImageService().getPhoto(Config.getInstance().getPicturesPath() + src);
            if (file != null) {
                response.setContentType(Files.probeContentType(file.toPath()));
                response.getOutputStream().write(Files.readAllBytes(file.toPath()));
                response.flushBuffer();
            } else {
                ServiceLocator.getInstance().getImageService().destroy(src);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/photo/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePhoto(String src) throws Exception {
        JsonObject response = new JsonObject();
        try {
            Image image = ServiceLocator.getInstance().getImageService().findEntity(src);
            ServiceLocator.getInstance().getImageService().destroy(image.getSrc());
            ServiceLocator.getInstance().getImageService().deletePhoto(Config.getInstance().getPicturesPath() + src);
        } catch (Exception e) {
            e.printStackTrace();
            response.addProperty("error", e.getMessage());
        }

        return response.toString();
    }

}
