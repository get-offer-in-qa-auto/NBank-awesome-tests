package ui.pages;

import lombok.Getter;

@Getter
public enum BankAlert {
    SUCCESSFUL_USER_CREATION_ALERT_TEXT("✅ User created successfully!"),
    USERNAME_MUST_BE_BETWEEN_3_AND_15_CHARACTERS("Username must be between 3 and 15 characters"),
    NEW_ACCOUNT_ADDED("✅ New Account Created! Account Number:");

    private final String message;

    BankAlert(String message) {
        this.message = message;
    }
}
