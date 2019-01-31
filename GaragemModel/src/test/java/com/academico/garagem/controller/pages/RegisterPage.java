package com.academico.garagem.controller.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage extends NavBar {

    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public RegisterPage register(String name, String lastName, String email, String password, String confirmPassword) {
        driver.findElement(By.cssSelector("#name")).click();
        driver.findElement(By.cssSelector("#name")).sendKeys(name);

        driver.findElement(By.cssSelector("#lastName")).click();
        driver.findElement(By.cssSelector("#lastName")).sendKeys(lastName);

        driver.findElement(By.cssSelector("#email")).click();
        driver.findElement(By.cssSelector("#email")).sendKeys(email);

        driver.findElement(By.cssSelector("#password")).click();
        driver.findElement(By.cssSelector("#password")).sendKeys(password);

        driver.findElement(By.cssSelector("#confirmPassword")).click();
        driver.findElement(By.cssSelector("#confirmPassword")).sendKeys(confirmPassword);

        driver.findElement(By.cssSelector("label[for='customCheckRegister']")).click();

        driver.findElement(By.cssSelector("#register")).click();
        return this;
    }

}
