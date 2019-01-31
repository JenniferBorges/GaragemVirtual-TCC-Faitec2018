/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.entity.Address;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Disponibility;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.User;
import static com.academico.garagem.model.service.RentGarageServiceTest.disponibility;
import java.sql.Timestamp;
import java.util.Calendar;
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
public class AdvertisementServiceTest {

    static Advertisement entity;
    static Address address;
    static User user;
    static Garage garage;
    static AdvertisementService instance;

    public AdvertisementServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Assembler.getInstance().configure();
        instance = ServiceLocator.getInstance().getAdvertisementService();

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

        ServiceLocator.getInstance().getAddressService().create(address);
        ServiceLocator.getInstance().getUserService().create(user);
        ServiceLocator.getInstance().getGarageService().create(garage);

        entity = new Advertisement();
        entity.setTitle("Anúncio de teste");
        entity.setDescription("Descrição teste");
        entity.setPrice(10.0);
        entity.setCurrency("R$");
        entity.setActive(Boolean.TRUE);
        entity.setGarageId(garage);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
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
     * Test of create method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test01Create() throws Exception {
        System.out.println("create");
        instance.create(entity);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for (int i = 1; i < 8; i++) {
            disponibility = new Disponibility();
            disponibility.setDay(i);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            disponibility.setStartsAt(cal.getTime());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            disponibility.setEndsAt(cal.getTime());
            disponibility.setAdvertisementId(entity);
            ServiceLocator.getInstance().getDisponibilityService().create(disponibility);
        }
    }

    /**
     * Test of findAll method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test02FindAll() throws Exception {
        System.out.println("findAll");
        List<Advertisement> result = instance.findAll();
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntities method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test03FindEntities() throws Exception {
        System.out.println("findEntities");
        int maxResults = 1;
        int firstResult = 0;
        List<Advertisement> result = instance.findEntities(maxResults, firstResult);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findByCriteria method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test04FindByCriteria() throws Exception {
        System.out.println("findByCriteria");
        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(AdvertisementCriteria.TITLE_EQ, "Anúncio de teste");
        int limit = 0;
        int offset = 0;
        List<Advertisement> result = instance.findByCriteria(criteria, limit, offset);
        assertEquals(entity, result.get(0));
    }

    /**
     * Test of findEntity method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test05FindEntity() throws Exception {
        System.out.println("findEntity");
        Advertisement result = instance.findEntity(entity.getId());
        assertEquals(entity, result);
    }

    /**
     * Test of getEntityCount method, of class AdvertisementService.
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
     * Test of edit method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test07Edit() throws Exception {
        System.out.println("edit");
        entity.setGarageId(garage);
        instance.edit(entity);
    }

    /**
     * Test of destroy method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test08Destroy() throws Exception {
        System.out.println("destroy");
        instance.destroy(entity.getId());
    }

    /**
     * Test of validate method, of class AdvertisementService.
     *
     * @throws java.lang.Exception
     */
    /*@Test
    public void testValidate() throws Exception {
        System.out.println("validate");
        Map<String, Object> fields = null;
        AdvertisementService instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.validate(fields);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}
