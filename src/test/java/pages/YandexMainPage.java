package pages;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class YandexMainPage extends Page {

    @FindBy(xpath = "//input[@aria-label='Запрос']")
    WebElement globalInputFieldLoc;
    @FindBy(xpath = "//div[@class='weather__temp']")
    WebElement currentTemperatureLoc;
    @FindBy(xpath = "//div[@aria-label='Яндекс']")
    WebElement yandexLogoLoc;
    @FindBy(xpath = "//span[text() ='Найти']/parent::button")
    WebElement findButtonLoc;
    @FindBy(xpath = "//ul[@aria-label='Результаты поиска']")
    WebElement resultListLoc;
    @FindBy(xpath = "//div[@class='weather__header']//a[text()='Погода']")
    WebElement weatherLoc;

    public YandexMainPage(WebDriver driver) {
        super(driver);
    }

    public void searchThemeWithYandex(String expression) {
        waitForElementAndSendKeys(globalInputFieldLoc, expression, "");
        waitForElementAndClick(findButtonLoc, "Неудалось кликнуть на элемент - найти");
        Assertions.assertTrue(waitForElementPresent(resultListLoc, "Элемент - список результатов, не найден").isEnabled());
    }

    public void getCurrentTemperature() {
        String todayTemperature;
        todayTemperature = waitForElementPresent(currentTemperatureLoc, "Не удалось найти элемент - температура").getText();
        log.info("Today is " + todayTemperature);
    }

    public void goToTemperatureForecast() {
        waitForElementAndClick(weatherLoc, "Неудалось кликнуть на элемент - погода", 30);
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
    }

}
