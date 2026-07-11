package pages;

import elements.Button;
import elements.Editor;
import elements.Input;

public class ComposePage extends BasePage {

    private final Input to = Input.byXpath("//div[@aria-label='Кому']");
    private final Input subject = Input.byName("subject");
    private final Editor body = Editor.byClass("cke_editable");

    private final Button closeButton = Button.byXpath("//button[@aria-label='Закрыть']");
    private final Button sendButton = Button.byXpath("//button[contains(@class, 'Button2 Button2_view_action Button2_size_l')]");
    private final Input fileInput = Input.byXpath("//input[contains(@class, 'qa-Compose-FileInput2')]");

    // тест 4
    private final Button recipient = Button.byXpath("//*[@data-email='deikinaang0707@yandex.ru']" +
            " | //div[@aria-label='Кому']" + "[contains(.,'deikinaang0707@yandex.ru')]");

    public ComposePage fillTo(String email) {
        to.fill(email);
        return this;
    }

    public InboxPage clickSend() {
        sendButton.click();
        return new InboxPage();
    }

    public ComposePage fillSubject(String value) {
        subject.fill(value);
        return this;
    }

    public ComposePage fillBody(String text) {
        body.fill(text);
        return this;
    }

    // "крестик", кнопка закрытия. тест 10
    public InboxPage closeCompose() {
        closeButton.click();
        return new InboxPage();
    }

    public String getTo() {
        return to.getText();
    }

    public String getSubject() {
        return subject.getValue();
    }

    public String getBody() {
        return body.getText();
    }

    // тест 8
    public ComposePage clickAddFile(String filePath) {
        fileInput.fill(filePath);
        return this;
    }

    // тест 3
    public boolean isRecipientDisplayed() {
        return recipient.isDisplayed();
    }

    // тест 4
    public ComposePage addBodyText(String text) {
        body.append(text);
        return this;
    }
}