package com.secacon.secasignbox.sdk.dto.sign.organization.pdf;

import java.util.List;
import java.util.UUID;

public final class CreateOrganizationPdfSigningDto {

    private UUID signingId;

    private List<CreateOrganizationPdfDocumentDto> documents;

    public CreateOrganizationPdfSigningDto() {
    }

    public CreateOrganizationPdfSigningDto(UUID signingId, List<CreateOrganizationPdfDocumentDto> documents) {
        this.signingId = signingId;
        this.documents = documents;
    }

    public UUID getSigningId() {
        return signingId;
    }

    public void setSigningId(UUID signingId) {
        this.signingId = signingId;
    }

    public List<CreateOrganizationPdfDocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<CreateOrganizationPdfDocumentDto> documents) {
        this.documents = documents;
    }
}
