package api.extensions;

import api.annotations.AdminSession;
import api.models.CreateUserRequest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.pages.BasePage;

public class AdminSessionExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        AdminSession annotation = context.getRequiredTestMethod().getAnnotation(AdminSession.class);
        if (annotation != null) {
            BasePage.authAsUser(CreateUserRequest.getAdmin().getUsername(), CreateUserRequest.getAdmin().getPassword());
        }
    }
}

