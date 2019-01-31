package com.academico.garagem.controller;

import com.academico.garagem.controller.pages.HomePage;
import com.academico.garagem.controller.pages.NavBar;
import com.academico.garagem.controller.pages.RegisterPage;
import com.academico.garagem.controller.pages.RentGaragePage;
import com.academico.garagem.controller.pages.Site;
import com.academico.garagem.controller.pages.VehiclePage;
import com.academico.garagem.model.Assembler;
import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.criteria.AdvertisementCriteria;
import com.academico.garagem.model.criteria.VehicleCriteria;
import com.academico.garagem.model.entity.Advertisement;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.Vehicle;
import java.util.Date;
import java.util.HashMap;
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
public class GaragemVirtualUserTest {

    static WebDriver driver;
    static WebDriverWait wait;
    static Site site;
    static NavBar navbar;

    public GaragemVirtualUserTest() {
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
        registerPage.register("Teste", "Automático 2", "ta2@teste.com", "1234", "1234");

        assertTrue(registerPage.isSuccess());
    }

    @Test
    public void test02ActivateOk() {
        System.out.println("testActivateOk");
        User user = ServiceLocator.getInstance().getUserService().findByEmail("ta2@teste.com");
        site.goTo("http://localhost:8084/virtualgarage/activate?x=" + user.getEmail() + "&y=" + user.getAuthToken());
        site.waitLoad();

        assertTrue(navbar.isSuccess());
    }

    @Test
    public void test03LoginOk() {
        System.out.println("testLoginOk");
        navbar = navbar.login("ta2@teste.com", "1234");
        site.waitLoad();

        assertTrue(site.isLogged());
    }

    @Test
    public void test04VehicleCreateOk() {
        System.out.println("testVehicleCreateOk");
        VehiclePage vehiclePage = navbar.goToVehiclePage();
        site.waitLoad();
        vehiclePage.goToNew();
        site.waitLoad();
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("QMR-7407");
        vehicle.setType("Carro");
        vehicle.setManufacturer("FIAT");
        vehicle.setModel("MOBI DRIVE 1.0 Flex 6V 5p");
        vehicle.setYear("2018 Gasolina");
        vehicle.setColor("Vermelho");

        vehiclePage.createVehicle(vehicle);

        assertTrue(vehiclePage.isSuccess());
    }

    @Test
    public void test05RentGarageCreateOk() throws Exception {
        System.out.println("testRentGarageCreateOk");
        HomePage homePage = navbar.goToHomePage();
        site.waitLoad();
        homePage.search("Santa Rita do Sapucaí");
        site.waitLoad();
        RentGarage rentGarage = new RentGarage();
        rentGarage.setDateTime(new Date());

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(VehicleCriteria.PLATE_EQ, "QMR-7407");
        Vehicle vehicle = ServiceLocator.getInstance().getVehicleService().findByCriteria(criteria, 0, 0).get(0);
        rentGarage.setVehicleId(vehicle);

        criteria = new HashMap<>();
        criteria.put(AdvertisementCriteria.TITLE_EQ, "Anúncio de teste");
        Advertisement advertisement = ServiceLocator.getInstance().getAdvertisementService().findByCriteria(criteria, 0, 0).get(0);
        driver.get("http://localhost:8084/virtualgarage/rent-garage/" + advertisement.getId());
        site.waitLoad();
        RentGaragePage rentGaragePage = new RentGaragePage(driver, wait);

        rentGaragePage.rent(rentGarage);

        assertTrue(rentGaragePage.isSuccess());
    }

    @Test
    public void test99LogoutOk() {
        System.out.println("testLoginOk");
        navbar.logout();
        site.waitLoad();

        assertTrue(!site.isLogged());
    }

}
