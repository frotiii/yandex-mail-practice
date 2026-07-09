package elements;

public class Label extends BaseElement {
    // класс для работы с элементами Label на странице,
    // позволяет находить их по значению атрибута title

    private static final String LABEL_BY_TITLE =
            "//span[@title='%s']";

    private Label(String xpath, String value) {
        super(findByXpath(String.format(xpath, value)));
    }

    public static Label byTitle(String title) {
        return new Label(LABEL_BY_TITLE, title);
    }
}