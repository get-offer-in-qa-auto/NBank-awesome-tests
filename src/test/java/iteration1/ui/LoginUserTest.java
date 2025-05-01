package iteration1.ui;

import api.requests.steps.AdminSteps;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import ui.pages.LoginPage;
import ui.pages.UserDashboard;
import ui.pages.admin.AdminPanel;

public class LoginUserTest extends BaseUiTest {
    @Test
    public void userCanLoginWithCorrectDataTest() {
        var user = AdminSteps.createUser();

        new LoginPage().open().login(user.getUsername(), user.getPassword())
                .getPage(UserDashboard.class)
                .getWelcomeText().shouldHave(Condition.text("noname"));
    }

    @Test
    public void adminCanLoginWithCorrectDataTest() {
        new LoginPage().open().login(admin.getUsername(), admin.getPassword())
                .getPage(AdminPanel.class)
                .getAdminPanelMessage().shouldBe(Condition.visible);
    }
}
