package pages;

import elements.Button;
import elements.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

// Класс, представляющий страницу авторизации Яндекс и процесс входа в аккаунт
public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private static final String MORE_BUTTON_XPATH = "//button[@data-testid='split-add-user-more-button']";
    private static final String LOGIN_MODE_BUTTON_XPATH = "//*[@data-testid='menu-option-switchToLogin']";
    private static final String LOGIN_INPUT_XPATH = "//input[@data-testid='text-field-input']";
    private static final String NEXT_LOGIN_BUTTON_XPATH = "//button[@data-testid='split-add-user-next-login']";
    private static final String PASSWORD_INPUT_XPATH = "//input[@placeholder='Пароль']";
    private static final String PASSWORD_NEXT_BUTTON_XPATH = "//button[@data-testid='password-next']";
    private static final String PASSWORD_LOGIN_BUTTON_XPATH = "//button[@data-testid='password-btn']";
    private static final String WEBAUTHN_LATER_BUTTON_XPATH = "//button[@data-testid='webauthn-reg-later-button']";
    private static final String IDENTIFICATION_SKIP_BUTTON_XPATH = "//button[@data-testid='identification-promo-start-skip-btn']";

    private final Button moreButton = Button.byXpath(MORE_BUTTON_XPATH);
    private final Button loginModeButton = Button.byXpath(LOGIN_MODE_BUTTON_XPATH);
    private final Input loginInput = Input.byXpath(LOGIN_INPUT_XPATH);
    private final Button nextLoginButton = Button.byXpath(NEXT_LOGIN_BUTTON_XPATH);
    private final Input passwordInput = Input.byXpath(PASSWORD_INPUT_XPATH);
    private final Button passwordNextButton = Button.byXpath(PASSWORD_NEXT_BUTTON_XPATH);
    private final Button passwordLoginButton = Button.byXpath(PASSWORD_LOGIN_BUTTON_XPATH);
    private final Button webauthnLaterButton = Button.byXpath(WEBAUTHN_LATER_BUTTON_XPATH);
    private final Button identificationSkipButton = Button.byXpath(IDENTIFICATION_SKIP_BUTTON_XPATH);

    // Возвращает экземпляр WebDriverWait с таймаутом 15 секунд для явных ожиданий
    private WebDriverWait waitForPage() {
        return new WebDriverWait(
                getWebDriver(),
                Duration.ofSeconds(15)
        );
    }

    // Выполняет полный процесс авторизации и возвращает страницу "Входящие"
    public InboxPage login(String login, String password) {
        validateCredentials(login, password);
        openAuthorizationPage();
        selectLoginWithPassword();
        enterLogin(login);
        enterPassword(password);
        skipWebauthnRegistration();
        skipIdentification();
        openMail();

        log.info("Авторизация успешно выполнена");
        return new InboxPage();
    }

    // Проверяет, что логин и пароль не пустые, иначе выбрасывает исключение
    private void validateCredentials(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин не указан в credentials.properties! Проверьте ключ yandex.login");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Пароль не указан в credentials.properties! Проверьте ключ yandex.password");
        }
    }

    // Открывает страницу авторизации Яндекса в браузере
    private void openAuthorizationPage() {
        log.info("Открываем страницу авторизации Яндекс");
        open("https://passport.yandex.ru/auth/");
    }

    // Переключает интерфейс на режим входа по логину и паролю через меню "Дополнительно"
    private void selectLoginWithPassword() {
        log.info("Открываем дополнительные способы авторизации");
        moreButton.click();

        log.info("Выбираем вход по логину и паролю");
        loginModeButton.click();
    }

    // Вводит логин в соответствующее поле и нажимает кнопку продолжения
    private void enterLogin(String login) {
        log.info("Вводим логин пользователя");
        loginInput.fill(login);

        log.info("Переходим к вводу пароля");
        nextLoginButton.click();
    }

    // Вводит пароль, подтверждает вход и ожидает завершения процесса авторизации или появления пропусков
    private void enterPassword(String password) {
        selectPasswordLoginIfAvailable();

        log.info("Вводим пароль");
        passwordInput.fill(password);

        log.info("Подтверждаем вход");
        passwordNextButton.click();

        waitForPage().until(driver -> webauthnLaterButton.isExists()
                || identificationSkipButton.isExists()
                || driver.getCurrentUrl().contains("mail.yandex")
        );
    }

    // Принудительно выбирает вход по паролю, если такая опция отображается на экране
    private void selectPasswordLoginIfAvailable() {
        if (passwordLoginButton.isExists()) {
            log.info("Выбираем вход с паролем");
            passwordLoginButton.click();

            waitForPage().until(driver ->
                    passwordInput.isExists()
            );
        }
    }

    // Пропускает предложение настроить вход без пароля, если оно появляется
    private void skipWebauthnRegistration() {
        if (webauthnLaterButton.isExists()) {
            log.info("Пропускаем настройку WebAuthn");
            webauthnLaterButton.click();

            waitForPage().until(driver ->
                    driver.getCurrentUrl().contains("id.yandex.ru")
            );
        }
    }

    // Пропускает экран дополнительной идентификации, если он отображается
    private void skipIdentification() {
        if (identificationSkipButton.isExists()) {
            log.info("Пропускаем дополнительную идентификацию");
            identificationSkipButton.click();

            waitForPage().until(driver ->
                    !identificationSkipButton.isExists() || driver.getCurrentUrl().contains("mail.yandex")
            );
        }
    }

    // Выполняет переход на главную страницу Яндекс Почты после успешной авторизации
    private void openMail() {
        log.info("Переходим в Яндекс Почту");
        open("https://mail.yandex.ru");
    }
}