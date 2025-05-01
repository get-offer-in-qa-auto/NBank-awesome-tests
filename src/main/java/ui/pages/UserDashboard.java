package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class UserDashboard extends BasePage<UserDashboard>{
    private SelenideElement welcomeText = $(Selectors.byClassName("welcome-text"));
    private SelenideElement createAccountButton =  $(Selectors.byText("➕ Create New Account"));

    @Override
    protected String url() {
        return "/dashboard";
    }

    public UserDashboard createAccount() {
        createAccountButton.click();
        return this;
    }
}
