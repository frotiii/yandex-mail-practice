package pages;

import elements.Label;

public class DraftsPage extends BasePage {

    public boolean isDraftPresent(String subject) {
        return Label.byTitle(subject).isDisplayed();
    }

    public ComposePage openDraft(String subject) {
        Label.byTitle(subject).click();
        return new ComposePage();
    }
}