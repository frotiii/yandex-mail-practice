package pages;

import elements.Button;
import elements.Checkbox;
import elements.Label;

// Класс, представляющий страницу "Корзина" и действия с удаленными письмами
public class TrashPage extends BasePage {

    // ===== XPath и другие локаторы (константы) =====
    private static final String LETTER_CHECKBOX =
            "//span[@title='%s']" +
                    "/ancestor::div[contains(@class,'MessageListItem__root')]" +
                    "//div[contains(@class,'MessageListItem__checkbox')]";

    private static final String MORE_BUTTON_XPATH = "//button[@data-testid='overflow-button']";
    private static final String FOLDER_MENU_BUTTON_XPATH = "//div[@data-testid='toolbar_menu_action'][@id='folder']";
    private static final String INBOX_FOLDER_ITEM_XPATH = "//div[@data-testid='folders-menu_tree_item'][@aria-label='Входящие']";

    // ===== Элементы страницы =====
    private final Button moreButton = Button.byXpath(MORE_BUTTON_XPATH);
    private final Button folderMenuButton = Button.byXpath(FOLDER_MENU_BUTTON_XPATH);
    private final Button inboxFolderItem = Button.byXpath(INBOX_FOLDER_ITEM_XPATH);

    // ===== Методы =====

    // Отмечает чекбокс письма с указанной темой для последующих действий
    public void selectLetter(String subject) {
        Checkbox.byXpath(String.format(LETTER_CHECKBOX, subject)).check();
    }

    // Проверяет, отображается ли письмо с указанной темой в текущем списке Корзины
    public boolean isLetterPresent(String subject) {
        return Label.byTitle(subject).isDisplayed();
    }

    // Нажимает кнопку дополнительных действий (троеточие) на панели инструментов
    public void clickMore() {
        moreButton.click();
    }

    // Наводит курсор на кнопку меню папок, чтобы раскрыть список доступных папок
    public void hoverFolderMenu() {
        folderMenuButton.hover();
    }

    // Выбирает папку "Входящие" в меню для перемещения туда выбранного письма
    public void selectInboxFolder() {
        inboxFolderItem.click();
    }
}