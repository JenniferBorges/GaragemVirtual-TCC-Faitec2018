package com.academico.garagem.controller.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBar {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public NavBar(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public HomePage goToHomePage() {
        driver.findElement(By.cssSelector("a.navbar-brand")).click();
        return new HomePage(driver, wait);
    }

    public RegisterPage goToRegisterPage() {
        driver.findElement(By.cssSelector(".nav-item a[title='Acessar o Garagem Virtual']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nav-item.dropdown.show")));

        driver.findElement(By.cssSelector(".nav-item a[href$='/register']")).click();
        return new RegisterPage(driver, wait);
    }

    public VehiclePage goToVehiclePage() {
        driver.findElement(By.cssSelector(".nav-item .avatar")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nav-item.dropdown.show")));

        driver.findElement(By.cssSelector(".nav-item a[href$='/vehicle']")).click();
        return new VehiclePage(driver, wait);
    }

    public GaragePage goToGaragePage() {
        driver.findElement(By.cssSelector(".nav-item .avatar")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nav-item.dropdown.show")));

        driver.findElement(By.cssSelector(".nav-item a[href$='/garage']")).click();
        return new GaragePage(driver, wait);
    }

    public AdvertisementPage goToAdvertisement() {
        driver.findElement(By.cssSelector(".nav-item .avatar")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nav-item.dropdown.show")));

        driver.findElement(By.cssSelector(".nav-item a[href$='/advertisement']")).click();
        return new AdvertisementPage(driver, wait);
    }

    public NavBar login(String email, String password) {
        driver.findElement(By.cssSelector(".nav-item a[title='Acessar o Garagem Virtual']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nav-item.dropdown.show")));

        driver.findElement(By.cssSelector("#loginEmail")).click();
        driver.findElement(By.cssSelector("#loginEmail")).sendKeys(email);

        driver.findElement(By.cssSelector("#loginPassword")).click();
        driver.findElement(By.cssSelector("#loginPassword")).sendKeys(password);

        driver.findElement(By.cssSelector("form button[name='login']")).click();
        return new NavBar(driver, wait);
    }

    public NavBar logout() {
        driver.findElement(By.cssSelector(".nav-item .avatar")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nav-item.dropdown.show")));

        driver.findElement(By.cssSelector(".nav-item .dropdown-item[title='Sair']")).click();
        return new NavBar(driver, wait);
    }

    public boolean isSuccess() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#noty_layout__topRight")));
        return driver.findElements(By.cssSelector("#noty_layout__topRight .noty_type__success")).size() > 0;
    }

}
