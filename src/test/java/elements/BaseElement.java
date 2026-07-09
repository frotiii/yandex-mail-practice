package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class BaseElement {

    protected final SelenideElement baseElement;

    protected BaseElement(SelenideElement element) {
        this.baseElement = element;
    }

    protected static SelenideElement findByXpath(String xpath) {
        return $x(xpath);
    }

    public void click() {
        baseElement.shouldBe(visible).click();
    }

    public String getText() {
        return baseElement.shouldBe(visible).getText();
    }

    public String getValue() {
        return baseElement.shouldBe(visible).getValue();
    }

    public boolean isDisplayed() {
        return baseElement.shouldBe(visible).isDisplayed();
    }

    public boolean isExists() {
        return baseElement.exists();
    }
}