package framework;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Core {

    protected WebDriver driver;
    private static final String baseURL = "https://yandex.ru/";

    @Before
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        driver.get(baseURL);
    }

    @After
    public void tierDown(){
        driver.quit();
    }
}
