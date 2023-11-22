package com.secacon.secasignbox.sdk.dto.sign.organization.pdf;

import com.secacon.secasignbox.sdk.dto.sign.general.SignatureStrategyDto;

public final class CreateOrganizationPdfDocumentDto {

    private String encodedDocument;

    private String documentName;

    private Boolean protectedPdfSigning;

    private SignatureStrategyDto signatureStrategy;

    public CreateOrganizationPdfDocumentDto() {
    }

    public CreateOrganizationPdfDocumentDto(String encodedDocument, String documentName, Boolean protectedPdfSigning, SignatureStrategyDto signatureStrategy) {
        this.encodedDocument = encodedDocument;
        this.documentName = documentName;
        this.protectedPdfSigning = protectedPdfSigning;
        this.signatureStrategy = signatureStrategy;
    }

    public String getEncodedDocument() {
        return encodedDocument;
    }

    public void setEncodedDocument(String encodedDocument) {
        this.encodedDocument = encodedDocument;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Boolean getProtectedPdfSigning() {
        return protectedPdfSigning;
    }

    public void setProtectedPdfSigning(Boolean protectedPdfSigning) {
        this.protectedPdfSigning = protectedPdfSigning;
    }

    public SignatureStrategyDto getSignatureStrategy() {
        return signatureStrategy;
    }

    public void setSignatureStrategy(SignatureStrategyDto signatureStrategy) {
        this.signatureStrategy = signatureStrategy;
    }
}
