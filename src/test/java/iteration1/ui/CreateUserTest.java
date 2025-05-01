package iteration1.ui;

import api.annotations.AdminSession;
import api.annotations.Browsers;
import api.generators.RandomData;
import api.generators.RandomModelGenerator;
import api.models.CreateUserRequest;
import api.models.CreateUserResponse;
import api.models.comparison.ModelAssertions;
import api.requests.steps.AdminSteps;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlert;
import ui.pages.admin.AdminPanel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserTest extends BaseUiTest {

    @Test
    @AdminSession
    public void adminCanCreateUserTest() {
        var newUser = RandomModelGenerator.generate(CreateUserRequest.class);

        assertTrue(new AdminPanel().open().createUser(newUser.getUsername(), newUser.getPassword())
                .checkAlertAndAccept(BankAlert.SUCCESSFUL_USER_CREATION_ALERT_TEXT)
                .getAllUsers().stream().anyMatch(userBage -> userBage.getUsername().equals(newUser.getUsername())));

       CreateUserResponse createdUser = AdminSteps.getAllUsers()
                .stream().filter(user -> user.getUsername().equals(newUser.getUsername())).findFirst().get();

        ModelAssertions.assertThatModels(newUser, createdUser).match();
    }

    @Test
    @AdminSession
    @Browsers({"chrome", "firefox"})
    public void adminCanNotCreateUserWithInvalidDataTest() {
        var newUser = RandomModelGenerator.generate(CreateUserRequest.class);
        newUser.setUsername(RandomData.getStringWithRegex("([a-zA-Z0-9]{1,2}|[a-zA-Z0-9]{16,})"));

        assertTrue(new AdminPanel().open().createUser(newUser.getUsername(), newUser.getPassword())
                .checkAlertAndAccept(BankAlert.USERNAME_MUST_BE_BETWEEN_3_AND_15_CHARACTERS)
                .getAllUsers().stream().noneMatch(userBage -> userBage.getUsername().equals(newUser.getUsername())));

        long usersWithSameUsernameOnDashBoardCount = AdminSteps.getAllUsers()
                .stream().filter(user -> user.getUsername().equals(newUser.getUsername())).count();

        assertThat(usersWithSameUsernameOnDashBoardCount).isZero();
    }
}
