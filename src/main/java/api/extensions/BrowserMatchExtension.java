package api.extensions;

import api.annotations.Browsers;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;

public class BrowserMatchExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Browsers annotation = context.getElement()
                .map(el -> el.getAnnotation(Browsers.class))
                .orElse(null);

        if (annotation == null) {
            return ConditionEvaluationResult.enabled("✅ No @Browsers restriction");
        }

        String currentBrowser = Configuration.browser;
        boolean matches = Arrays.stream(annotation.value())
                .anyMatch(browser -> browser.equalsIgnoreCase(currentBrowser));

        if (matches) {
            return ConditionEvaluationResult.enabled("✅ Browser matches: " + currentBrowser);
        } else {
            return ConditionEvaluationResult.disabled("❌ Test skipped: browser '" + currentBrowser +
                    "' is not in allowed list " + Arrays.toString(annotation.value()));
        }
    }
}

