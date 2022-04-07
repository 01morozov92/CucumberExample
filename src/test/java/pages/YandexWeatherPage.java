package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YandexWeatherPage extends Page {

    @FindBy(xpath = "//div[text()='Сегодня']/ancestor::div[1]/following-sibling::div[2]//span[text()='днём']/following-sibling::span[contains(@class,'temp__value')]")
    WebElement tomorrowTemperatureLoc;

    public YandexWeatherPage(WebDriver driver) {
        super(driver);
    }

    public void getTomorrowTemperature() {
        String tomorrowTemperature;
        tomorrowTemperature = waitForElementPresent(tomorrowTemperatureLoc, "").getText();
        System.out.println("Tomorrow is " + tomorrowTemperature);

    }
}
