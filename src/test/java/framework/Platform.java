package framework;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static framework.Configure.BROWSER;

@Log4j2
public class Platform {

    private static final String FIREFOX_DRIVER_PATH = new File("seleniumDrivers/geckodriver0.30.0.exe").getAbsolutePath();
    private static final String CHROME_DRIVER_PATH = new File("seleniumDrivers/chromedriver99.0.4844.51.exe").getAbsolutePath();


    private static final String FIREFOX_BROWSER = "firefox";
    private static final String CHROME_BROWSER = "chrome";

    private static Platform instance;

    public Platform() {
    }

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public String getLaunchedBrowser() throws Exception {
        if (isChrome()) {
            return CHROME_BROWSER;
        } else if (isFirefox()) {
            return FIREFOX_BROWSER;
        } else {
            throw new Exception("Cannot detect type of the Driver. Platform value " + getBrowser());
        }
    }

    public static boolean isChrome() {
        return isBrowser(CHROME_BROWSER);
    }

    public static boolean isFirefox() {
        return isBrowser(FIREFOX_BROWSER);
    }

    private ChromeOptions getChromeCaps() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        return new ChromeOptions().merge(capabilities);
    }

    private FirefoxOptions getFireFoxCaps() {
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        return new FirefoxOptions().merge(capabilities);
    }

    private static boolean isBrowser(String my_platform) {
        String platform = getBrowser();
        return my_platform.equals(platform);
    }

    private static String getBrowser() {
        return BROWSER;
    }

    public static String readProperty(String property) {
        Properties prop;
        String value = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream(new File("src/test/resources/config.properties")));

            value = prop.getProperty(property);

            if (value == null || value.isEmpty()) {
                throw new Exception("Value not set or empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public RemoteWebDriver getDriver() throws Exception {
        RemoteWebDriver driver;

        switch (getLaunchedBrowser().toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(getChromeCaps());
                break;
            case "firefox":
                driver = new FirefoxDriver(getFireFoxCaps());
                break;
            default:
                throw new Exception("Platform not supported");
        }
        return driver;
    }

}
