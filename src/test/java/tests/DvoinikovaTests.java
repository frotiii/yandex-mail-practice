package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;
import pages.DraftsPage;
import pages.FolderPage;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.screenshot;

public class DvoinikovaTests extends BaseTest {

    @Test
    public void test10_DraftSaving() {
        logger.info("ТЕСТ 10: Сохранение письма в черновиках");

        ComposePage composePage = inboxPage.clickCompose();
        logger.info("1. Нажата кнопка 'Написать'");

        composePage.fillTo(LOGIN);
        logger.info("2. Заполнено поле 'Кому': {}", LOGIN);

        composePage.fillSubject("Черновик");
        logger.info("3. Заполнено поле 'Тема': Черновик");

        composePage.fillBody("Это письмо сохранено как черновик");
        logger.info("4. Заполнен текст письма");

        inboxPage = composePage.closeCompose();
        logger.info("5. Окно создания письма закрыто, письмо сохранено");

        DraftsPage draftsPage = inboxPage.openDrafts();
        logger.info("6. Открыт раздел 'Черновики'");

        assertThat(draftsPage.isDraftPresent("Черновик")).isTrue();
        logger.info("7. Проверка: черновик с темой 'Черновик' присутствует");

        ComposePage draft = draftsPage.openDraft("Черновик");
        logger.info("8. Открыт черновик");

        assertThat(draft.getTo()).contains(LOGIN);
        logger.info("9. Проверка поля 'Кому': {}", LOGIN);

        assertThat(draft.getSubject()).isEqualTo("Черновик");
        logger.info("10. Проверка поля 'Тема' сохранена");

        assertThat(draft.getBody()).contains("Это письмо сохранено как черновик");
        logger.info("11. Проверка текста письма сохранена");

        String screenshotPath = screenshot("test10_DraftSaving");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("ТЕСТ 10 ЗАВЕРШЁН УСПЕШНО");
    }

    @Test
    public void test9_createFolderAndMoveLetter() {
        logger.info("ТЕСТ 9: Создание папки и перемещение письма");

        inboxPage.clickAddFolder();
        logger.info("1. Нажата кнопка создания папки");

        inboxPage.fillFolderName("Проекты");
        logger.info("2. Введено название папки: 'Проекты'");

        inboxPage.clickCreateFolder();
        logger.info("3. Папка создана");

        inboxPage.selectLetter("Переместить в папку");
        logger.info("4. Выбрано письмо 'Переместить в папку'");

        inboxPage.clickMoveToFolder();
        logger.info("5. Нажата кнопка 'В папку'");

        inboxPage.selectFolder("Проекты");
        logger.info("6. Выбрана папка 'Проекты'");

        FolderPage folderPage = inboxPage.openFolder("Проекты");
        logger.info("7. Открыта папка 'Проекты'");

        assertThat(folderPage.isLetterDisplayed("Переместить в папку"))
                .isTrue();
        logger.info("8. Проверка: письмо отображается в папке 'Проекты'");

        String screenshotPath = screenshot("test9_createFolderAndMoveLetter");
        logger.info("Скриншот сохранён: {}", screenshotPath);

        logger.info("ТЕСТ 9 ЗАВЕРШЁН УСПЕШНО");
    }
}
