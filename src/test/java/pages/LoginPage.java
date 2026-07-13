package pages;

import elements.Button;
import elements.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private final Button moreButton = Button.byXpath("//button[@data-testid='split-add-user-more-button']");

    private final Button loginModeButton = Button.byXpath("//*[@data-testid='menu-option-switchToLogin']");

    private final Input loginInput = Input.byXpath("//input[@data-testid='text-field-input']");

    private final Button nextLoginButton = Button.byXpath("//button[@data-testid='split-add-user-next-login']");

    private final Input passwordInput = Input.byXpath("//input[@placeholder='Пароль']");

    private final Button passwordNextButton = Button.byXpath("//button[@data-testid='password-next']");

    private final Button passwordLoginButton = Button.byXpath("//button[@data-testid='password-btn']");

    private final Button webauthnLaterButton = Button.byXpath("//button[@data-testid='webauthn-reg-later-button']");

    private final Button identificationSkipButton = Button.byXpath("//button[@data-testid='identification-promo-start-skip-btn']");

    private WebDriverWait waitForPage() {
        return new WebDriverWait(
                getWebDriver(),
                Duration.ofSeconds(15)
        );
    }

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

    private void validateCredentials(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException(
                    "Логин не указан в credentials.properties! " +
                            "Проверьте ключ yandex.login"
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Пароль не указан в credentials.properties! " +
                            "Проверьте ключ yandex.password"
            );
        }
    }

    private void openAuthorizationPage() {
        log.info("Открываем страницу авторизации Яндекс");
        open("https://passport.yandex.ru/auth/");
    }

    private void selectLoginWithPassword() {
        log.info("Открываем дополнительные способы авторизации");
        moreButton.click();

        log.info("Выбираем вход по логину и паролю");
        loginModeButton.click();
    }

    private void enterLogin(String login) {
        log.info("Вводим логин пользователя");
        loginInput.fill(login);

        log.info("Переходим к вводу пароля");
        nextLoginButton.click();
    }

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

    private void selectPasswordLoginIfAvailable() {
        if (passwordLoginButton.isExists()) {
            log.info("Выбираем вход с паролем");
            passwordLoginButton.click();

            waitForPage().until(driver ->
                    passwordInput.isExists()
            );
        }
    }

    private void skipWebauthnRegistration() {
        if (webauthnLaterButton.isExists()) {
            log.info("Пропускаем настройку WebAuthn");
            webauthnLaterButton.click();

            waitForPage().until(driver ->
                    driver.getCurrentUrl().contains("id.yandex.ru")
            );
        }
    }

    private void skipIdentification() {
        if (identificationSkipButton.isExists()) {
            log.info("Пропускаем дополнительную идентификацию");
            identificationSkipButton.click();

            waitForPage().until(driver ->
                    !identificationSkipButton.isExists() || driver.getCurrentUrl().contains("mail.yandex")
            );
        }
    }

    private void openMail() {
        log.info("Переходим в Яндекс Почту");
        open("https://mail.yandex.ru");
    }
}