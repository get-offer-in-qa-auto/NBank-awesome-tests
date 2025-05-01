package iteration1.ui;

import api.models.CreateAccountResponse;
import api.models.CreateUserRequest;
import api.requests.steps.AdminSteps;
import api.requests.steps.UserSteps;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlert;
import ui.pages.UserDashboard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccountTest extends BaseUiTest {

    @Test
    public void userCanCreateAccountTest() {
        CreateUserRequest user = AdminSteps.createUser();

        authAsUser(user.getUsername(), user.getPassword());

        String createdAccountNumber = new UserDashboard().open().createAccount()
                .checkAlertAndExtractAndAccept(BankAlert.NEW_ACCOUNT_ADDED, "Account Number: (\\w+)");

        List<CreateAccountResponse> existingUserAccounts = new UserSteps(user.getUsername(), user.getPassword()).getAllAccounts();
        CreateAccountResponse createAccount = existingUserAccounts.stream().filter(account -> account.getAccountNumber().equals(createdAccountNumber)).findFirst().get();

        assertThat(createAccount).isNotNull();
        assertThat(createAccount.getBalance()).isZero();
        assertThat(existingUserAccounts).hasSize(1);
    }
}

