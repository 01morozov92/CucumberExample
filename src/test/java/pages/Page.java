package pages;

import cucumba.BaseElement;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static junit.framework.TestCase.fail;

@Log4j2
@NoArgsConstructor
public abstract class Page {

    public abstract String getPageName();

    abstract public WebElement getElementByName(String elementName);

    protected WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    public boolean waitForElementNotPresent(WebElement locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        try {
            wait.until(ExpectedConditions.visibilityOf((locator)));
            return false;
        } catch (Exception tex) {
            tex.printStackTrace();
            return true;
        }
    }

    public void waitForElementAndClick(WebElement locator, String errorMessage, long timeoutInSeconds) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                log.info(String.format("Failed to click on element %s due to the StaleElementReferenceException", locator));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", locator));
                }
            }
        } while (!successfulClick);
    }

    public void waitForElementAndClick(WebElement locator, String errorMessage) {
        int numberOfRetry = 0;
        boolean successfulClick = false;
        do {
            try {
                WebElement element = waitForElementPresent(locator, errorMessage, 5);
                element.click();
                successfulClick = true;
            } catch (StaleElementReferenceException se) {
                numberOfRetry++;
                log.info(String.format("Failed to click on element %s due to the StaleElementReferenceException", locator));
                if (numberOfRetry == 3) {
                    se.printStackTrace();
                    fail(String.format("Cannot click on element %s due to the StaleElementReferenceException", locator));
                }
            }
        } while (!successfulClick);
    }

    public WebElement waitForElementPresent(WebElement locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        try {
            wait.until(ExpectedConditions.visibilityOf((locator)));
        } catch (Exception tex) {
            tex.printStackTrace();
            fail(errorMessage);
        }
        return locator;
    }

    public WebElement waitForElementPresent(WebElement locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean exist(WebElement element) {
        int j = 0;
        boolean webElementPassed = false;
        do {
            sleep(1000);
            try {
                element.isDisplayed();
                webElementPassed = true;
            } catch (TimeoutException | NoSuchElementException e) {
                e.printStackTrace();
                log.info(String.format("Элемент: %s не найден", element));
                return false;
            } catch (StaleElementReferenceException ex) {
                log.info("Ошибка при поиске элемента " + element);
                j += 1;
                if (j == 5) {
                    ex.printStackTrace();
                    log.info(String.format("Элемент: %s принял другое состояние", element));
                    Assert.fail("Элемент принял другое состояние");
                    return false;
                }
            }
        }
        while (!webElementPassed);
        return true;
    }

    public WebElement waitForElementAndSendKeys(WebElement locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndSendKeys(WebElement locator, String value, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, 5);
        element.sendKeys(value);
        return element;
    }
}
