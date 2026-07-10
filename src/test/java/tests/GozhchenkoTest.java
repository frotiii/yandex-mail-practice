package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;
import pages.TrashPage;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

public class GozhchenkoTest extends BaseTest {

    @Test
    public void test1_sendEmail() {
        logger.info("Тест 1. Создание и отправка письма");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(LOGIN);
        logger.info("2. Заполнено поле 'Кому': {}", LOGIN);

        composePage.fillSubject("Тестовое письмо");
        logger.info("3. Заполнено поле 'Тема': Тестовое письмо");

        composePage.fillBody("Привет! Это тестовое письмо для проверки отправки.");
        logger.info("4. Заполнен текст письма");

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");

        assertThat(inboxPage.isInboxDisplayed()).isTrue();
        logger.info("6. Проверка: письмо отправлено");

        String screenshotPath = screenshot("test1_sendEmail");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("Тест 1 успешно завершен");
    }

    @Test
    public void test5_deleteEmail() {
        logger.info("Тест 5. Проверка удаления письма");

        inboxPage.isInboxDisplayed();
        logger.info("1. Открыта папка 'Входящие'");

        inboxPage.selectLetter("Удалить это письмо");
        logger.info("2. Выбрано письмо 'Удалить это письмо'");

        inboxPage.clickDelete();
        logger.info("3. Нажата кнопка 'Удалить'");

        TrashPage trashPage = inboxPage.openTrash();
        logger.info("4. Открыта папка 'Удалённые'");

        sleep(1000);
        assertThat(trashPage.isLetterPresent("Удалить это письмо")).isTrue();
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

        trashPage.selectLetter("Письмо для восстановления");
        logger.info("2. Выбрано письмо 'Письмо для восстановления'");

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
        assertThat(inboxPage.isLetterPresent("Письмо для восстановления")).isTrue();
        logger.info("7. Проверка: письмо появилось во Входящих");

        String screenshotPath = screenshot("test6_restoreEmail");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("Тест 6 успешно заверше");
    }
}