package com.academico.garagem.controller.pages;

import com.academico.garagem.model.entity.Garage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GaragePage extends NavBar {

    public GaragePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public GaragePage goToNew() {
        driver.findElement(By.cssSelector(".card-header a[href$='/new']")).click();
        return this;
    }

    public GaragePage createGarage(Garage garage) {
        driver.findElement(By.cssSelector("#street")).click();
        driver.findElement(By.cssSelector("#street")).sendKeys(garage.getAddressId().getStreet());

        driver.findElement(By.cssSelector("#number")).click();
        driver.findElement(By.cssSelector("#number")).sendKeys(garage.getAddressId().getNumber());

        driver.findElement(By.cssSelector("#neighborhood")).click();
        driver.findElement(By.cssSelector("#neighborhood")).sendKeys(garage.getAddressId().getNeighborhood());

        driver.findElement(By.cssSelector("#city")).click();
        driver.findElement(By.cssSelector("#city")).sendKeys(garage.getAddressId().getCity());

        driver.findElement(By.cssSelector("#state")).click();
        driver.findElement(By.cssSelector("#state")).sendKeys(garage.getAddressId().getState());

        driver.findElement(By.cssSelector("#zip")).click();
        driver.findElement(By.cssSelector("#zip")).sendKeys(garage.getAddressId().getZip());

        driver.findElement(By.cssSelector("#height")).click();
        driver.findElement(By.cssSelector("#height")).sendKeys(String.valueOf(garage.getHeight()));

        driver.findElement(By.cssSelector("#width")).click();
        driver.findElement(By.cssSelector("#width")).sendKeys(String.valueOf(garage.getWidth()));

        driver.findElement(By.cssSelector("#length")).click();
        driver.findElement(By.cssSelector("#length")).sendKeys(String.valueOf(garage.getLength()));

        driver.findElement(By.cssSelector("#access")).click();
        driver.findElement(By.cssSelector("#access")).sendKeys(garage.getAccess());

        if (garage.getHasRoof()) {
            driver.findElement(By.cssSelector("label[for='roofY']")).click();
        } else {
            driver.findElement(By.cssSelector("label[for='roofN']")).click();
        }

        if (garage.getHasIndemnity()) {
            driver.findElement(By.cssSelector("label[for='indemnityY']")).click();
        } else {
            driver.findElement(By.cssSelector("label[for='indemnityN']")).click();
        }

        if (garage.getHasCam()) {
            driver.findElement(By.cssSelector("label[for='camY']")).click();
        } else {
            driver.findElement(By.cssSelector("label[for='camN']")).click();
        }

        if (garage.getHasElectronicGate()) {
            driver.findElement(By.cssSelector("label[for='electronicGateY']")).click();
        } else {
            driver.findElement(By.cssSelector("label[for='electronicGateN']")).click();
        }

        driver.findElement(By.cssSelector("#save")).click();
        return this;
    }

    public GaragePage deleteGarage(Integer id) {
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
