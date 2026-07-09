package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import pages.InboxPage;
import pages.LoginPage;

import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected static String LOGIN;
    protected static String PASSWORD;

    protected InboxPage inboxPage;

    static {
        try (InputStream input = BaseTest.class.getClassLoader()
                .getResourceAsStream("credentials.properties")) {

            Properties props = new Properties();
            props.load(input);

            LOGIN = props.getProperty("yandex.login");
            PASSWORD = props.getProperty("yandex.password");

        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить credentials.properties", e);
        }
    }


    @BeforeAll
    public static void setup() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }


    @BeforeEach
    public void login() {
        logger.info("Авторизация в Яндекс Почте");

        LoginPage loginPage = new LoginPage();
        inboxPage = loginPage.login(LOGIN, PASSWORD);

        logger.info("Авторизация успешна");
    }
}