package tests;

import org.junit.jupiter.api.Test;
import pages.TrashPage;

import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailDeletionTests extends BaseTest {

    private static final String LETTER_TO_DELETE = "Удалить это письмо";
    private static final String LETTER_TO_RESTORE = "Письмо для восстановления";

    @Test
    public void test5_deleteEmail() {
        logger.info("Тест 5. Проверка удаления письма");

        inboxPage.isInboxDisplayed();
        logger.info("1. Открыта папка 'Входящие'");

        inboxPage.selectLetter(LETTER_TO_DELETE);
        logger.info("2. Выбрано письмо '{}'", LETTER_TO_DELETE);

        inboxPage.clickDelete();
        logger.info("3. Нажата кнопка 'Удалить'");

        TrashPage trashPage = inboxPage.openTrash();
        logger.info("4. Открыта папка 'Удалённые'");

        sleep(1000);
        assertThat(trashPage.isLetterPresent(LETTER_TO_DELETE)).isTrue();
        logger.info("5. Проверка: письмо появилось в Корзине");

        String screenshotPath = screenshot("test5_deleteEmail");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("Тест 5 успешно завершен");
    }

    @Test
    public void test6_restoreEmail() {
        logger.info("Тест 6. Проверка восстановления письма из корзины");

        TrashPage trashPage = inboxPage.openTrash();
        logger.info("1. Открыта папка 'Удалённые'");

        trashPage.selectLetter(LETTER_TO_RESTORE);
        logger.info("2. Выбрано письмо '{}'", LETTER_TO_RESTORE);

        trashPage.clickMore();
        logger.info("3. Нажата кнопка 'Ещё'");

        sleep(500);
        trashPage.hoverFolderMenu();
        logger.info("4. Выбран пункт 'В папку'");

        sleep(500);
        trashPage.selectInboxFolder();
        logger.info("5. Выбрана папка 'Входящие'");

        inboxPage.openInbox();
        logger.info("6. Открыта папка 'Входящие'");

        sleep(1000);
        assertThat(inboxPage.isLetterPresent(LETTER_TO_RESTORE)).isTrue();
        logger.info("7. Проверка: письмо появилось во Входящих");

        String screenshotPath = screenshot("test6_restoreEmail");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("Тест 6 успешно завершен");
    }
}