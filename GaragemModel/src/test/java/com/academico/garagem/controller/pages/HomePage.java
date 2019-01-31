package com.academico.garagem.controller.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends NavBar {

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public HomePage search(String address) {
        driver.findElement(By.cssSelector("#address")).click();
        driver.findElement(By.cssSelector("#address")).sendKeys(address);

        driver.findElement(By.cssSelector("#search")).click();
        return this;
    }

}
