package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pages.ComposePage;
import pages.LoginPage;
import pages.MailPage;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class DeikinaTest extends BaseTest {
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
    // тест 7
    private static final String SEARCH_KEYWORD = "Тестирование";
    private static final String SEARCH_SUBJECT = "Важное сообщение по проекту Тестирование";

    @Override
    @BeforeEach
    public void login() {
        logger.info("Авторизация пользователя uipractice-test2");

        LoginPage loginPage = new LoginPage();
        inboxPage = loginPage.login(REPLY_LOGIN, REPLY_PASSWORD);

        logger.info("Авторизация успешно выполнена");
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

    @Test
    public void Test7() {
        logger.info("=== Тест 3: Проверка поиска письма по ключевому слову в теме ===");

        logger.info("Шаг 1: Вводим в строку поиска '{}'", SEARCH_KEYWORD);
        inboxPage.openSearch().fillSearch("subject:" + SEARCH_KEYWORD);

        logger.info("Шаг 2: Запускаем поиск клавишей Enter");
        inboxPage.submitSearch();

        logger.info("Шаг 3: Ожидаем результаты поиска");
        sleep(3000);

        assertThat(inboxPage.isSearchResultPresent(SEARCH_SUBJECT))
                .as("В результатах поиска должно отображаться письмо с темой '%s'", SEARCH_SUBJECT).isTrue();

        logger.info("=== Тест 3 успешно завершён ===");
    }
}