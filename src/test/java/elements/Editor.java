package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Editor extends BaseElement {
    // класс для работы с текстовым редактором на странице
    // позволяет находить редактор по aria-label или классу, заполнять его

    private Editor(SelenideElement element) {
        super(element);
    }

    public static Editor byAriaLabel(String label) {
        return new Editor($("[aria-label='" + label + "']"));
    }

    public static Editor byClass(String className) {
        return new Editor($("." + className));
    }

    public void fill(String text) {
        baseElement.setValue(text);
    }

    // тест 4
    public void append(String text) { baseElement.sendKeys(text); }
}