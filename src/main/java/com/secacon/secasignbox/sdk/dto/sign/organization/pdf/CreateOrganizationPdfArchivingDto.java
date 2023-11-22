package com.secacon.secasignbox.sdk.dto.sign.organization.pdf;

import java.util.List;
import java.util.UUID;

public final class CreateOrganizationPdfArchivingDto {

    private UUID processingRuleId;

    private List<CreateOrganizationPdfDocumentDto> documents;

    public CreateOrganizationPdfArchivingDto() {
    }

    public CreateOrganizationPdfArchivingDto(UUID processingRuleId, List<CreateOrganizationPdfDocumentDto> documents) {
        this.processingRuleId = processingRuleId;
        this.documents = documents;
    }

    public UUID getProcessingRuleId() {
        return processingRuleId;
    }

    public void setProcessingRuleId(UUID processingRuleId) {
        this.processingRuleId = processingRuleId;
    }

    public List<CreateOrganizationPdfDocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<CreateOrganizationPdfDocumentDto> documents) {
        this.documents = documents;
    }
}
