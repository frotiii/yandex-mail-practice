package elements;

import static com.codeborne.selenide.Condition.visible;

public class Button extends BaseElement {
    // Класс для работы с кнопками на странице
    // позволяет находить кнопки по xpath, нажимать на них и проверять их наличие

    // Конструктор, инициализирующий кнопку по указанному XPath
    private Button(String xpath) {
        super(findByXpath(xpath));
    }

    // Выполняет клик по кнопке после проверки её видимости
    public void click() {
        baseElement.shouldBe(visible).click();
    }

    // Наводит курсор мыши на кнопку
    public void hover() {
        baseElement.hover();
    }

    // Фабричный метод для создания экземпляра кнопки по XPath
    public static Button byXpath(String xpath) {
        return new Button(xpath);
    }

}