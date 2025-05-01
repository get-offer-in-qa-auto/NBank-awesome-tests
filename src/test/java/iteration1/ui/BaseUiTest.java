package iteration1.ui;

import api.configs.Config;
import api.extensions.AdminSessionExtension;
import api.extensions.UserSessionExtension;
import api.extensions.BrowserMatchExtension;
import api.extensions.EnvironmentExtension;
import com.codeborne.selenide.Configuration;
import iteration1.api.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

@ExtendWith(UserSessionExtension.class)
@ExtendWith(AdminSessionExtension.class)
@ExtendWith(BrowserMatchExtension.class)
@ExtendWith(EnvironmentExtension.class)
public class BaseUiTest extends BaseTest {
    @BeforeAll
    public static void setupSelenoid() {
        Configuration.remote = Config.getProperty("remote");
        Configuration.baseUrl = Config.getProperty("baseUiUrl");
        Configuration.browserSize = Config.getProperty("browserSize");
        Configuration.browser = Config.getProperty("browser");

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true,
                        "enableLog", true));
    }
}
