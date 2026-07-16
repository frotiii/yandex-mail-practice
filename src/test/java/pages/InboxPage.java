package pages;

import elements.Button;
import elements.Input;
import elements.Checkbox;

// Класс, представляющий страницу "Входящие" и основные действия со списком писем
public class InboxPage extends BasePage {

    private static final String COMPOSE_BUTTON_XPATH = "//button[@aria-label='Написать']";
    private static final String DRAFTS_BUTTON_XPATH = "//a[@href='#draft']";
    private static final String SENT_BUTTON_XPATH = "//a[@href='#sent']";
    private static final String DELETE_BUTTON_XPATH = "//button[@data-id='delete']";

    private static final String SEARCH_INPUT_XPATH = "//input[@placeholder='Поиск' or @aria-label='Поиск']";
    private static final String SEARCH_BUTTON_XPATH = "//button[@aria-label='Поиск']";

    private static final String ADD_FOLDER_BUTTON_XPATH = "//button[@aria-label='Создать папку']";
    private static final String FOLDER_NAME_INPUT_XPATH = "//input[@placeholder='Название']";
    private static final String CREATE_FOLDER_BUTTON_XPATH =
            "//button[contains(@class,'qa-Toolbar-FolderModal-ActionButton')]";
    private static final String MOVE_TO_FOLDER_BUTTON_XPATH = "//button[@id='folder']";

    private static final String TRASH_LINK_XPATH = "//a[@href='#trash']";
    private static final String INBOX_LINK_XPATH = "//a[@href='#tabs/relevant']";

    private static final String FOLDER_IN_MENU = "//div[@data-testid='folders-menu_tree_item' and @aria-label='%s']";
    private static final String FOLDER = "//div[@aria-label='%s, папка']";
    private static final String LETTER_CHECKBOX =
            "//span[@title='%s']" +
                    "/ancestor::div[contains(@class,'MessageListItem__root')]" +
                    "//div[contains(@class,'MessageListItem__checkbox')]";

    private static final String LETTER_BY_SUBJECT = "//span[@title='%s']";

    private static final String SEARCH_RESULT_BY_SUBJECT =
            "//div[contains(@class,'MessageListItem__root')]" +
                    "[.//span[@title='%s']]";

    private final Button composeButton = Button.byXpath(COMPOSE_BUTTON_XPATH);
    private final Button draftsButton = Button.byXpath(DRAFTS_BUTTON_XPATH);
    private final Button sentButton = Button.byXpath(SENT_BUTTON_XPATH);
    private final Button deleteButton = Button.byXpath(DELETE_BUTTON_XPATH);

    private final Input searchInput = Input.byXpath(SEARCH_INPUT_XPATH);
    private final Button searchButton = Button.byXpath(SEARCH_BUTTON_XPATH);

    private final Button addFolderButton = Button.byXpath(ADD_FOLDER_BUTTON_XPATH);
    private final Input folderName = Input.byXpath(FOLDER_NAME_INPUT_XPATH);
    private final Button createFolderButton = Button.byXpath(CREATE_FOLDER_BUTTON_XPATH);
    private final Button moveToFolderButton = Button.byXpath(MOVE_TO_FOLDER_BUTTON_XPATH);


    // Нажимает кнопку "Написать" и возвращает страницу создания письма
    public ComposePage clickCompose() {
        composeButton.click();
        return new ComposePage();
    }

    // открывает "черновики" из входящих писем; тест 10
    public DraftsPage openDrafts() {
        draftsButton.click();
        return new DraftsPage();
    }

    // Проверяет базовую загрузку страницы (через видимость кнопки "Отправленные")
    public boolean isInboxDisplayed() {
        return sentButton.isDisplayed();
    }

    // Проверяет наличие письма с указанной темой в текущем списке
    public boolean isLetterPresent(String subject) {
        return elements.Label.byTitle(subject).isDisplayed();
    }

    // тест 9
    // Открывает модальное окно для создания новой папки
    public void clickAddFolder() {
        addFolderButton.click();
    }

    // Заполняет поле ввода названия новой папки
    public void fillFolderName(String name) {
        folderName.fill(name);
    }

    // Подтверждает создание новой папки нажатием соответствующей кнопки
    public void clickCreateFolder() {
        createFolderButton.click();
    }

    // Отмечает чекбокс письма с указанной темой для последующих действий
    public void selectLetter(String subject) {
        Checkbox.byXpath(String.format(LETTER_CHECKBOX, subject)).check();
    }

    // Открывает меню перемещения выбранного письма в другую папку
    public void clickMoveToFolder() {
        moveToFolderButton.click();
    }

    // Выбирает целевую папку в выпадающем меню перемещения
    public void selectFolder(String name) {
        Button.byXpath(String.format(FOLDER_IN_MENU, name)).click();
    }

    // Открывает указанную папку из бокового меню и возвращает её страницу
    public FolderPage openFolder(String name) {
        Button.byXpath(String.format(FOLDER, name)).click();
        return new FolderPage();
    }

    // Нажимает кнопку удаления для выбранных писем
    public void clickDelete() {
        deleteButton.click();
    }

    // Переходит в папку "Корзина" и возвращает соответствующую страницу
    public TrashPage openTrash() {
        Button.byXpath(TRASH_LINK_XPATH).click();
        return new TrashPage();
    }

    // Открывает папку "Входящие" (переход по соответствующей ссылке)
    public void openInbox() {
        Button.byXpath(INBOX_LINK_XPATH).click();
    }

    //тест 3
    // Открывает письмо с указанной темой и возвращает страницу просмотра письма
    public MailPage openLetter(String subject) {
        Button.byXpath(String.format(LETTER_BY_SUBJECT, subject)).click();
        return new MailPage();
    }

    // Переходит в папку "Отправленные" и возвращает страницу просмотра списка
    public MailPage openSent() {
        sentButton.click();
        return new MailPage();
    }

    // тест 7
    // Вводит поисковый запрос (включая операторы, например, "subject:") в поле поиска
    public void fillSearch(String keyword) {
        searchInput.fill(keyword);
    }

    // Проверяет, отображается ли письмо с указанной темой в результатах поиска
    public boolean isSearchResultPresent(String subject) {
        return Button.byXpath(String.format(SEARCH_RESULT_BY_SUBJECT, subject)).isDisplayed();
    }

    // Активирует строку поиска и возвращает текущий экземпляр страницы
    public InboxPage openSearch() {
        searchButton.click();
        return this;
    }
}