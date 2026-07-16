package pages;

import elements.Button;

// Класс, представляющий страницу просмотра содержимого конкретного письма
public class MailPage extends BasePage {

    // ===== XPath и другие локаторы (константы) =====
    private static final String REPLY_BUTTON_XPATH = "//button[@aria-label='Ответить']" +
            " | //button[.//*[normalize-space(text())='Ответить']]";

    private static final String SENDER_BUTTON_XPATH = "//*[not(self::title)" +
            " and normalize-space(text())='deikinaang0707@yandex.ru']";

    private static final String FORWARD_BUTTON_XPATH = "//button[@aria-label='Переслать']" +
            " | //button[.//*[normalize-space(text())='Переслать']]";

    private static final String LETTER_BY_SUBJECT = "//div[contains(@class,'MessageListItem__root')]" +
            "[contains(normalize-space(.),'%s')]";

    private static final String SENDER_BY_EMAIL = "//*[not(self::title)" +
            " and normalize-space(text())='%s']";

    private static final String FORWARD_LETTER_PREFIX = "//div[contains(@class,'MessageListItem__root')]" +
            "[contains(normalize-space(.),'Fwd:')]" +
            "[contains(normalize-space(.),'%s')]";

    // ===== Элементы страницы =====
    private final Button replyButton = Button.byXpath(REPLY_BUTTON_XPATH);
    private final Button senderButton = Button.byXpath(SENDER_BUTTON_XPATH);
    private final Button forwardButton = Button.byXpath(FORWARD_BUTTON_XPATH);

    // ===== Методы =====

    // Проверяет, что отправитель письма соответствует ожидаемому адресу
    public boolean isSenderCorrect() {
        return senderButton.isDisplayed();
    }

    // Нажимает кнопку "Ответить" и возвращает страницу создания ответного письма
    public ComposePage clickReply() {
        replyButton.click();
        return new ComposePage();
    }

    // Проверяет наличие письма с указанной темой в текущем списке
    public boolean isLetterPresent(String subject) {
        return Button.byXpath(
                String.format(LETTER_BY_SUBJECT, subject)
        ).isDisplayed();
    }

    // Проверяет наличие пересланного письма (с префиксом "Fwd:") и указанной темой в списке
    public boolean isForwardLetterPresent(String subject) {
        return Button.byXpath(
                String.format(FORWARD_LETTER_PREFIX, subject)
        ).isDisplayed();
    }

    // Проверяет, что отправитель письма соответствует переданному адресу электронной почты
    public boolean isSenderCorrect(String email) {
        return Button.byXpath(
                String.format(SENDER_BY_EMAIL, email)
        ).isDisplayed();
    }

    // Нажимает кнопку "Переслать" и возвращает страницу создания пересылаемого письма
    public ComposePage clickForward() {
        forwardButton.click();
        return new ComposePage();
    }
}