package api.extensions;

import api.annotations.Environments;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.*;

import java.util.*;

public class EnvironmentExtension implements ExecutionCondition {

    // ✅ Пресеты: название окружения -> размер браузера
    private static final Map<String, String> PRESETS = Map.of(
            "desktop", "1920x1080",
            "mobile", "375x667",
            "tablet", "768x1024",
            "ultrawide", "2560x1080",
            "macbook", "1440x900"
    );

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<Environments> maybeAnnotation = context.getElement()
                .map(el -> el.getAnnotation(Environments.class));

        if (maybeAnnotation.isEmpty()) {
            return ConditionEvaluationResult.enabled("No @Environments annotation present");
        }

        String currentSize = Configuration.browserSize;
        Environments annotation = maybeAnnotation.get();

        for (String env : annotation.value()) {
            String expectedSize = PRESETS.get(env.toLowerCase());
            if (expectedSize != null && expectedSize.equalsIgnoreCase(currentSize)) {
                return ConditionEvaluationResult.enabled("✅ Browser size matches environment preset: " + env);
            }
        }

        return ConditionEvaluationResult.disabled(
                "❌ Current browser size " + currentSize + " doesn't match any of: " + Arrays.toString(annotation.value())
        );
    }
}

