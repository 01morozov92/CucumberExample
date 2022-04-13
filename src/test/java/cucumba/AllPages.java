package cucumba;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.Page;
import pages.YandexMainPage;
import pages.YandexWeatherPage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AllPages extends Page {

    public AllPages(WebDriver driver) {
        super(driver);
    }

    private final List<Page> allPages = Arrays.asList(
            new YandexMainPage(driver),
            new YandexWeatherPage(driver)
    );

    public Page getPageByName(String pageName) {
        return allPages.stream()
                .distinct()
                .filter(page -> page.getPageName().equals(pageName))
                .findFirst()
                .orElseThrow(() -> new Error("Не найдена страница с наименованием: " + pageName));
    }

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    public WebElement getElementByName(String elementName) {
        return null;
    }
}
