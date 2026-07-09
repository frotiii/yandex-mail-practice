package pages;

import elements.Button;
import elements.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private final Button moreButton =
            Button.byXpath("//button[@data-testid='split-add-user-more-button']");

    private final Button loginModeButton =
            Button.byXpath("//*[@data-testid='menu-option-switchToLogin']");

    private final Input loginInput =
            Input.byXpath("//input[@data-testid='text-field-input']");

    private final Button nextLoginButton =
            Button.byXpath("//button[@data-testid='split-add-user-next-login']");

    private final Input passwordInput =
            Input.byXpath("//input[@placeholder='Пароль']");

    private final Button passwordNextButton =
            Button.byXpath("//button[@data-testid='password-next']");

    private final Button passwordLoginButton =
            Button.byXpath("//button[@data-testid='password-btn']");

    private final Button webauthnLaterButton =
            Button.byXpath("//button[@data-testid='webauthn-reg-later-button']");

    private final Button identificationSkipButton =
            Button.byXpath("//button[@data-testid='identification-promo-start-skip-btn']");


    public InboxPage login(String login, String password) {

        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException(
                    "Логин не указан в credentials.properties! Проверьте ключ yandex.login"
            );
        }

        log.info("Открываем страницу авторизации Яндекс");
        open("https://passport.yandex.ru/auth/");

        sleep(1000);

        log.info("Нажимаем кнопку «Ещё»...");
        moreButton.click();
        sleep(500);

        log.info("Выбираем вход по логину и паролю");
        loginModeButton.click();
        sleep(1000);

        log.info("Вводим логин пользователя");
        loginInput.fill(login);

        log.info("Переходим к вводу пароля");
        nextLoginButton.click();
        sleep(1000);

        if (passwordLoginButton.isExists()) {
            log.info("Выбираем вход с паролем");
            passwordLoginButton.click();
            sleep(1000);
        }

        log.info("Вводим пароль");
        passwordInput.fill(password);

        log.info("Подтверждаем вход");
        passwordNextButton.click();

        sleep(2000);

        if (webauthnLaterButton.isExists()) {
            log.info("Пропускаем настройку WebAuthn");
            webauthnLaterButton.click();
            sleep(1000);
        }

        if (identificationSkipButton.isExists()) {
            log.info("Пропускаем дополнительную идентификацию");
            identificationSkipButton.click();
            sleep(1000);
        }

        log.info("Переходим в Яндекс Почту");
        open("https://mail.yandex.ru");

        sleep(2000);

        log.info("Авторизация успешно выполнена");

        return new InboxPage();
    }
}