package pages;

import cucumba.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class YandexWeatherPage extends Page {

    @FindBy(xpath = "//div[text()='Сегодня']/ancestor::div[1]/following-sibling::div[2]//span[text()='днём']/following-sibling::span[contains(@class,'temp__value')]")
    WebElement tomorrowTemperatureLoc;

    @Override
    public String getPageName() {
        return "Яндекс погода";
    }

    private List<BaseElement> elements = Arrays.asList(

    );

    @Override
    public WebElement getElementByName(String elementName) {
        return elements.stream()
                .filter(element -> element.getElementName().equals(elementName))
                .findFirst()
                .orElseThrow(() -> new Error("На странице не найден элемент для наименования: " + elementName)).getWebElement();

    }

    public YandexWeatherPage(WebDriver driver) {
        super(driver);
    }

    public void getTomorrowTemperature() {
        String tomorrowTemperature;
        tomorrowTemperature = waitForElementPresent(tomorrowTemperatureLoc, "").getText();
        System.out.println("Tomorrow is " + tomorrowTemperature);

    }
}
