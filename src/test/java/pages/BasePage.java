package pages;

import com.codeborne.selenide.Selenide;

public class BasePage {

    public <T extends BasePage> T refresh(Class<T> pageClass) {
        Selenide.refresh();

        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать страницу", e);
        }
    }
}