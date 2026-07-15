package pages;

import elements.Button;
import elements.Input;
import elements.Checkbox;

// Класс, представляющий страницу "Входящие" и основные действия со списком писем
public class InboxPage extends BasePage {

    private final Button composeButton = Button.byXpath("//button[@aria-label='Написать']");
    private final Button draftsButton = Button.byXpath("//a[@href='#draft']");
    private final Button sentButton = Button.byXpath("//a[@href='#sent']");
    private final Button deleteButton = Button.byXpath("//button[@data-id='delete']");

    // тест 7
    private final Input searchInput = Input.byXpath("//input[@placeholder='Поиск' or @aria-label='Поиск']");
    private final Button searchButton = Button.byXpath("//button[@aria-label='Поиск']");

    // тест 9
    private final Button addFolderButton = Button.byXpath("//button[@aria-label='Создать папку']");
    private final Input folderName = Input.byXpath("//input[@placeholder='Название']");
    private final Button createFolderButton =
            Button.byXpath("//button[contains(@class,'qa-Toolbar-FolderModal-ActionButton')]");
    private final Button moveToFolderButton = Button.byXpath("//button[@id='folder']");


    private static final String FOLDER_IN_MENU = "//div[@data-testid='folders-menu_tree_item' and @aria-label='%s']";
    private static final String FOLDER = "//div[@aria-label='%s, папка']";
    private static final String LETTER_CHECKBOX =
            "//span[@title='%s']" +
                    "/ancestor::div[contains(@class,'MessageListItem__root')]" +
                    "//div[contains(@class,'MessageListItem__checkbox')]";

    // тест 3
    private static final String LETTER_BY_SUBJECT = "//span[@title='%s']";

    // тест 7
    private static final String SEARCH_RESULT_BY_SUBJECT =
            "//div[contains(@class,'MessageListItem__root')]" +
                    "[.//span[@title='%s']]";

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
        Button.byXpath("//a[@href='#trash']").click();
        return new TrashPage();
    }

    // Открывает папку "Входящие" (переход по соответствующей ссылке)
    public void openInbox() {
        Button.byXpath("//a[@href='#tabs/relevant']").click();
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
        return Button.byXpath(
                String.format(SEARCH_RESULT_BY_SUBJECT, subject)
        ).isDisplayed();
    }

    // Активирует строку поиска и возвращает текущий экземпляр страницы
    public InboxPage openSearch() {
        searchButton.click();
        return this;
    }
}