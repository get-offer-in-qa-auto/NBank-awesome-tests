package ui.pages.admin;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.Alert;
import ui.elements.UserBage;
import ui.pages.BankAlert;
import ui.pages.BasePage;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
public class AdminPanel extends BasePage<AdminPanel> {
    private SelenideElement addUserButton = $(Selectors.byText("Add User"));
    private SelenideElement adminPanelMessage = $(Selectors.byText("Admin Panel"));

    @Override
    protected String url() {
        return "/admin";
    }

    public AdminPanel createUser(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        addUserButton.click();
        return this;
    }

    public List<UserBage> getAllUsers() {
        ElementsCollection elements = $(Selectors.byText("All Users")).parent().findAll("li");
        var s= generatePageElements(elements, UserBage::new);
        return s;
    }
}
