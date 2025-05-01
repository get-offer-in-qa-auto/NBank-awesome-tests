package iteration1.ui;

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

public class CreateUserTest extends BaseUiTest {

    @Test
    public void adminCanCreateUserTest() {
        authAsUser(admin.getUsername(), admin.getPassword());

        var newUser = RandomModelGenerator.generate(CreateUserRequest.class);

        new AdminPanel().open().createUser(newUser.getUsername(), newUser.getPassword())
                .checkAlertAndAccept(BankAlert.SUCCESSFUL_USER_CREATION_ALERT_TEXT)
                .getAllUsers().findBy(Condition.exactText(newUser.getUsername() + "\n" + newUser.getRole())).scrollTo().shouldBe(Condition.visible);

       CreateUserResponse createdUser = AdminSteps.getAllUsers()
                .stream().filter(user -> user.getUsername().equals(newUser.getUsername())).findFirst().get();

        ModelAssertions.assertThatModels(newUser, createdUser).match();
    }

    @Test
    public void adminCanNotCreateUserWithInvalidDataTest() {
        authAsUser(admin.getUsername(), admin.getPassword());

        var newUser = RandomModelGenerator.generate(CreateUserRequest.class);
        newUser.setUsername(RandomData.getStringWithRegex("([a-zA-Z0-9]{1,2}|[a-zA-Z0-9]{16,})"));

        new AdminPanel().open().createUser(newUser.getUsername(), newUser.getPassword())
                .checkAlertAndAccept(BankAlert.USERNAME_MUST_BE_BETWEEN_3_AND_15_CHARACTERS)
                .getAllUsers().findBy(Condition.exactText(newUser.getUsername() + "\n" + newUser.getRole())).shouldNot(Condition.exist);

        long usersWithSameUsernameOnDashBoardCount = AdminSteps.getAllUsers()
                .stream().filter(user -> user.getUsername().equals(newUser.getUsername())).count();

        assertThat(usersWithSameUsernameOnDashBoardCount).isZero();
    }
}
