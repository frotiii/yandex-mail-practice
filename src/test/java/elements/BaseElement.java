package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

// Базовый класс для инкапсуляции действий с веб-элементами через Selenide
public class BaseElement {

    protected final SelenideElement baseElement;

    protected BaseElement(SelenideElement element) {
        this.baseElement = element;
    }

    // Возвращает видимый текст элемента
    public String getText() {
        return baseElement.shouldBe(visible).getText();
    }

    // Возвращает значение атрибута value элемента
    public String getValue() {
        return baseElement.shouldBe(visible).getValue();
    }

    // Проверяет, что элемент реально отображается на странице
    public boolean isDisplayed() {
        return baseElement.shouldBe(visible).isDisplayed();
    }

    // Проверяет наличие элемента в DOM-дереве
    public boolean isExists() {
        return baseElement.exists();
    }

    // Находит и возвращает элемент по указанному XPath
    protected static SelenideElement findByXpath(String xpath) {
        return $x(xpath);
    }
}