package pages;

import com.codeborne.selenide.Selenide;

// Базовый класс для страниц, предоставляющий общие операции
public class BasePage {

    // Перезагружает текущую страницу и возвращает новый экземпляр переданного класса страницы
    public <T extends BasePage> T refresh(Class<T> pageClass) {
        Selenide.refresh();

        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать страницу", e);
        }
    }
}