package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;
import pages.DraftsPage;
import pages.FolderPage;

import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

public class TestsOfAdditionalFunctions extends BaseTest {

    private static final String SEARCH_KEYWORD = "Тестирование";
    private static final String SEARCH_SUBJECT = "Важное сообщение по проекту Тестирование";

    private static final String FOLDER_NAME = "Проекты";
    private static final String LETTER_TO_MOVE = "Переместить в папку";

    private static final String DRAFT_SUBJECT = "Черновик";
    private static final String DRAFT_BODY = "Это письмо сохранено как черновик";

    // Тест поиска письма по ключевому слову в теме. Открывается строка поиска и вводится запрос по теме письма.
    // Поиск выполняется по заданному ключевому слову с параметром "subject:". После выполнения поиска ожидается
    // отображение результатов. В конце теста осуществляется проверка наличия в результатах поиска письма с ожидаемой темой.
    @Test
    public void test7_searchEmailBySubjectKeyword() {
        logger.info("Тест 7: Проверка поиска письма по ключевому слову в теме");

        logger.info("1. Вводим в строку поиска '{}'", SEARCH_KEYWORD);
        inboxPage.openSearch().fillSearch("subject:" + SEARCH_KEYWORD);

        logger.info("2. Запускаем поиск клавишей Enter");
        // Поиск запускается автоматически после ввода

        logger.info("3. Ожидаем результаты поиска");

        assertThat(inboxPage.isSearchResultPresent(SEARCH_SUBJECT))
                .as("В результатах поиска должно отображаться письмо с темой '%s'", SEARCH_SUBJECT)
                .isTrue();

        logger.info("Тест 7 завершён успешно");
    }

    // Тест создания папки и перемещения письма.
    // Создаётся новая папка "Проекты", после чего выбирается письмо из списка входящих
    // и перемещается в созданную папку. В конце теста осуществляется проверка:
    // в папке "Проекты" отображается письмо с указанной темой.
    @Test
    public void test9_createFolderAndMoveLetter() {
        logger.info("ТЕСТ 9: Создание папки и перемещение письма");

        inboxPage.clickAddFolder();
        logger.info("1. Нажата кнопка создания папки");

        inboxPage.fillFolderName(FOLDER_NAME);
        logger.info("2. Введено название папки: '{}'", FOLDER_NAME);

        inboxPage.clickCreateFolder();
        logger.info("3. Папка создана");

        inboxPage.selectLetter(LETTER_TO_MOVE);
        logger.info("4. Выбрано письмо '{}'", LETTER_TO_MOVE);

        inboxPage.clickMoveToFolder();
        logger.info("5. Нажата кнопка 'В папку'");

        inboxPage.selectFolder(FOLDER_NAME);
        logger.info("6. Выбрана папка '{}'", FOLDER_NAME);

        FolderPage folderPage = inboxPage.openFolder(FOLDER_NAME);
        logger.info("7. Открыта папка '{}'", FOLDER_NAME);

        assertThat(folderPage.isLetterDisplayed(LETTER_TO_MOVE))
                .as("Письмо должно отображаться в папке '%s'", FOLDER_NAME)
                .isTrue();
        logger.info("8. Проверка: письмо отображается в папке '{}'", FOLDER_NAME);

        String screenshotPath = screenshot("test9_createFolderAndMoveLetter");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("ТЕСТ 9 ЗАВЕРШЁН УСПЕШНО");
    }

    // Тест сохранения письма в черновиках.
    // Создаётся новое письмо, заполняются поля получателя, темы и текста.
    // После закрытия окна создания письма проверяется, что письмо сохранилось
    // в разделе "Черновики" и все заполненные данные были сохранены.
    @Test
    public void test10_DraftSaving() {
        logger.info("ТЕСТ 10: Сохранение письма в черновиках");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(LOGIN);
        logger.info("2. Заполнено поле 'Кому': {}", LOGIN);

        composePage.fillSubject(DRAFT_SUBJECT);
        logger.info("3. Заполнено поле 'Тема': {}", DRAFT_SUBJECT);

        composePage.fillBody(DRAFT_BODY);
        logger.info("4. Заполнен текст письма");

        inboxPage = composePage.closeCompose();
        logger.info("5. Окно создания письма закрыто, письмо сохранено");

        DraftsPage draftsPage = inboxPage.openDrafts();
        logger.info("6. Открыт раздел 'Черновики'");

        assertThat(draftsPage.isDraftPresent(DRAFT_SUBJECT))
                .as("Черновик с темой '%s' должен присутствовать", DRAFT_SUBJECT)
                .isTrue();
        logger.info("7. Проверка: черновик с темой '{}' присутствует", DRAFT_SUBJECT);

        ComposePage draft = draftsPage.openDraft(DRAFT_SUBJECT);
        logger.info("8. Открыт черновик");

        assertThat(draft.getTo()).contains(LOGIN);
        logger.info("9. Проверка поля 'Кому': {}", LOGIN);

        assertThat(draft.getSubject()).isEqualTo(DRAFT_SUBJECT);
        logger.info("10. Проверка поля 'Тема' сохранена");

        assertThat(draft.getBody()).contains(DRAFT_BODY);
        logger.info("11. Проверка текста письма сохранена");

        String screenshotPath = screenshot("test10_DraftSaving");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("ТЕСТ 10 ЗАВЕРШЁН УСПЕШНО");
    }
}