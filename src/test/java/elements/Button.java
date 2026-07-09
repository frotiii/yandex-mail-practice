package elements;

public class Button extends BaseElement {
    // класс для работы с кнопками на странице
    // позволяет находить кнопки по xpath, нажимать на них и проверять их наличие

    private Button(String xpath) {
        super(findByXpath(xpath));
    }

    public static Button byXpath(String xpath) {
        return new Button(xpath);
    }
}