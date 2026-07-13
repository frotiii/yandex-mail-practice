package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;
import pages.MailPage;

import java.io.File;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailSendingTests extends BaseTest {
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

    //Тест отсправки письма с приложенным файлом. Исходный файл находится в папке src\test\resources.
    //Добавляение файла происходит не через проводник, а через input атрибут страницы (файл загружается напрямую)
    //В конце теста осущеставляется проверка: происходит переход на страницу отправленных писем
    //и по тексту темы письма оно ищется на странице
    @Test
    public void test2_SendWithFile(){
        logger.info("ТЕСТ 2: ОТПРАВКА ПИСЬМА С ВЛОЖЕННЫМ ФАЙЛОМ");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo("uipractice-test2@yandex.ru");
        logger.info("2. Заполнено поле 'Кому': uipractice-test2@yandex.ru");

        composePage.fillSubject("Письмо с вложением");
        logger.info("3. Заполнено поле 'Тема': Письмо с вложением");

        composePage.fillBody("Отправляю файл с отчётом");
        logger.info("4. Заполнен текст письма");

        String pathFile = "src\\test\\resources\\report.pdf";
        File file = new File(pathFile);
        composePage.clickAddFile(file.getAbsolutePath());

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");
        System.out.println("Нажата кнопка 'Отправить'");

        inboxPage.openSent();
        assertThat(inboxPage.isLetterPresent("Письмо с вложением")).isTrue();

        logger.info("ТЕСТ 2 ЗАВЕРШЁН УСПЕШНО");
    }

    @Test
    public void Test3() {
        logger.info("=== Тест 1: Проверка ответа на полученное письмо ===");

        logger.info("Шаг 1: Открываем папку 'Входящие'");
        inboxPage.openInbox();

        logger.info( "Шаг 2-3: Ищем письмо от {} с темой '{}'", SENDER_EMAIL, SUBJECT );

        MailPage mailPage = inboxPage.openLetter(SUBJECT);
        logger.info("Проверка: письмо открыто и получено от нужного отправителя");

        assertThat(mailPage.isSenderCorrect()).as("Письмо должно быть получено от адреса '" + SENDER_EMAIL + "'")
                .isTrue();

        logger.info("Шаг 4: Нажимаем кнопку 'Ответить'");
        ComposePage composePage = mailPage.clickReply();

        logger.info("Проверка автозаполнения полей ответного письма");

        assertThat(composePage.isRecipientDisplayed())
                .as("Поле 'Кому' должно содержать адрес отправителя")
                .isTrue();

        assertThat(composePage.getSubject())
                .as("Поле 'Тема' должно содержать тему ответного письма")
                .isEqualTo(REPLY_SUBJECT);

        logger.info("Шаг 5: Вводим текст ответа");
        composePage.fillBody(REPLY_TEXT);

        assertThat(composePage.getBody())
                .as("Текст ответа должен отображаться в теле письма")
                .contains(REPLY_TEXT);

        logger.info("Шаг 6: Отправляем письмо");
        inboxPage = composePage.clickSend();
        sleep(5000);

        logger.info("Шаг 7: Открываем папку 'Отправленные'");
        MailPage sentPage = inboxPage.openSent();
        sleep(3000);

        assertThat(sentPage.isLetterPresent(SUBJECT))
                .as("Письмо-ответ с темой '%s' должно находиться в папке 'Отправленные'", SUBJECT)
                .isTrue();

        logger.info("=== Тест 1 успешно завершён ===");
    }


    @Test
    public void Test4() {
        logger.info("=== Тест 2: Проверка пересылки полученного письма третьему пользователю ===");

        logger.info("Шаг 1: Открываем папку 'Входящие'");
        inboxPage.openInbox();

        logger.info( "Шаг 2-3: Ищем письмо от {} с темой '{}'", FORWARD_SENDER_EMAIL, FORWARD_SUBJECT );
        MailPage mailPage = inboxPage.openLetter(FORWARD_SUBJECT);
        logger.info( "Проверка: письмо открыто и получено от нужного отправителя" );

        assertThat(mailPage.isSenderCorrect(FORWARD_SENDER_EMAIL))
                .as("Письмо должно быть получено от адреса 'FORWARD_SENDER_EMAIL'").isTrue();

        logger.info("Шаг 4: Нажимаем кнопку 'Переслать'");
        ComposePage composePage = mailPage.clickForward();
        logger.info( "Проверка: поле 'Кому' не заполнено автоматически" );
        assertThat(composePage.getTo()).as("При пересылке поле 'Кому' должно быть пустым").isBlank();
        logger.info( "Проверка темы пересылаемого письма" );

        assertThat(composePage.getSubject())
                .as("Поле 'Тема' должно начинаться с префикса 'Fwd:'").startsWith("Fwd:");

        assertThat(composePage.getSubject())
                .as("Поле 'Тема' должно содержать тему исходного письма").contains(FORWARD_SUBJECT);

        logger.info( "Шаг 5: Вводим адрес получателя {}", FORWARD_RECIPIENT );
        composePage.fillTo(FORWARD_RECIPIENT);

        assertThat(composePage.getTo())
                .as("Поле 'Кому' должно содержать адрес получателя").containsIgnoringCase(FORWARD_RECIPIENT);

        logger.info( "Шаг 6: Добавляем комментарий к пересылаемому письму" );
        composePage.addBodyText(FORWARD_TEXT);

        assertThat(composePage.getBody())
                .as("Комментарий должен отображаться в теле письма").contains(FORWARD_TEXT);

        logger.info("Шаг 7: Отправляем письмо");
        inboxPage = composePage.clickSend();
        sleep(5000);
        logger.info("Проверяем письмо в папке 'Отправленные'");
        MailPage sentPage = inboxPage.openSent();
        sleep(3000);

        assertThat(sentPage.isForwardLetterPresent(FORWARD_REPLY_SUBJECT))
                .as("Пересланное письмо с темой '%s' должно находиться в папке 'Отправленные'",
                        FORWARD_REPLY_SUBJECT).isTrue();

        logger.info("=== Тест 2 успешно завершён ===");
    }

    //Тест отправкуи письма с пустой темой. Поле тема ничем не заполняется, остальные части письма заполняются
    //всеми нужными данными. В конце теста осущеставляется проверка: происходит переход на страницу отправленных писем
    //и по тексту тела письма оно ищется на странице
    @Test
    public void test8_sendEmailWithNoTopic() {
        logger.info("ТЕСТ 8: ОТПРАВКА ПИСЬМА БЕЗ ТЕМЫ");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo("uipractice-test2@yandex.ru");
        logger.info("2. Заполнено поле 'Кому': uipractice-test2@yandex.ru");

        composePage.fillSubject("");
        logger.info("3. Заполнено поле 'Тема': ");

        composePage.fillBody("Это письмо отправлено без темы");
        logger.info("4. Заполнен текст письма");

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");
        System.out.println("Нажата кнопка 'Отправить'");

        inboxPage.openSent();
        assertThat(inboxPage.isLetterPresent("Это письмо отправлено без темы")).isTrue();

        logger.info("ТЕСТ 8 ЗАВЕРШЁН УСПЕШНО");
    }

}
