package elements;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;

public class Checkbox extends BaseElement {
    // Класс для работы с чекбоксами
    // позволяет находить кнопки по xpath

    // Конструктор, инициализирующий чекбокс переданным SelenideElement
    private Checkbox(SelenideElement element) {
        super(element);
    }

    // Переключает состояние чекбокса кликом по нему после проверки видимости
    public void check() {
        baseElement.shouldBe(visible).click();
    }

    // Фабричный метод для создания экземпляра чекбокса по указанному XPath
    public static Checkbox byXpath(String xpath) {
        return new Checkbox(findByXpath(xpath));
    }
}