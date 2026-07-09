package elements;

public class Input extends BaseElement {
    // класс для работы с полями ввода на странице
    // позволяет находить input по классу, имени или xpath и заполнять их значением

    private static final String INPUT_BY_CLASS =
            "//input[contains(@class,'%s')]";

    private static final String INPUT_BY_NAME =
            "//input[@name='%s']";

    private static final String INPUT_BY_XPATH =
            "%s";

    private Input(String xpath, String value) {
        super(findByXpath(String.format(xpath, value)));
    }

    public void fill(String value) {
        baseElement.setValue(value);
    }

    public static Input byClass(String className) {
        return new Input(INPUT_BY_CLASS, className);
    }

    public static Input byName(String name) {
        return new Input(INPUT_BY_NAME, name);
    }

    public static Input byXpath(String xpath) {
        return new Input(INPUT_BY_XPATH, xpath);
    }
}