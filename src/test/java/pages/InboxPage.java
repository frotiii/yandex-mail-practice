package pages;

import elements.Button;

public class InboxPage extends BasePage {

    private final Button composeButton = Button.byXpath("//button[@aria-label='Написать']");
    private final Button draftsButton = Button.byXpath("//a[@href='#draft']");
    private final Button sentButton = Button.byXpath("//a[@href='#sent']");

    public ComposePage clickCompose() {
        composeButton.click();
        return new ComposePage();
    }

    // открывает "черновики" из входящих писем; тест 10
    public DraftsPage openDrafts() {
        draftsButton.click();
        return new DraftsPage();
    }

    public boolean isInboxDisplayed() {
        return sentButton.isDisplayed();
    }
}