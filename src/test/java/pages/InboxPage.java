package pages;

import elements.Button;
import elements.Input;
import elements.Checkbox;

import static com.codeborne.selenide.Selenide.$$x;

public class InboxPage extends BasePage {

    private final Button composeButton = Button.byXpath("//button[@aria-label='Написать']");
    private final Button draftsButton = Button.byXpath("//a[@href='#draft']");
    private final Button sentButton = Button.byXpath("//a[@href='#sent']");

    private final Button deleteButton = Button.byXpath("//button[@data-id='delete']");

    // тест 7
    private final Input searchInput = Input.byXpath("//input[@placeholder='Поиск' or @aria-label='Поиск']");
    private final Button searchButton = Button.byXpath("//button[@aria-label='Поиск']");

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

    // тест 3
    private static final String LETTER_BY_SUBJECT =
            "//span[@title='%s']";

    // тест 7
    private static final String SEARCH_RESULT_BY_SUBJECT =
            "//div[contains(@class,'MessageListItem__root')]" +
                    "[.//span[@title='%s']]";

    private static final String SEARCH_RESULT_ROWS =
            "//div[contains(@class,'MessageListItem__root')]";

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

    public boolean isLetterPresent(String subject) {
        return elements.Label.byTitle(subject).isDisplayed();
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

    public InboxPage clickDelete() {
        deleteButton.click();
        return this;
    }

    public TrashPage openTrash() {
        Button.byXpath("//a[@href='#trash']").click();
        return new TrashPage();
    }

    public InboxPage openInbox() {
        Button.byXpath("//a[@href='#tabs/relevant']").click();
        return this;
    }

    //тест 3
    public MailPage openLetter(String subject) {
        Button.byXpath(String.format(LETTER_BY_SUBJECT, subject)).click();
        return new MailPage();
    }

    public MailPage openSent() {
        sentButton.click();
        return new MailPage();
    }

    // тест 7
    public InboxPage fillSearch(String keyword) {
        searchInput.fill(keyword);
        return this;
    }

    public boolean isSearchResultPresent(String subject) {
        return Button.byXpath(
                String.format(SEARCH_RESULT_BY_SUBJECT, subject)
        ).isDisplayed();
    }

    public InboxPage openSearch() {
        searchButton.click();
        return this;
    }

    public InboxPage submitSearch() {
        searchInput.pressEnter();
        return this;
    }
}