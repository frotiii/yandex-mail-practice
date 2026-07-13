package elements;

import static com.codeborne.selenide.Condition.visible;

public class Button extends BaseElement {
    // класс для работы с кнопками на странице
    // позволяет находить кнопки по xpath, нажимать на них и проверять их наличие

    private Button(String xpath) {
        super(findByXpath(xpath));
    }

    public void click() {
        baseElement.shouldBe(visible).click();
    }

    public void hover() {
        baseElement.hover();
    }

    public static Button byXpath(String xpath) {
        return new Button(xpath);
    }

}