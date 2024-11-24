package com.secacon.secasign.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.util.AnnotationUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public class SdkExtension implements ExecutionCondition, ParameterResolver {

    private static final String CONFIGURATION_PROPERTIES_NAME = "../configuration.properties";

    private static SdkConfiguration sdkConfiguration;

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        // Read the SDK configuration if required
        if (sdkConfiguration == null) {
            sdkConfiguration = readSdkConfiguration();
        }

        // Check if the test is annotated
        Optional<SdkTestRequirements> optionalTestRequirements = AnnotationUtils.findAnnotation(context.getElement(), SdkTestRequirements.class);
        if (optionalTestRequirements.isPresent()) {
            // Get the requirements
            SdkTestRequirements testRequirements = optionalTestRequirements.get();

            // Check all conditions
            boolean isLoginAvailable = sdkConfiguration.getUrl() != null && sdkConfiguration.getEmailAddress() != null && sdkConfiguration.getPassword() != null;
            boolean isSigningAvailable = sdkConfiguration.getSigningId() != null;
            boolean isArchivingAvailable = sdkConfiguration.getProcessingRuleId() != null;
            boolean isDocumentAvailable = sdkConfiguration.getDocumentId() != null;
            boolean isArchiveSearchAvailable = sdkConfiguration.getArchiveAttributeId() != null && sdkConfiguration.getArchiveAttributeValue() != null;

            // Check if login is required and available
            if (testRequirements.isLoginAvailable() && !isLoginAvailable) {
                return ConditionEvaluationResult.disabled("Please set the credentials to non-null values in the Values.java file!");
            }

            // Check if signing is required and available
            if (testRequirements.isSigningAvailable() && (!isLoginAvailable || !isSigningAvailable)) {
                return ConditionEvaluationResult.disabled("Please set the credentials/signing ID to non-null values in the Values.java file!");
            }

            // Check if archiving is required and available
            if (testRequirements.isArchivingAvailable() && (!isLoginAvailable || !isArchivingAvailable)) {
                return ConditionEvaluationResult.disabled("Please set the credentials/processing rule ID to non-null values in the Values.java file!");
            }

            // Check if document information is required and available
            if (testRequirements.isDocumentAvailable() && (!isLoginAvailable || !isDocumentAvailable)) {
                return ConditionEvaluationResult.disabled("Please set the credentials/processing rule ID to non-null values in the Values.java file!");
            }

            // Check if archive search is required and available
            if (testRequirements.isArchiveSearchAvailable() && (!isLoginAvailable || !isArchiveSearchAvailable)) {
                return ConditionEvaluationResult.disabled("Please set the credentials/archive attribute ID/value to non-null values in the Values.java file!");
            }
        }

        // All requirements are fulfilled
        return ConditionEvaluationResult.enabled("No requirements found");
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == SdkConfiguration.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return sdkConfiguration;
    }

    private static SdkConfiguration readSdkConfiguration() {
        // Read the properties file
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(CONFIGURATION_PROPERTIES_NAME)) {
            // Load the properties
            properties.load(inputStream);

            // Read all values but don't parse them
            String url = properties.getProperty("secasign.url", null);
            String emailAddress = properties.getProperty("secasign.email_address", null);
            String password = properties.getProperty("secasign.password", null);
            String signingId = properties.getProperty("secasign.signing_id", null);
            String processingRuleId = properties.getProperty("secasign.processing_rule_id", null);
            String documentId = properties.getProperty("secasign.document_id", null);
            String archiveAttributeId = properties.getProperty("secasign.archive_attribute_id", null);
            String archiveAttributeValue = properties.getProperty("secasign.archive_attribute_value", null);

            // Check and parse the values and return the configuration
            return new SdkConfiguration(
                isValueNotBlank(url) ? url : null,
                isValueNotBlank(emailAddress) ? emailAddress : null,
                isValueNotBlank(password) ? password : null,
                isValueNotBlank(signingId) ? UUID.fromString(signingId) : null,
                isValueNotBlank(processingRuleId) ? UUID.fromString(processingRuleId) : null,
                isValueNotBlank(documentId) ? UUID.fromString(documentId) : null,
                isValueNotBlank(archiveAttributeId) ? UUID.fromString(archiveAttributeId) : null,
                isValueNotBlank(archiveAttributeValue) ? archiveAttributeValue : null
            );
        } catch (Exception exception) {
            throw new RuntimeException("Unable to read the configuration.properties file: " + exception.getMessage(), exception);
        }
    }

    private static boolean isValueNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
