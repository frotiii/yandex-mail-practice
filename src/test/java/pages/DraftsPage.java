package pages;

import elements.Button;
import elements.Label;

public class DraftsPage extends BasePage {

    public boolean isDraftPresent(String subject) {
        return Label.byTitle(subject).isDisplayed();
    }

    public ComposePage openDraft(String subject) {
        Button.byXpath(String.format(
                "//span[@title='%s']",
                subject
        )).click();

        return new ComposePage();
    }
}