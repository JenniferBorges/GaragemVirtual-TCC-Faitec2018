package com.academico.garagem.controller.pages;

import com.academico.garagem.model.entity.RentGarage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RentGaragePage extends NavBar {

    public RentGaragePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public RentGaragePage rent(RentGarage rentGarage) {
        Select vehicle = new Select(driver.findElement(By.cssSelector("#vehicleId")));
        vehicle.selectByValue(String.valueOf(rentGarage.getVehicleId().getId()));

        driver.findElement(By.cssSelector("#dateTime")).click();
        driver.findElement(By.cssSelector("#dateTime")).sendKeys(rentGarage.getDateTime().toString());

        driver.findElement(By.cssSelector("#save")).click();
        return this;
    }

}
