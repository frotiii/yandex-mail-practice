package elements;

public class Label extends BaseElement {
    // Класс для работы с элементами Label на странице,
    // позволяет находить их по значению атрибута title

    // Шаблон XPath для поиска элемента span по точному совпадению атрибута title
    private static final String LABEL_BY_TITLE =
            "//span[@title='%s']";

    // Конструктор, инициализирующий метку по отформатированному XPath
    private Label(String xpath, String value) {
        super(findByXpath(String.format(xpath, value)));
    }

    // Фабричный метод для создания экземпляра метки по значению атрибута title
    public static Label byTitle(String title) {
        return new Label(LABEL_BY_TITLE, title);
    }
}