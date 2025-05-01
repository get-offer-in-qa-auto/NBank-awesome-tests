package api.extensions;

import api.annotations.WithUserSessions;
import api.configs.SessionStorage;
import api.models.CreateUserRequest;
import api.requests.steps.AdminSteps;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.List;

public class UserSessionExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        WithUserSessions annotation = context.getRequiredTestMethod().getAnnotation(WithUserSessions.class);
        if (annotation != null) {
            int userCount = annotation.value();

            SessionStorage.clear();

            List<CreateUserRequest> users = new ArrayList<>();
            for (int i = 0; i < userCount; i++) {
                CreateUserRequest user = AdminSteps.createUser();
                users.add(user);
            }

            SessionStorage.addUsers(users);
        }
    }
}
