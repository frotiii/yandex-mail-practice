package pages;

import elements.Button;
import elements.Checkbox;
import elements.Label;

public class TrashPage extends BasePage {

    private static final String LETTER_CHECKBOX =
            "//span[@title='%s']" +
                    "/ancestor::div[contains(@class,'MessageListItem__root')]" +
                    "//div[contains(@class,'MessageListItem__checkbox')]";

    private final Button moreButton = Button.byXpath("//button[@data-testid='overflow-button']");
    private final Button folderMenuButton = Button.byXpath("//div[@data-testid='toolbar_menu_action'][@id='folder']");
    private final Button inboxFolderItem = Button.byXpath("//div[@data-testid='folders-menu_tree_item'][@aria-label='Входящие']");

    public void selectLetter(String subject) {
        Checkbox.byXpath(String.format(LETTER_CHECKBOX, subject)).check();
    }

    public boolean isLetterPresent(String subject) {
        return Label.byTitle(subject).isDisplayed();
    }

    public void clickMore() {
        moreButton.click();
    }

    public void hoverFolderMenu() {
        folderMenuButton.hover();
    }

    public void selectInboxFolder() {
        inboxFolderItem.click();
    }
}