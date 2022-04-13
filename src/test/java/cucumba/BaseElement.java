package cucumba;


import lombok.Builder;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Data
@Builder
public class BaseElement {

    /**
     * Наименование элемента используемого в сценарии
     */
    private String elementName;

    /**
     * локатор для element
     */
    private WebElement webElement;

}
