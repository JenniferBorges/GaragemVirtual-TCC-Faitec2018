package com.academico.garagem.controller.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Site {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public Site(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public Site goTo(String url) {
        driver.get(url);
        return new Site(driver, wait);
    }

    public boolean isLogged() {
        return driver.findElements(By.cssSelector(".nav-item .avatar")).size() > 0;
    }

    public void waitLoad() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
    }

    public NavBar getNavBar() {
        return new NavBar(driver, wait);
    }

}
