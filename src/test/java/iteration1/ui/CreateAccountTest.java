package iteration1.ui;

import api.annotations.Browsers;
import api.configs.SessionStorage;
import api.annotations.WithUserSessions;
import api.annotations.Environments;
import api.models.CreateAccountResponse;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlert;
import ui.pages.BasePage;
import ui.pages.UserDashboard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccountTest extends BaseUiTest {

    @Test
    @WithUserSessions()
    @Browsers({"chrome", "firefox"})
    @Environments({"desktop", "tablet"})
    public void userCanCreateAccountTest() {
        var user = SessionStorage.getUser();
        var userSteps = SessionStorage.getSteps();

        BasePage.authAsUser(user.getUsername(), user.getPassword());

        String createdAccountNumber = new UserDashboard().open().createAccount()
                .checkAlertAndExtractAndAccept(BankAlert.NEW_ACCOUNT_ADDED, "Account Number: (\\w+)");

        List<CreateAccountResponse> existingUserAccounts = userSteps.getAllAccounts();
        CreateAccountResponse createAccount = existingUserAccounts.stream().filter(account -> account.getAccountNumber().equals(createdAccountNumber)).findFirst().get();

        assertThat(createAccount).isNotNull();
        assertThat(createAccount.getBalance()).isZero();
        assertThat(existingUserAccounts).hasSize(1);
    }
}

