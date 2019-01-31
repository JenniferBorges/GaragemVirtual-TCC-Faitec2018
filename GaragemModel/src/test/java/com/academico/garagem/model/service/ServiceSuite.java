/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author mathe
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    com.academico.garagem.model.service.UserServiceTest.class,
    com.academico.garagem.model.service.AdvertisementServiceTest.class,
    com.academico.garagem.model.service.RentGarageServiceTest.class,
    com.academico.garagem.model.service.MessageServiceTest.class,
    com.academico.garagem.model.service.UserPhoneServiceTest.class,
    com.academico.garagem.model.service.GarageServiceTest.class,
    com.academico.garagem.model.service.AddressServiceTest.class,
    com.academico.garagem.model.service.DisponibilityServiceTest.class,
    com.academico.garagem.model.service.ImageServiceTest.class,
    com.academico.garagem.model.service.VehicleServiceTest.class
})
public class ServiceSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
