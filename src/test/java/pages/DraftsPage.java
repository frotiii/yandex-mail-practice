package pages;

import elements.Button;
import elements.Label;

// Класс, представляющий страницу "Черновики"
public class DraftsPage extends BasePage {

    // Проверяет, отображается ли черновик с указанной темой на странице
    public boolean isDraftPresent(String subject) {
        return Label.byTitle(subject).isDisplayed();
    }

    // Открывает черновик с указанной темой кликом по нему и возвращает страницу редактирования
    public ComposePage openDraft(String subject) {
        Button.byXpath(String.format(
                "//span[@title='%s']",
                subject
        )).click();

        return new ComposePage();
    }
}