package pages;

import elements.Button;
import elements.Checkbox;
import elements.Label;

// Класс, представляющий страницу "Корзина" и действия с удаленными письмами
public class TrashPage extends BasePage {

    private static final String LETTER_CHECKBOX = "//span[@title='%s']" +
                    "/ancestor::div[contains(@class,'MessageListItem__root')]" +
                    "//div[contains(@class,'MessageListItem__checkbox')]";

    private final Button moreButton = Button.byXpath("//button[@data-testid='overflow-button']");
    private final Button folderMenuButton = Button.byXpath("//div[@data-testid='toolbar_menu_action'][@id='folder']");
    private final Button inboxFolderItem = Button.byXpath("//div[@data-testid='folders-menu_tree_item'][@aria-label='Входящие']");

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