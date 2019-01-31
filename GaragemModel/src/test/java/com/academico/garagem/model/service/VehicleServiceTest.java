/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.VehicleCriteria;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import java.sql.Timestamp;
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
public class VehicleServiceTest {

    static Vehicle entity;
    static User user;
    static VehicleService instance;

    public VehicleServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
        instance = ServiceLocator.getInstance().getVehicleService();

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

        ServiceLocator.getInstance().getUserService().create(user);

        entity = new Vehicle();
        entity.setUserId(user);
        entity.setPlate("ABC1234");
        entity.setType("Tipo teste");
        entity.setManufacturer("Montadora teste");
        entity.setModel("Modelo teste");
        entity.setYear("2018");
        entity.setColor("Cor teste");
        entity.setChassis("123456789");
        entity.setIsAuth(Boolean.TRUE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        ServiceLocator.getInstance().getUserService().destroy(user.getId());
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test01Create() throws Exception {
        System.out.println("create");
        instance.create(entity);
    }

    /**
     * Test of findAll method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02FindAll() throws Exception {
        System.out.println("findAll");
        List<Vehicle> result = instance.findAll();
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntities method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03FindEntities() throws Exception {
        System.out.println("findEntities");
        int maxResults = 1;
        int firstResult = 0;
        List<Vehicle> result = instance.findEntities(maxResults, firstResult);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findByCriteria method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04FindByCriteria() throws Exception {
        System.out.println("findByCriteria");
        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(VehicleCriteria.TYPE_EQ, entity.getType());
        int limit = 0;
        int offset = 0;
        List<Vehicle> result = instance.findByCriteria(criteria, limit, offset);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntity method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test05FindEntity() throws Exception {
        System.out.println("findEntity");
        Vehicle result = instance.findEntity(entity.getId());
        assertEquals(entity, result);
    }

    /**
     * Test of getEntityCount method, of class VehicleService.
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
     * Test of edit method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test07Edit() throws Exception {
        System.out.println("edit");
        entity.setUserId(user);
        instance.edit(entity);
    }

    /**
     * Test of destroy method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test08Destroy() throws Exception {
        System.out.println("destroy");
        instance.destroy(entity.getId());
    }

    /**
     * Test of validate method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    /*@Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        Map<String, Object> fields = null;
        VehicleService instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.validate(fields);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    /**
     * Test of getManufacturer method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test10GetManufacturer() throws Exception {
        String type = "2";
        switch (type) {
            case "1":
                type = "carros";
                break;
            case "2":
                type = "motos";
                break;
            case "3":
                type = "caminhoes";
                break;
            default:
                type = null;
        }
        instance.getManufacturer(type);
    }

    /**
     * Test of getModel method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test11GetModel() throws Exception {
        String type = "1";
        switch (type) {
            case "1":
                type = "carros";
                break;
            case "2":
                type = "motos";
                break;
            case "3":
                type = "caminhoes";
                break;
            default:
                type = null;
        }
        String manufacturer = "80";
        instance.getModel(type, manufacturer);
    }

    /**
     * Test of getYear method, of class VehicleService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test12GetYear() throws Exception {
        String type = "2";
        switch (type) {
            case "1":
                type = "carros";
                break;
            case "2":
                type = "motos";
                break;
            case "3":
                type = "caminhoes";
                break;
            default:
                type = null;
        }
        String manufacturer = "80";
        String model = "7383";
        instance.getYear(type, manufacturer, model);
    }
}
