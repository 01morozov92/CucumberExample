package steps;

import framework.Platform;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.YandexMainPage;
import pages.YandexWeatherPage;

@NoArgsConstructor
public class MyStepDefs {
    private static final String baseURL = "https://yandex.ru/";

    protected RemoteWebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        driver.get(baseURL);
    }

    @After
    public void tierDown(){
        driver.quit();
    }

    @Тогда("^пользователь попадает на главную страницу Яндекса и нажимает на раздел Погода$")
    public void goToYandex() {
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        yandexMainPage.goToTemperatureForecast();
    }

    @Если("происходит переход на страницу Прогноз погоды и находит на ней прогноз на завтра")
    public void findTomorrowTemperatureTest() {
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        YandexWeatherPage yandexWeatherPage = new YandexWeatherPage(driver);
        yandexMainPage.goToTemperatureForecast();
        yandexWeatherPage.getTomorrowTemperature();
    }

    @Дано("пользователь получает с главной страницы Яндекса текущую температуру")
    @SneakyThrows
    public void findCurrentTemperatureTest() {
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        yandexMainPage.getCurrentTemperature();
        Thread.sleep(5000);
    }

    @Тогда("пользователь открывает страницу Яндекс")
    public void goToYaPage() {
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        System.out.println("успех?");
    }
}
