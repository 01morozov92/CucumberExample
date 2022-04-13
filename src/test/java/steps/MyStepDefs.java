package steps;

import cucumba.AllPages;
import framework.Platform;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.YandexMainPage;
import pages.YandexWeatherPage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.Map;

import static cucumba.RunContext.RUN_CONTEXT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@NoArgsConstructor
public class MyStepDefs {
    private static final String baseURL = "https://yandex.ru/";
    protected RemoteWebDriver driver;
    private static AllPages allPages;

    @Before
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        allPages = new AllPages(driver);
        driver.get(baseURL);
    }

    @After
    public void tierDown() {
        driver.quit();
    }

    @Если("происходит переход на страницу Прогноз погоды и находит на ней прогноз на завтра")
    public void findTomorrowTemperatureTest(String elementName) {
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        YandexWeatherPage yandexWeatherPage = new YandexWeatherPage(driver);
        yandexMainPage.waitForElementAndClick(yandexMainPage.getElementByName(elementName), "Неудалось кликнуть на элемент - погода", 30);
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
        yandexWeatherPage.getTomorrowTemperature();
    }

    @Дано("пользователь на странице {string} сохраняет текст элемента {string}, в переменную контекста {string}")
    @SneakyThrows
    public void findCurrentTemperatureTest(String pageName, String elementName, String param) {
        String text = allPages.waitForElementPresent(allPages.getPageByName(pageName).getElementByName(elementName), "").getText();
        RUN_CONTEXT.put(param, text);
    }

    @Тогда("Сравнить две перменные из контекста 1: {string}, 2: {string}")
    public void compareTwoParamsFromContext(String firstParam, String secondParam) {
        Assertions.assertEquals(RUN_CONTEXT.get(firstParam), RUN_CONTEXT.get(secondParam));
    }

    @Тогда("Сравнить переменную из контекста: {string} с ожидаемым результатом: {string}")
    public void compareParamFromContextWithExpected(String firstParam, String secondParam) {
        Assertions.assertEquals(RUN_CONTEXT.get(firstParam), secondParam);
    }

    //Использовать для сравнения чисел >, ==, < и тд.
    @Тогда("Убедиться в истинности числового выражения {string}")
    public void checkEvalisTrue(String eval) throws ScriptException {
        String streval = eval;
        for (Map.Entry<String, Object> entry : RUN_CONTEXT.getContext().entrySet())  {
            if (streval.contains(entry.getKey().replaceAll("^.*:", ""))) {
                streval = streval.replace(entry.getKey().replaceAll("^.*:", ""), (String) RUN_CONTEXT.get(entry.getKey().replaceAll("^.*:", "")));
            }
        }
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        assertTrue("Expression is false", (Boolean) engine.eval(streval));
    }

    @Дано("пользователь на странице {string} кликает на элемент {string}")
    @SneakyThrows
    public void clickOnElement(String pageName, String elementName) {
        allPages.waitForElementAndClick(allPages.getPageByName(pageName).getElementByName(elementName),
                "Не удалось кликнуть на эелемент: " + elementName, 15);
    }

    @Дано("пользователь на странице {string} вводит текст: {string}, в элемент {string}")
    @SneakyThrows
    public void sendKeysValue(String pageName, String text, String elementName) {
        allPages.waitForElementAndSendKeys(allPages.getPageByName(pageName).getElementByName(elementName), text,
                "Не удалось ввести текст " + text + " в эелемент: " + elementName);
    }

    @SneakyThrows
    @Дано("дебаг")
    public void debug() {
        Thread.sleep(1000);
    }

    @Тогда("пользователь открывает страницу Яндекс")
    public void goToYaPage() {
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        System.out.println("успех?");
    }
}
