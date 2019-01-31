package com.academico.garagem.controller.pages;

import com.academico.garagem.model.entity.Advertisement;
import java.text.SimpleDateFormat;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdvertisementPage extends NavBar {

    public AdvertisementPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public AdvertisementPage goToNew() {
        driver.findElement(By.cssSelector(".card-header a[href$='/new']")).click();
        return this;
    }

    public AdvertisementPage createAdvertisement(Advertisement advertisement) {
        for (int i = 0; i < advertisement.getDisponibilityList().size(); i++) {
            driver.findElement(By.cssSelector("#addDisponibility")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".row.disponibility")));

            Select day = new Select(driver.findElement(By.cssSelector("#day-" + i)));
            day.selectByValue(String.valueOf(advertisement.getDisponibilityList().get(i).getDay()));

            driver.findElement(By.cssSelector("#startsAt-" + i)).click();
            driver.findElement(By.cssSelector("#startsAt-" + i)).sendKeys(new SimpleDateFormat("HH:mm").format(advertisement.getDisponibilityList().get(i).getStartsAt()));

            driver.findElement(By.cssSelector("#endsAt-" + i)).click();
            driver.findElement(By.cssSelector("#endsAt-" + i)).sendKeys(new SimpleDateFormat("HH:mm").format(advertisement.getDisponibilityList().get(i).getEndsAt()));
        }

        driver.findElement(By.cssSelector("#title")).click();
        driver.findElement(By.cssSelector("#title")).sendKeys(advertisement.getTitle());

        Select currency = new Select(driver.findElement(By.cssSelector("#currency")));
        currency.selectByValue(String.valueOf(advertisement.getCurrency()));

        driver.findElement(By.cssSelector("#price")).click();
        driver.findElement(By.cssSelector("#price")).sendKeys(String.valueOf(advertisement.getPrice()));

        driver.findElement(By.cssSelector("#description")).click();
        driver.findElement(By.cssSelector("#description")).sendKeys(advertisement.getDescription());

        Select garage = new Select(driver.findElement(By.cssSelector("#garageId")));
        garage.selectByValue(String.valueOf(advertisement.getGarageId().getId()));

        driver.findElement(By.cssSelector("#save")).click();
        return this;
    }

    public AdvertisementPage deleteAdvertisement(Integer id) {
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
