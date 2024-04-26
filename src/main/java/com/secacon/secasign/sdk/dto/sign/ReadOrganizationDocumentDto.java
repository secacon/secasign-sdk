package com.secacon.secasign.sdk.dto.sign;

import com.secacon.secasign.sdk.dto.document.ReadDocumentDto;
import com.secacon.secasign.sdk.dto.document.DocumentSigningStatusDto;
import com.secacon.secasign.sdk.dto.document.SignatureTypeDto;

public final class ReadOrganizationDocumentDto {

    private DocumentSigningStatusDto signingStatus;

    private String errorMessage;

    private String documentName;

    private String encodedSignedDocumentOrSignature;

    private SignatureTypeDto signatureType;

    private ReadDocumentDto document;

    public ReadOrganizationDocumentDto() {
    }

    public ReadOrganizationDocumentDto(DocumentSigningStatusDto signingStatus, String errorMessage, String documentName, String encodedSignedDocumentOrSignature, SignatureTypeDto signatureType, ReadDocumentDto document) {
        this.signingStatus = signingStatus;
        this.errorMessage = errorMessage;
        this.documentName = documentName;
        this.encodedSignedDocumentOrSignature = encodedSignedDocumentOrSignature;
        this.signatureType = signatureType;
        this.document = document;
    }

    public DocumentSigningStatusDto getSigningStatus() {
        return signingStatus;
    }

    public void setSigningStatus(DocumentSigningStatusDto signingStatus) {
        this.signingStatus = signingStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getEncodedSignedDocumentOrSignature() {
        return encodedSignedDocumentOrSignature;
    }

    public void setEncodedSignedDocumentOrSignature(String encodedSignedDocumentOrSignature) {
        this.encodedSignedDocumentOrSignature = encodedSignedDocumentOrSignature;
    }

    public SignatureTypeDto getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(SignatureTypeDto signatureType) {
        this.signatureType = signatureType;
    }

    public ReadDocumentDto getDocument() {
        return document;
    }

    public void setDocument(ReadDocumentDto document) {
        this.document = document;
    }
}
