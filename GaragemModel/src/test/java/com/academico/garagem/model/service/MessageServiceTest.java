/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.MessageCriteria;
import com.academico.garagem.model.entity.Address;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.Message;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author mathe
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageServiceTest {

    static Message entity;
    static Address address;
    static Vehicle vehicle;
    static Advertisement advertisement;
    static User user;
    static Garage garage;
    static RentGarage rentGarage;
    static MessageService instance;

    public MessageServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
        instance = ServiceLocator.getInstance().getMessageService();

        address = new Address();
        address.setStreet("Rua José Luís Bueno de Carvalho");
        address.setNumber("65");
        address.setNeighborhood("Jardim Santo Antônio");
        address.setCity("Santa Rita do Sapucaí");
        address.setState("MG");
        address.setZip("37540-000");
        address.setLatitude(-22.2587309);
        address.setLongitude(-45.7018637);

        user = new User();
        user.setIdentity("00000000001");
        user.setName("Teste");
        user.setLastName("Garage");
        user.setEmail("teste@garage.com");
        user.setPassword("teste");
        user.setGender("M");
        user.setActive(Boolean.TRUE);
        user.setBanned(Boolean.FALSE);
        user.setAuthToken(null);
        user.setResetToken(null);
        user.setResetComplete(Boolean.TRUE);
        user.setJoiningDate(new Timestamp(System.currentTimeMillis()));
        user.setIsAdmin(Boolean.FALSE);
        user.setIsAuth(Boolean.TRUE);

        garage = new Garage();
        garage.setUserId(user);
        garage.setAddressId(address);
        garage.setHeight(10.0);
        garage.setWidth(10.0);
        garage.setLength(10.0);
        garage.setAccess("Acesso teste");
        garage.setHasRoof(Boolean.TRUE);
        garage.setHasCam(Boolean.FALSE);
        garage.setHasIndemnity(Boolean.FALSE);
        garage.setHasElectronicGate(Boolean.TRUE);

        advertisement = new Advertisement();
        advertisement.setTitle("Anúncio de teste");
        advertisement.setDescription("Descrição teste");
        advertisement.setPrice(10.0);
        advertisement.setCurrency("R$");
        advertisement.setActive(Boolean.TRUE);
        advertisement.setGarageId(garage);

        vehicle = new Vehicle();
        vehicle.setUserId(user);
        vehicle.setPlate("ABC1234");
        vehicle.setType("Tipo teste");
        vehicle.setManufacturer("Montadora teste");
        vehicle.setModel("Modelo teste");
        vehicle.setYear("2018");
        vehicle.setColor("Cor teste");
        vehicle.setChassis("123456789");
        vehicle.setIsAuth(Boolean.TRUE);

        rentGarage = new RentGarage();
        rentGarage.setAdvertisementId(advertisement);
        rentGarage.setVehicleId(vehicle);
        rentGarage.setDateTime(new Date());
        rentGarage.setInitialDateTime(new Date());
        rentGarage.setFinalDateTime(new Date());

        ServiceLocator.getInstance().getUserService().create(user);
        ServiceLocator.getInstance().getAddressService().create(address);
        ServiceLocator.getInstance().getGarageService().create(garage);
        ServiceLocator.getInstance().getAdvertisementService().create(advertisement);
        ServiceLocator.getInstance().getVehicleService().create(vehicle);
        ServiceLocator.getInstance().getRentGarageService().create(rentGarage);

        entity = new Message();
        entity.setUserToId(user);
        entity.setUserFromId(user);
        entity.setRentGarageId(rentGarage);
        entity.setDateTime(new Timestamp(System.currentTimeMillis()));
        entity.setMessage("Mensagem teste");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        ServiceLocator.getInstance().getRentGarageService().destroy(rentGarage.getId());
        ServiceLocator.getInstance().getVehicleService().destroy(vehicle.getId());
        ServiceLocator.getInstance().getAdvertisementService().destroy(advertisement.getId());
        ServiceLocator.getInstance().getGarageService().destroy(garage.getId());
        ServiceLocator.getInstance().getAddressService().destroy(address.getId());
        ServiceLocator.getInstance().getUserService().destroy(user.getId());
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test01Create() throws Exception {
        System.out.println("create");
        instance.create(entity);
    }

    /**
     * Test of findAll method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02FindAll() throws Exception {
        System.out.println("findAll");
        List<Message> result = instance.findAll();
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntities method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03FindEntities() throws Exception {
        System.out.println("findEntities");
        int maxResults = 1;
        int firstResult = 0;
        List<Message> result = instance.findEntities(maxResults, firstResult);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findByCriteria method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04FindByCriteria() throws Exception {
        System.out.println("findByCriteria");
        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(MessageCriteria.MESSAGE_ILIKE, entity.getMessage());
        int limit = 0;
        int offset = 0;
        List<Message> result = instance.findByCriteria(criteria, limit, offset);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntity method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test05FindEntity() throws Exception {
        System.out.println("findEntity");
        Message result = instance.findEntity(entity.getId());
        assertEquals(entity, result);
    }

    /**
     * Test of getEntityCount method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test06GetEntityCount() throws Exception {
        System.out.println("getEntityCount");
        int expResult = 1;
        int result = instance.getEntityCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of edit method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test07Edit() throws Exception {
        System.out.println("edit");
        entity.setUserToId(user);
        entity.setUserFromId(user);
        instance.edit(entity);
    }

    /**
     * Test of destroy method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test08Destroy() throws Exception {
        System.out.println("destroy");
        instance.destroy(entity.getId());
    }

    /**
     * Test of validate method, of class MessageService.
     *
     * @throws java.lang.Exception
     */
    /*@Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        Map<String, Object> fields = null;
        MessageService instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.validate(fields);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    @Test
    public void test10() throws Exception {
        System.out.println("serialize");
        List<Message> messageList = instance.findAll();
        String json = new ObjectMapper().writeValueAsString(messageList);
        System.out.println(json);
    }
}
