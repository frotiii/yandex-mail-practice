package elements;

public class Input extends BaseElement {
    // Класс для работы с полями ввода на странице
    // позволяет находить input по классу, имени или xpath и заполнять их значением

    // Шаблон XPath для поиска поля ввода по атрибуту name
    private static final String INPUT_BY_NAME =
            "//input[@name='%s']";

    // Шаблон XPath для поиска по точному совпадению пути
    private static final String INPUT_BY_XPATH =
            "%s";

    // Шаблон XPath для поиска элемента по частичному совпадению класса
    private static final String ELEMENT_BY_CLASS =
            "//*[contains(@class,'%s')]";

    // Конструктор, инициализирующий поле ввода по отформатированному XPath
    private Input(String xpath, String value) {
        super(findByXpath(String.format(xpath, value)));
    }

    // Полностью очищает и заполняет поле ввода указанным значением
    public void fill(String value) {
        baseElement.setValue(value);
    }

    // тест 4
    // Добавляет (дописывает) текст в конец текущего значения поля ввода без очистки
    public void append(String text) { baseElement.sendKeys(text); }

    // Фабричный метод для создания экземпляра поля ввода по имени класса (class)
    public static Input byClass(String className) {
        return new Input(ELEMENT_BY_CLASS, className);
    }

    // Фабричный метод для создания экземпляра поля ввода по атрибуту name
    public static Input byName(String name) {
        return new Input(INPUT_BY_NAME, name);
    }

    // Фабричный метод для создания экземпляра поля ввода по точному XPath
    public static Input byXpath(String xpath) {
        return new Input(INPUT_BY_XPATH, xpath);
    }
}
