package com.academico.garagem.controller;

import com.academico.garagem.controller.pages.AdvertisementPage;
import com.academico.garagem.controller.pages.GaragePage;
import com.academico.garagem.controller.pages.NavBar;
import com.academico.garagem.controller.pages.RegisterPage;
import com.academico.garagem.controller.pages.Site;
import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.GarageCriteria;
import com.academico.garagem.model.entity.Address;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.Disponibility;
import com.academico.garagem.model.entity.Garage;
import com.academico.garagem.model.entity.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GaragemVirtualAnnouncerTest {

    static WebDriver driver;
    static WebDriverWait wait;
    static Site site;
    static NavBar navbar;

    public GaragemVirtualAnnouncerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Assembler.getInstance().configure();
        System.setProperty("webdriver.chrome.driver", "../Selenium/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        site = new Site(driver, wait).goTo("http://localhost:8084/virtualgarage");
        site.waitLoad();
        navbar = site.getNavBar();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.close();
        driver.quit();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test01RegisterOk() {
        System.out.println("testRegisterOk");
        RegisterPage registerPage = navbar.goToRegisterPage();
        site.waitLoad();
        registerPage.register("Teste", "Automático", "ta@teste.com", "1234", "1234");

        assertTrue(registerPage.isSuccess());
    }

    @Test
    public void test02ActivateOk() {
        System.out.println("testActivateOk");
        User user = ServiceLocator.getInstance().getUserService().findByEmail("ta@teste.com");
        site.goTo("http://localhost:8084/virtualgarage/activate?x=" + user.getEmail() + "&y=" + user.getAuthToken());
        site.waitLoad();

        assertTrue(navbar.isSuccess());
    }

    @Test
    public void test03LoginOk() {
        System.out.println("testLoginOk");
        navbar = navbar.login("ta@teste.com", "1234");
        site.waitLoad();

        assertTrue(site.isLogged());
    }

    @Test
    public void test04GarageCreateOk() {
        System.out.println("testGarageCreateOk");
        GaragePage garagePage = navbar.goToGaragePage();
        site.waitLoad();
        garagePage.goToNew();
        site.waitLoad();

        Address address = new Address();
        address.setStreet("Rua José Luís Bueno de Carvalho");
        address.setNumber("65");
        address.setNeighborhood("Jardim Santo Antônio");
        address.setCity("Santa Rita do Sapucaí");
        address.setState("MG");
        address.setZip("37540-000");

        Garage garage = new Garage();
        garage.setAddressId(address);
        garage.setHeight(10.0);
        garage.setWidth(10.0);
        garage.setLength(10.0);
        garage.setAccess("Acesso teste");
        garage.setHasRoof(Boolean.TRUE);
        garage.setHasCam(Boolean.FALSE);
        garage.setHasIndemnity(Boolean.FALSE);
        garage.setHasElectronicGate(Boolean.TRUE);

        garagePage.createGarage(garage);

        assertTrue(garagePage.isSuccess());
    }

    @Test
    public void test05AdvertisementCreateOk() throws Exception {
        System.out.println("testAdvertisementCreateOk");
        AdvertisementPage advertisementPage = navbar.goToAdvertisement();
        site.waitLoad();
        advertisementPage.goToNew();
        site.waitLoad();

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle("Anúncio de teste");
        advertisement.setDescription("Descrição teste");
        advertisement.setPrice(10.0);
        advertisement.setCurrency("R$");

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(GarageCriteria.ACCESS_EQ, "Acesso teste");
        Garage garage = ServiceLocator.getInstance().getGarageService().findByCriteria(criteria, 0, 0).get(0);
        advertisement.setGarageId(garage);

        List<Disponibility> disponibilityList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for (int i = 1; i < 8; i++) {
            Disponibility disponibility = new Disponibility();
            disponibility.setDay(i);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            disponibility.setStartsAt(cal.getTime());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            disponibility.setEndsAt(cal.getTime());
            disponibility.setAdvertisementId(advertisement);
            disponibilityList.add(disponibility);
        }

        advertisement.setDisponibilityList(disponibilityList);

        advertisementPage.createAdvertisement(advertisement);

        assertTrue(advertisementPage.isSuccess());
    }

    @Test
    public void test99LogoutOk() {
        System.out.println("testLoginOk");
        navbar.logout();
        site.waitLoad();

        assertTrue(!site.isLogged());
    }

}
