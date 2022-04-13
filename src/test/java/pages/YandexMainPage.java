package pages;

import cucumba.BaseElement;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class YandexMainPage extends Page {

    public YandexMainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPageName() {
        return "Главная страница";
    }


    private List<BaseElement> elements = Arrays.asList(
            getCurrentTemperatureLoc(), getSearchBtn(), getSearchInput(), getWeatherLoc()
    );

    @Override
    public WebElement getElementByName(String elementName) {
        return elements.stream()
                .filter(element -> element.getElementName().equals(elementName))
                .findFirst()
                .orElseThrow(() -> new Error("На странице не найден элемент для наименования: " + elementName)).getWebElement();
    }

    @FindBy(xpath = "//input[@aria-label='Запрос']")
    WebElement globalInputFieldLoc;
    @FindBy(css = ".search2__button")
    WebElement searchBtn;
    @FindBy(css = ".mini-suggest__input")
    WebElement searchInput;
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

    private BaseElement getCurrentTemperatureLoc(){
        return BaseElement.builder()
                .webElement(currentTemperatureLoc)
                .elementName("Текущая температура")
                .build();
    }

    private BaseElement getSearchBtn(){
        return BaseElement.builder()
                .webElement(searchBtn)
                .elementName("Поиск")
                .build();
    }

   private BaseElement getWeatherLoc(){
        return BaseElement.builder()
                .webElement(weatherLoc)
                .elementName("Прогноз на завтра")
                .build();
    }

  private BaseElement getSearchInput(){
        return BaseElement.builder()
                .webElement(searchInput)
                .elementName("Поле поиска")
                .build();
    }
}
