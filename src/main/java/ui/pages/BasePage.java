package ui.pages;

import api.generators.StringUtils;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class BasePage<T extends BasePage<T>> {
    protected SelenideElement usernameInput = $(Selectors.byAttribute("placeholder", "Username"));
    protected SelenideElement passwordInput = $(Selectors.byAttribute("placeholder", "Password"));
    protected SelenideElement button =  $("button");

    protected abstract String url();

    // Универсальный open, возвращающий тип T
    @SuppressWarnings("unchecked")
    public T open() {
        return Selenide.open(url(), (Class<T>) this.getClass());
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        return Selenide.page(pageClass);
    }

    public T checkAlertAndAccept(BankAlert bankAlert) {
        checkAlertMessageAndExtractAlert(bankAlert).accept();
        return (T) this;
    }

    public String checkAlertAndExtractAndAccept(BankAlert bankAlert, String rexep) {
        Alert alert = checkAlertMessageAndExtractAlert(bankAlert);
        String extractedStr = StringUtils.getStringByRegex(alert.getText(), rexep);
        alert.accept();
        return extractedStr;
    }

    private Alert checkAlertMessageAndExtractAlert(BankAlert bankAlert) {
        Alert alert = switchTo().alert();
        assertThat(alert.getText()).contains(bankAlert.getMessage());
        return alert;
    }
}
