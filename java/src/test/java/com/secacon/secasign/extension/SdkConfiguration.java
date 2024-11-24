package com.secacon.secasign.extension;

import java.util.UUID;

public class SdkConfiguration {

    private final String url;

    private final String emailAddress;

    private final String password;

    private final UUID signingId;

    private final UUID processingRuleId;

    private final UUID documentId;

    private final UUID archiveAttributeId;

    private final String archiveAttributeValue;

    public SdkConfiguration(String url, String emailAddress, String password, UUID signingId, UUID processingRuleId, UUID documentId, UUID archiveAttributeId, String archiveAttributeValue) {
        this.url = url;
        this.emailAddress = emailAddress;
        this.password = password;
        this.signingId = signingId;
        this.processingRuleId = processingRuleId;
        this.documentId = documentId;
        this.archiveAttributeId = archiveAttributeId;
        this.archiveAttributeValue = archiveAttributeValue;
    }

    public String getUrl() {
        return url;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public UUID getSigningId() {
        return signingId;
    }

    public UUID getProcessingRuleId() {
        return processingRuleId;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public UUID getArchiveAttributeId() {
        return archiveAttributeId;
    }

    public String getArchiveAttributeValue() {
        return archiveAttributeValue;
    }
}
