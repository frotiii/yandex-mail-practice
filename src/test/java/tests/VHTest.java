package tests;

import org.junit.jupiter.api.Test;
import pages.ComposePage;
import java.io.File;
import static com.codeborne.selenide.Selenide.sleep;


import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.screenshot;

public class VHTest extends BaseTest {

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
        sleep(1000);

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");
        System.out.println("Нажата кнопка 'Отправить'");
        sleep(1000);

        inboxPage.openSent();
        assertThat(inboxPage.isLetterPresent("Это письмо отправлено без темы")).isTrue();
        sleep(2000);


        logger.info("ТЕСТ 8 ЗАВЕРШЁН УСПЕШНО");
        System.out.println("ТЕСТ 8 ПРОШЕЛ");
    }

    @Test
    public void test2_SendWithPic(){
        logger.info("ТЕСТ 2: ОТПРАВКА ПИСЬМА С КАРТИНКОЙ");

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
        sleep(1000);

        inboxPage = composePage.clickSend();
        logger.info("5. Нажата кнопка 'Отправить'");
        System.out.println("Нажата кнопка 'Отправить'");
        sleep(2000);

        inboxPage.openSent();
        assertThat(inboxPage.isLetterPresent("Письмо с вложением")).isTrue();
        sleep(2000);


        logger.info("ТЕСТ 2 ЗАВЕРШЁН УСПЕШНО");
        System.out.println("ТЕСТ 2 ПРОШЕЛ");
    }
}
