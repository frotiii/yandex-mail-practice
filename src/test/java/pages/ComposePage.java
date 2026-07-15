package pages;

import elements.Button;
import elements.Input;

// Класс, представляющий страницу создания нового письма
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

    // Заполняет поле "Кому" указанным адресом электронной почты
    public void fillTo(String email) {
        toInput.fill(email);
    }

    // Нажимает кнопку "Отправить" и возвращает экземпляр страницы входящих
    public InboxPage clickSend() {
        sendButton.click();
        return new InboxPage();
    }

    // Заполняет поле темы письма указанным значением
    public void fillSubject(String value) {
        subjectInput.fill(value);
    }

    // Заполняет тело письма указанным текстом (с полной заменой содержимого)
    public void fillBody(String text) {
        body.fill(text);
    }

    // "крестик", кнопка закрытия. тест 10
    public InboxPage closeCompose() {
        closeButton.click();
        return new InboxPage();
    }

    // Возвращает текст, отображаемый в поле "Кому"
    public String getTo() {
        return toInput.getText();
    }

    // Возвращает текущее значение атрибута value поля темы письма
    public String getSubject() {
        return subjectInput.getValue();
    }

    // Возвращает видимый текст из тела письма
    public String getBody() {
        return body.getText();
    }

    // тест 8
    // Имитирует выбор файла для вложения по указанному локальному пути
    public void clickAddFile(String filePath) {
        fileInput.fill(filePath);
    }

    // тест 3
    // Проверяет, отображается ли ожидаемый элемент получателя на странице
    public boolean isRecipientDisplayed() {
        return recipient.isDisplayed();
    }

    // тест 4
    // Дописывает текст в конец текущего содержимого тела письма без его очистки
    public void addBodyText(String text) {
        body.append(text);
    }
}