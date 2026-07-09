package elements;

import com.codeborne.selenide.SelenideElement;

public class Checkbox extends BaseElement {
    // класс для работы с чекбоксами
    // позволяет находить кнопки по xpath

    private Checkbox(SelenideElement element) {
        super(element);
    }

    public static Checkbox byXpath(String xpath) {
        return new Checkbox(findByXpath(xpath));
    }
}