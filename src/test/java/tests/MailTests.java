package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.screenshot;

public class MailTests extends BaseTest {

    @Test
    public void test1_sendEmail() {
        logger.info("=== ТЕСТ 1: Создание и отправка письма ===");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(LOGIN);
        logger.info("2. Заполнено поле 'Кому': {}", LOGIN);

        composePage.fillSubject("Тестовое письмо");
        logger.info("3. Заполнено поле 'Тема': Тестовое письмо");

        composePage.fillBody("Привет! Это тестовое письмо для учебной практики.");
        logger.info("4. Заполнен текст письма");

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");

        assertThat(inboxPage.isInboxDisplayed()).isTrue();
        logger.info("6. Проверка: письмо отправлено (открылись Входящие)");

        String screenshotPath = screenshot("test1_sendEmail");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("=== ТЕСТ 1 ЗАВЕРШЁН УСПЕШНО ===");
    }
}