package pages;

import elements.Label;

public class FolderPage extends BasePage {

    public boolean isLetterDisplayed(String subject) {
        Label letter = Label.byTitle(subject);
        return letter.isDisplayed();
    }
}