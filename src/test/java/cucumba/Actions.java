package cucumba;

import java.util.List;

public class Actions {

    protected BaseElement getElementByName(List<BaseElement> baseElements, String elementName) {
        return baseElements.stream()
                .filter(element -> element.getElementName().equals(elementName))
                .findFirst()
                .orElseThrow(() -> new Error("На странице не найден элемент для наименования: " + elementName));
    }
}
