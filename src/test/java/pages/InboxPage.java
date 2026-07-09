package pages;

import elements.Button;
import elements.Input;
import elements.Checkbox;

public class InboxPage extends BasePage {

    private final Button composeButton = Button.byXpath("//button[@aria-label='Написать']");
    private final Button draftsButton = Button.byXpath("//a[@href='#draft']");
    private final Button sentButton = Button.byXpath("//a[@href='#sent']");

    // тест 9
    private final Button addFolderButton =
            Button.byXpath("//button[@aria-label='Создать папку']");
    private final Input folderName =
            Input.byXpath("//input[@placeholder='Название']");
    private final Button createFolderButton =
            Button.byXpath("//button[contains(@class,'qa-Toolbar-FolderModal-ActionButton')]");
    private final Button moveToFolderButton =
            Button.byXpath("//button[@id='folder']");


    private static final String FOLDER_IN_MENU =
            "//div[@data-testid='folders-menu_tree_item' and @aria-label='%s']";

    private static final String FOLDER =
            "//div[@aria-label='%s, папка']";

    private static final String LETTER_CHECKBOX =
            "//span[@title='%s']" +
                    "/ancestor::div[contains(@class,'MessageListItem__root')]" +
                    "//div[contains(@class,'MessageListItem__checkbox')]";

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

    // тест 9
    public InboxPage clickAddFolder() {
        addFolderButton.click();
        return this;
    }

    public InboxPage fillFolderName(String name) {
        folderName.fill(name);
        return this;
    }

    public InboxPage clickCreateFolder() {
        createFolderButton.click();
        return this;
    }

    public InboxPage selectLetter(String subject) {
        Checkbox.byXpath(String.format(LETTER_CHECKBOX, subject)).click();
        return this;
    }

    public InboxPage clickMoveToFolder() {
        moveToFolderButton.click();
        return this;
    }

    public InboxPage selectFolder(String name) {
        Button.byXpath(String.format(FOLDER_IN_MENU, name)).click();
        return this;
    }

    public FolderPage openFolder(String name) {
        Button.byXpath(String.format(FOLDER, name)).click();
        return new FolderPage();
    }


}