/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.UserCriteria;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.UserPhone;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class UserServiceTest {

    static User entity;
    static UserService instance;

    public UserServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
        instance = ServiceLocator.getInstance().getUserService();

        entity = new User();
        entity.setName("Teste");
        entity.setLastName("Garage");
        entity.setEmail("teste@garage.com");
        entity.setPassword("teste");
        entity.setGender("M");
        entity.setActive(Boolean.TRUE);
        entity.setBanned(Boolean.FALSE);
        entity.setAuthToken(null);
        entity.setResetToken(null);
        entity.setResetComplete(Boolean.TRUE);
        entity.setJoiningDate(new Timestamp(System.currentTimeMillis()));
        entity.setIsAdmin(Boolean.FALSE);
        entity.setIsAuth(Boolean.TRUE);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test01Create() throws Exception {
        System.out.println("create");
        entity.setPassword(UserService.passwordEncoder.encode(entity.getPassword()));
        instance.create(entity);
    }

    /**
     * Test of login method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02Login() throws Exception {
        System.out.println("login");
        String password = "teste";
        User result = instance.login(entity.getEmail(), password);
        assertEquals(entity, result);
    }

    /**
     * Test of findAll method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03FindAll() throws Exception {
        System.out.println("findAll");
        List<User> result = instance.findAll();
        assertEquals(entity, result.get(1));
    }

    /**
     * Test of findEntities method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04FindEntities() throws Exception {
        System.out.println("findEntities");
        int maxResults = 2;
        int firstResult = 0;
        List<User> result = instance.findEntities(maxResults, firstResult);
        assertEquals(entity, result.get(1));
    }

    /**
     * Test of findByCriteria method, of class AddressService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test05FindByCriteria() throws Exception {
        System.out.println("findByCriteria");
        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(UserCriteria.NAME_EQ, entity.getName());
        int limit = 0;
        int offset = 0;
        List<User> result = instance.findByCriteria(criteria, limit, offset);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntity method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test06FindEntity() throws Exception {
        System.out.println("findEntity");
        User result = instance.findEntity(entity.getId());
        assertEquals(entity, result);
    }

    /**
     * Test of findByEmail method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test07FindByEmail() throws Exception {
        System.out.println("findByEmail");
        User result = instance.findByEmail(entity.getEmail());
        assertEquals(entity, result);
    }

    /**
     * Test of getEntityCount method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test08GetEntityCount() throws Exception {
        System.out.println("getEntityCount");
        int expResult = 2;
        int result = instance.getEntityCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of edit method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test09Edit() throws Exception {
        System.out.println("edit");
        entity = instance.findEntity(entity.getId());
        entity.setIdentity("00000000002");
        entity.setName("Teste");
        entity.setLastName("Garage");
        entity.setEmail("abc@garage.com");
        entity.setPassword("abc");
        entity.setGender("F");
        entity.setActive(Boolean.FALSE);
        entity.setBanned(Boolean.TRUE);
        entity.setAuthToken("");
        entity.setResetToken("");
        entity.setResetComplete(Boolean.TRUE);
        entity.setJoiningDate(new Timestamp(System.currentTimeMillis()));
        entity.setIsAdmin(Boolean.TRUE);
        entity.setIsAuth(Boolean.FALSE);

        UserPhone phone = new UserPhone();
        phone.setNumber("12345");
        phone.setUserId(entity);

        ServiceLocator.getInstance().getUserPhoneService().create(phone);

        List<UserPhone> list = new ArrayList<>();
        list.add(phone);
        entity.setUserPhoneList(list);

        entity.setPassword(UserService.passwordEncoder.encode(entity.getPassword()));
        instance.edit(entity);
    }

    /**
     * Test of destroy method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test10Destroy() throws Exception {
        System.out.println("destroy");
        instance.destroy(entity.getId());
    }

    /**
     * Test of validate method, of class UserService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test11Validate() throws Exception {
        System.out.println("validate");
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", entity.getName());
        fields.put("lastName", entity.getLastName());
        fields.put("email", entity.getEmail());
        fields.put("password", entity.getPassword());
        fields.put("confirmPassword", entity.getPassword());
        Map<String, String> result = instance.validate(fields);
        assertEquals(new HashMap<>(), result);
    }

}
