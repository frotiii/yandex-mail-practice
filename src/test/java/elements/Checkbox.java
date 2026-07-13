package elements;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

public class Checkbox extends BaseElement {
    // класс для работы с чекбоксами
    // позволяет находить кнопки по xpath

    private Checkbox(SelenideElement element) {
        super(element);
    }

    public void check() {
        baseElement.shouldBe(visible).click();
    }

    public static Checkbox byXpath(String xpath) {
        return new Checkbox(findByXpath(xpath));
    }
}