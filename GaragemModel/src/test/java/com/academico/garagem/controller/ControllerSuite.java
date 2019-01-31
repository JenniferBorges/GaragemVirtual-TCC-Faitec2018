package com.academico.garagem.controller;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    GaragemVirtualAnnouncerTest.class,
    GaragemVirtualUserTest.class
})
public class ControllerSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        User announcer = ServiceLocator.getInstance().getUserService().findByEmail("ta@teste.com");
        User user = ServiceLocator.getInstance().getUserService().findByEmail("ta2@teste.com");

        for (Garage garage : announcer.getGarageList()) {
            for (Advertisement advertisement : garage.getAdvertisementList()) {
                for (RentGarage rentGarage : advertisement.getRentGarageList()) {
                    ServiceLocator.getInstance().getRentGarageService().destroy(rentGarage.getId());
                }
                ServiceLocator.getInstance().getAdvertisementService().destroy(advertisement.getId());
            }
            ServiceLocator.getInstance().getGarageService().destroy(garage.getId());
            ServiceLocator.getInstance().getAddressService().destroy(garage.getAddressId().getId());
        }

        for (Vehicle vehicle : user.getVehicleList()) {
            ServiceLocator.getInstance().getVehicleService().destroy(vehicle.getId());
        }

        ServiceLocator.getInstance().getUserService().destroy(user.getId());
        ServiceLocator.getInstance().getUserService().destroy(announcer.getId());
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
