package iteration1.api;

import api.models.CreateUserRequest;
import api.requests.steps.AdminSteps;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    protected SoftAssertions softly;
    protected CreateUserRequest admin = CreateUserRequest.getAdmin();


    @BeforeEach
    public void setupTest() {
        this.softly = new SoftAssertions();
    }

    @AfterEach
    public void afterTest() {
        softly.assertAll();
        AdminSteps.getAllUsers().forEach(user -> AdminSteps.deleteUser(user.getId()));
    }
}
