package com.secacon.secasign.extension;

import com.secacon.secasign.sdk.Values;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Optional;

public class SdkExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        // Check if the test is annotated
        Optional<SdkTestRequirements> optionalTestRequirements = AnnotationUtils.findAnnotation(context.getElement(), SdkTestRequirements.class);
        if (optionalTestRequirements.isPresent()) {
            // Get the requirements
            SdkTestRequirements testRequirements = optionalTestRequirements.get();

            // Check if login is required and available
            if (testRequirements.isLoginAvailable() && (Values.USERNAME == null || Values.PASSWORD == null)) {
                return ConditionEvaluationResult.disabled("Please set the username/password to a non-null value in the Value.java file!");
            }

            // Check if archiving is required and available
            if (testRequirements.isArchivingAvailable() && Values.PROCESSING_RULE_ID == null) {
                return ConditionEvaluationResult.disabled("Please set the processing rule ID to a non-null value in the Value.java file!");
            }

            // Check if signing is required and available
            if (testRequirements.isSigningAvailable() && Values.SIGNING_ID == null) {
                return ConditionEvaluationResult.disabled("Please set the signing ID to a non-null value in the Value.java file!");
            }
        }
        return ConditionEvaluationResult.enabled("No requirements found");
    }
}
