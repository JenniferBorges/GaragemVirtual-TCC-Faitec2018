package com.academico.garagem.model;

import com.academico.garagem.model.service.*;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    public static final String ADDRESS_SERVICE = "address";
    public static final String ADVERTISEMENT_SERVICE = "advertisement";
    public static final String DISPONIBILITY_SERVICE = "disponibility";
    public static final String MESSAGE_SERVICE = "message";
    public static final String GARAGE_SERVICE = "garage";
    public static final String RATING_SERVICE = "rating";
    public static final String RENT_GARAGE_SERVICE = "rentGarage";
    public static final String IMAGE_SERVICE = "image";
    public static final String USER_PHONE_SERVICE = "userPhone";
    public static final String USER_SERVICE = "user";
    public static final String VEHICLE_SERVICE = "vehicle";

    private static final ServiceLocator INSTANCE = new ServiceLocator();

    private final Map<String, Object> services = new HashMap<>();

    private ServiceLocator() {
    }

    void loadService(String key, Object service) {
        services.put(key, service);
    }

    public static ServiceLocator getInstance() {
        return INSTANCE;
    }

    public Object getService(String key) {
        return services.get(key);
    }

    public AddressService getAddressService() {
        return (AddressService) getService(ADDRESS_SERVICE);
    }

    public AdvertisementService getAdvertisementService() {
        return (AdvertisementService) getService(ADVERTISEMENT_SERVICE);
    }

    public DisponibilityService getDisponibilityService() {
        return (DisponibilityService) getService(DISPONIBILITY_SERVICE);
    }

    public MessageService getMessageService() {
        return (MessageService) getService(MESSAGE_SERVICE);
    }

    public RatingService getRatingService() {
        return (RatingService) getService(RATING_SERVICE);
    }

    public RentGarageService getRentGarageService() {
        return (RentGarageService) getService(RENT_GARAGE_SERVICE);
    }

    public GarageService getGarageService() {
        return (GarageService) getService(GARAGE_SERVICE);
    }

    public ImageService getImageService() {
        return (ImageService) getService(IMAGE_SERVICE);
    }

    public UserPhoneService getUserPhoneService() {
        return (UserPhoneService) getService(USER_PHONE_SERVICE);
    }

    public UserService getUserService() {
        return (UserService) getService(USER_SERVICE);
    }

    public VehicleService getVehicleService() {
        return (VehicleService) getService(VEHICLE_SERVICE);
    }

}
