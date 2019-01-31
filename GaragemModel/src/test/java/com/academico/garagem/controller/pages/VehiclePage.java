package com.academico.garagem.controller.pages;

import com.academico.garagem.model.entity.Vehicle;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VehiclePage extends NavBar {

    public VehiclePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public VehiclePage goToNew() {
        driver.findElement(By.cssSelector(".card-header a[href$='/new']")).click();
        return this;
    }

    public VehiclePage createVehicle(Vehicle vehicle) {
        driver.findElement(By.cssSelector("#plate")).click();
        driver.findElement(By.cssSelector("#plate")).sendKeys(vehicle.getPlate());

        Select type = new Select(driver.findElement(By.cssSelector("#type")));
        type.selectByValue(vehicle.getType());

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#manufacturer")));
        Select manufacturer = new Select(driver.findElement(By.cssSelector("#manufacturer")));
        manufacturer.selectByValue(vehicle.getManufacturer());

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#model")));
        Select model = new Select(driver.findElement(By.cssSelector("#model")));
        model.selectByValue(vehicle.getModel());

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#year")));
        Select year = new Select(driver.findElement(By.cssSelector("#year")));
        year.selectByValue(vehicle.getYear());

        driver.findElement(By.cssSelector("#save")).click();
        return this;
    }

    public VehiclePage deleteVehicle(Integer id) {
        List<WebElement> rows = driver.findElements(By.cssSelector("table[id='table'] tbody tr"));
        for (WebElement row : rows) {
            if (row.findElements(By.cssSelector(".dropdown a[href$='/delete/" + id + "']")).size() > 0) {
                row.findElement(By.cssSelector(".dropdown")).click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".dropdown.show a[href$='/delete/" + id + "']")));
                row.findElement(By.cssSelector(".dropdown.show a[href$='/delete/" + id + "']")).click();
                return this;
            }
        }
        return null;
    }

}
