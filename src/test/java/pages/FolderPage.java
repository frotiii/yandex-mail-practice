package pages;

import elements.Label;

// Класс, представляющий страницу произвольной папки почтового ящика
public class FolderPage extends BasePage {

    // Проверяет, отображается ли письмо с указанной темой в текущей папке
    public boolean isLetterDisplayed(String subject) {
        Label letter = Label.byTitle(subject);
        return letter.isDisplayed();
    }
}