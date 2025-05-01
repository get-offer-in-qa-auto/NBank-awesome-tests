package ui.pages;

import api.generators.StringUtils;
import api.specs.RequestSpecs;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import ui.elements.BasePageElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

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

    public static void authAsUser(String username, String password) {
        Selenide.open("/");
        String userAuthHeader = RequestSpecs.getAuthHeader(username, password);
        executeJavaScript("localStorage.setItem('authToken', arguments[0]);", userAuthHeader);
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        return Selenide.page(pageClass);
    }

    public T checkAlertAndAccept(BankAlert bankAlert) {
        checkAlertMessageAndExtractAlert(bankAlert).accept();
        waitTillAlertClosed();
        return (T) this;
    }

    public String checkAlertAndExtractAndAccept(BankAlert bankAlert, String rexep) {
        Alert alert = checkAlertMessageAndExtractAlert(bankAlert);
        String extractedStr = StringUtils.getStringByRegex(alert.getText(), rexep);
        alert.accept();
        waitTillAlertClosed();
        return extractedStr;
    }

    private Alert checkAlertMessageAndExtractAlert(BankAlert bankAlert) {
        Alert alert = switchTo().alert();
        assertThat(alert.getText()).contains(bankAlert.getMessage());
        return alert;
    }

    public void waitTillAlertClosed() {
        await().pollInterval(Duration.ofMillis(300))
                .atMost(Duration.ofSeconds(5))
                .until(() -> !isAlertPresent());
    }


    private boolean isAlertPresent() {
        try {
            switchTo().alert();
            return true;
        } catch (NoAlertPresentException | IllegalStateException ignored) {
            return false;
        }
    }


    protected <T extends BasePageElement> List<T> generatePageElements(ElementsCollection collection, Function<SelenideElement, T> creator)
    {
        return collection.stream().map(creator).toList();
    }
}
