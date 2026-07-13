package pages;

import elements.Button;

public class MailPage extends BasePage {

    private final Button replyButton =
            Button.byXpath("//button[@aria-label='Ответить']" +
                    " | //button[.//*[normalize-space(text())='Ответить']]");

    private final Button senderButton =
            Button.byXpath(
                    "//*[not(self::title)" +
                            " and normalize-space(text())='deikinaang0707@yandex.ru']"
            );

    private final Button forwardButton =
            Button.byXpath("//button[@aria-label='Переслать']" +
                    " | //button[.//*[normalize-space(text())='Переслать']]");

    private static final String LETTER_BY_SUBJECT =
            "//div[contains(@class,'MessageListItem__root')]" +
                    "[contains(normalize-space(.),'%s')]";

    public boolean isSenderCorrect() {
        return senderButton.isDisplayed();
    }

    public ComposePage clickReply() {
        replyButton.click();
        return new ComposePage();
    }

    public boolean isLetterPresent(String subject) {
        return Button.byXpath(
                String.format(LETTER_BY_SUBJECT, subject)
        ).isDisplayed();
    }

    public boolean isForwardLetterPresent(String subject) {
        return Button.byXpath(
                "//div[contains(@class,'MessageListItem__root')]" +
                        "[contains(normalize-space(.),'Fwd:')]" +
                        "[contains(normalize-space(.),'" + subject + "')]"
        ).isDisplayed();
    }

    public boolean isSenderCorrect(String email) {
        return Button.byXpath(
                "//*[not(self::title)" +
                        " and normalize-space(text())='" + email + "']"
        ).isDisplayed();
    }

    public ComposePage clickForward() {
        forwardButton.click();
        return new ComposePage();
    }

}