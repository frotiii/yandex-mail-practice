package pages;

import elements.Button;
import elements.Input;

public class ComposePage extends BasePage {

    private final Input toInput = Input.byXpath("//div[@aria-label='Кому']");
    private final Input subjectInput = Input.byName("subject");
    private final Input body = Input.byClass("cke_editable");

    private final Button closeButton = Button.byXpath("//button[@aria-label='Закрыть']");
    private final Button sendButton = Button.byXpath("//button[contains(@class, 'Button2 Button2_view_action Button2_size_l')]");
    private final Input fileInput = Input.byXpath("//input[contains(@class, 'qa-Compose-FileInput2')]");

    // тест 4
    private final Button recipient = Button.byXpath("//*[@data-email='deikinaang0707@yandex.ru']" +
            " | //div[@aria-label='Кому']" + "[contains(.,'deikinaang0707@yandex.ru')]");

    public void fillTo(String email) {
        toInput.fill(email);
    }

    public InboxPage clickSend() {
        sendButton.click();
        return new InboxPage();
    }

    public void fillSubject(String value) {
        subjectInput.fill(value);
    }

    public void fillBody(String text) {
        body.fill(text);
    }

    // "крестик", кнопка закрытия. тест 10
    public InboxPage closeCompose() {
        closeButton.click();
        return new InboxPage();
    }

    public String getTo() {
        return toInput.getText();
    }

    public String getSubject() {
        return subjectInput.getValue();
    }

    public String getBody() {
        return body.getText();
    }

    // тест 8
    public void clickAddFile(String filePath) {
        fileInput.fill(filePath);
    }

    // тест 3
    public boolean isRecipientDisplayed() {
        return recipient.isDisplayed();
    }

    // тест 4
    public void addBodyText(String text) {
        body.append(text);
    }
}