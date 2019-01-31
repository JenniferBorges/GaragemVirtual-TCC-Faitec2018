package com.academico.garagem.model;

import com.academico.garagem.model.service.*;
import com.academico.garagem.model.util.FlywayUtil;
import javax.persistence.EntityManagerFactory;

public class Assembler {

    private static Assembler instance;

    private Assembler() {
    }

    public static Assembler getInstance() {
        if (instance == null) {
            instance = new Assembler();
        }
        return instance;
    }

    public void configure() {
        FlywayUtil.update();
        EntityManagerFactory emf = EntityManagerProvider.getInstance().getFactory();

        ServiceLocator.getInstance().loadService(ServiceLocator.ADDRESS_SERVICE, new AddressService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.ADVERTISEMENT_SERVICE, new AdvertisementService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.DISPONIBILITY_SERVICE, new DisponibilityService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.GARAGE_SERVICE, new GarageService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.MESSAGE_SERVICE, new MessageService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.RATING_SERVICE, new RatingService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.RENT_GARAGE_SERVICE, new RentGarageService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.IMAGE_SERVICE, new ImageService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.USER_PHONE_SERVICE, new UserPhoneService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.USER_SERVICE, new UserService(emf));
        ServiceLocator.getInstance().loadService(ServiceLocator.VEHICLE_SERVICE, new VehicleService(emf));
    }

}
