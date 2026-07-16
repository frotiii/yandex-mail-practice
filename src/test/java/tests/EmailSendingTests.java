package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;
import pages.MailPage;

import java.io.File;

import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailSendingTests extends BaseTest {

    // ===== Тестовые данные (константы) =====
    // тест 3
    private static final String SENDER_EMAIL = "deikinaang0707@yandex.ru";
    private static final String SUBJECT = "Вопрос по проекту";
    private static final String REPLY_SUBJECT = "Re: Вопрос по проекту";
    private static final String REPLY_TEXT = "Ответ на ваше письмо. Всё готово.";

    // тест 4
    private static final String FORWARD_SENDER_EMAIL = "deikinaang0707@yandex.ru";
    private static final String FORWARD_SUBJECT = "Договор";
    private static final String FORWARD_REPLY_SUBJECT = "Fwd: Договор";
    private static final String FORWARD_RECIPIENT = "nik-dolgikh1@yandex.com";
    private static final String FORWARD_TEXT = "Пересылаю вам договор для ознакомления";

    // тест 1
    private static final String TEST1_SUBJECT = "Тестовое письмо";
    private static final String TEST1_BODY = "Привет! Это тестовое письмо для проверки отправки.";

    // тест 2
    private static final String RECIPIENT_EMAIL = "uipractice-test2@yandex.ru";
    private static final String ATTACHMENT_SUBJECT = "Письмо с вложением";
    private static final String ATTACHMENT_BODY = "Отправляю файл с отчётом";
    private static final String FILE_PATH = "src\\test\\resources\\report.pdf";

    // тест 8
    private static final String NO_TOPIC_RECIPIENT = "uipractice-test2@yandex.ru";
    private static final String NO_TOPIC_BODY = "Это письмо отправлено без темы";

    @Test
    public void test1_sendEmail() {
        logger.info("Тест 1. Создание и отправка письма");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(LOGIN);
        logger.info("2. Заполнено поле 'Кому': {}", LOGIN);

        composePage.fillSubject(TEST1_SUBJECT);
        logger.info("3. Заполнено поле 'Тема': {}", TEST1_SUBJECT);

        composePage.fillBody(TEST1_BODY);
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
    public void test2_SendWithFile() {
        logger.info("ТЕСТ 2: ОТПРАВКА ПИСЬМА С ВЛОЖЕННЫМ ФАЙЛОМ");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(RECIPIENT_EMAIL);
        logger.info("2. Заполнено поле 'Кому': {}", RECIPIENT_EMAIL);

        composePage.fillSubject(ATTACHMENT_SUBJECT);
        logger.info("3. Заполнено поле 'Тема': {}", ATTACHMENT_SUBJECT);

        composePage.fillBody(ATTACHMENT_BODY);
        logger.info("4. Заполнен текст письма");

        File file = new File(FILE_PATH);
        composePage.clickAddFile(file.getAbsolutePath());

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");

        inboxPage.openSent();
        assertThat(inboxPage.isLetterPresent(ATTACHMENT_SUBJECT)).isTrue();

        logger.info("ТЕСТ 2 ЗАВЕРШЁН УСПЕШНО");
    }

    @Test
    public void test3_replyToReceivedEmail() {
        logger.info("Тест 3: Проверка ответа на полученное письмо");

        logger.info("1. Открываем папку 'Входящие'");
        inboxPage.openInbox();
        logger.info("Ищем письмо от {} с темой '{}'", SENDER_EMAIL, SUBJECT);

        MailPage mailPage = inboxPage.openLetter(SUBJECT);
        assertThat(mailPage.isSenderCorrect())
                .as("Письмо должно быть получено от адреса '" + SENDER_EMAIL + "'")
                .isTrue();

        logger.info("2. Нажимаем кнопку 'Ответить'");
        ComposePage composePage = mailPage.clickReply();

        logger.info("Проверяем автозаполнение полей ответного письма");
        assertThat(composePage.isRecipientDisplayed())
                .as("Поле 'Кому' должно содержать адрес отправителя")
                .isTrue();

        assertThat(composePage.getSubject())
                .as("Поле 'Тема' должно содержать тему ответного письма")
                .isEqualTo(REPLY_SUBJECT);

        logger.info("3. Вводим текст ответа");
        composePage.fillBody(REPLY_TEXT);
        assertThat(composePage.getBody())
                .as("Текст ответа должен отображаться в теле письма")
                .contains(REPLY_TEXT);

        logger.info("4. Отправляем письмо");
        inboxPage = composePage.clickSend();

        logger.info("5. Открываем папку 'Отправленные'");
        MailPage sentPage = inboxPage.openSent();

        assertThat(sentPage.isLetterPresent(SUBJECT))
                .as("Письмо-ответ с темой '%s' должно находиться в папке 'Отправленные'", SUBJECT)
                .isTrue();

        logger.info("Тест 3 завершён успешно");
    }

    @Test
    public void test4_forwardReceivedEmail() {
        logger.info("Тест 4: Проверка пересылки полученного письма третьему пользователю");

        logger.info("1. Открываем папку 'Входящие'");
        inboxPage.openInbox();

        logger.info("Ищем письмо от {} с темой '{}'", FORWARD_SENDER_EMAIL, FORWARD_SUBJECT);
        MailPage mailPage = inboxPage.openLetter(FORWARD_SUBJECT);

        assertThat(mailPage.isSenderCorrect(FORWARD_SENDER_EMAIL))
                .as("Письмо должно быть получено от адреса '" + FORWARD_SENDER_EMAIL + "'")
                .isTrue();

        logger.info("2. Нажимаем кнопку 'Переслать'");
        ComposePage composePage = mailPage.clickForward();

        logger.info("Проверяем, что поле 'Кому' не заполнено автоматически");
        assertThat(composePage.getTo())
                .as("При пересылке поле 'Кому' должно быть пустым")
                .isBlank();

        logger.info("Проверяем тему пересылаемого письма");
        assertThat(composePage.getSubject())
                .as("Поле 'Тема' должно начинаться с префикса 'Fwd:'")
                .startsWith("Fwd:");
        assertThat(composePage.getSubject())
                .as("Поле 'Тема' должно содержать тему исходного письма")
                .contains(FORWARD_SUBJECT);

        logger.info("3. Вводим адрес получателя {}", FORWARD_RECIPIENT);
        composePage.fillTo(FORWARD_RECIPIENT);
        assertThat(composePage.getTo())
                .as("Поле 'Кому' должно содержать адрес получателя")
                .containsIgnoringCase(FORWARD_RECIPIENT);

        logger.info("4. Добавляем комментарий к пересылаемому письму");
        composePage.addBodyText(FORWARD_TEXT);
        assertThat(composePage.getBody())
                .as("Комментарий должен отображаться в теле письма")
                .contains(FORWARD_TEXT);

        logger.info("5. Отправляем письмо");
        inboxPage = composePage.clickSend();

        logger.info("Проверяем письмо в папке 'Отправленные'");
        MailPage sentPage = inboxPage.openSent();

        assertThat(sentPage.isForwardLetterPresent(FORWARD_REPLY_SUBJECT))
                .as("Пересланное письмо с темой '%s' должно находиться в папке 'Отправленные'",
                        FORWARD_REPLY_SUBJECT)
                .isTrue();

        logger.info("Тест 4 завершён успешно");
    }

    @Test
    public void test8_sendEmailWithNoTopic() {
        logger.info("ТЕСТ 8: ОТПРАВКА ПИСЬМА БЕЗ ТЕМЫ");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(NO_TOPIC_RECIPIENT);
        logger.info("2. Заполнено поле 'Кому': {}", NO_TOPIC_RECIPIENT);

        composePage.fillSubject("");
        logger.info("3. Заполнено поле 'Тема': (пусто)");

        composePage.fillBody(NO_TOPIC_BODY);
        logger.info("4. Заполнен текст письма");

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");

        inboxPage.openSent();
        assertThat(inboxPage.isLetterPresent(NO_TOPIC_BODY)).isTrue();

        logger.info("ТЕСТ 8 ЗАВЕРШЁН УСПЕШНО");
    }
}