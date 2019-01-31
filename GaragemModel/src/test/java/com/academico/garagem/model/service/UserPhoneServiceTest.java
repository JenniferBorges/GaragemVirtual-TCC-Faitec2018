/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.UserPhoneCriteria;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.UserPhone;
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
public class UserPhoneServiceTest {

    static UserPhone entity;
    static User user;
    static UserPhoneService instance;

    public UserPhoneServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
        instance = ServiceLocator.getInstance().getUserPhoneService();

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

        entity = new UserPhone();
        entity.setNumber("3599192388");
        entity.setUserId(user);
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
     * Test of create method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test01Create() throws Exception {
        System.out.println("create");
        instance.create(entity);
    }

    /**
     * Test of findAll method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02FindAll() throws Exception {
        System.out.println("findAll");
        List<UserPhone> result = instance.findAll();
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntities method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03FindEntities() throws Exception {
        System.out.println("findEntities");
        int maxResults = 1;
        int firstResult = 0;
        List<UserPhone> result = instance.findEntities(maxResults, firstResult);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findByCriteria method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04FindByCriteria() throws Exception {
        System.out.println("findByCriteria");
        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(UserPhoneCriteria.NUMBER_EQ, entity.getNumber());
        int limit = 0;
        int offset = 0;
        List<UserPhone> result = instance.findByCriteria(criteria, limit, offset);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntity method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test05FindEntity() throws Exception {
        System.out.println("findEntity");
        UserPhone result = instance.findEntity(entity.getId());
        assertEquals(entity, result);
    }

    /**
     * Test of getEntityCount method, of class UserPhoneService.
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
     * Test of edit method, of class UserPhoneService.
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
     * Test of destroy method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test08Destroy() throws Exception {
        System.out.println("destroy");
        instance.destroy(entity.getId());
    }

    /**
     * Test of validate method, of class UserPhoneService.
     *
     * @throws java.lang.Exception
     */
    /*@Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        Map<String, Object> fields = null;
        UserPhoneService instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.validate(fields);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}
