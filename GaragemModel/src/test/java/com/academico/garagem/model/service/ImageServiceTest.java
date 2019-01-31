/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.ImageCriteria;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.entity.User;
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
public class ImageServiceTest {

    static Image entity;
    static User user;
    static ImageService instance;

    public ImageServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
        instance = ServiceLocator.getInstance().getImageService();

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

        entity = new Image();
        entity.setSrc("teste.png");
        entity.setType(1);
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
     * Test of create method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test01Create() throws Exception {
        System.out.println("create");
        instance.create(entity);
    }

    /**
     * Test of findAll method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02FindAll() throws Exception {
        System.out.println("findAll");
        List<Image> result = instance.findAll();
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntities method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03FindEntities() throws Exception {
        System.out.println("findEntities");
        int maxResults = 1;
        int firstResult = 0;
        List<Image> result = instance.findEntities(maxResults, firstResult);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findByCriteria method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04FindByCriteria() throws Exception {
        System.out.println("findByCriteria");
        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(ImageCriteria.USER_ID_EQ, entity.getUserId().getId());
        int limit = 0;
        int offset = 0;
        List<Image> result = instance.findByCriteria(criteria, limit, offset);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntity method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test05FindEntity() throws Exception {
        System.out.println("findEntity");
        Image result = instance.findEntity(entity.getSrc());
        assertEquals(entity, result);
    }

    /**
     * Test of getEntityCount method, of class ImageService.
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
     * Test of edit method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test07Edit() throws Exception {
        System.out.println("edit");
        entity.setUserId(ServiceLocator.getInstance().getUserService().findEntity(1));
        instance.edit(entity);
    }

    /**
     * Test of destroy method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test08Destroy() throws Exception {
        System.out.println("destroy");
        instance.destroy(entity.getSrc());
    }

    /**
     * Test of validate method, of class ImageService.
     *
     * @throws java.lang.Exception
     */
    /*@Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        Map<String, Object> fields = null;
        ImageService instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.validate(fields);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}
