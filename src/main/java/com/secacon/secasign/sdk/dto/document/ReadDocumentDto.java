package com.secacon.secasign.sdk.dto.document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReadDocumentDto {

    private UUID id;

    private UUID archiveId;

    private String documentName;

    private Long documentSize;

    private String originalDocumentHash;

    private String signedDocumentHash;

    private String textHash;

    private Instant archivingDate;

    private Instant retentionEndDate;

    private SignatureTypeDto signatureType;

    private DocumentProcessingStatusDto processingStatus;

    private DocumentExtractionStatusDto extractionStatus;

    private String errorMessage;

    private List<ReadDocumentAttributeDto> documentAttributes;

    private DocumentProcessingDto processing;

    public ReadDocumentDto() {
    }

    public ReadDocumentDto(UUID id, UUID archiveId, String documentName, Long documentSize, String originalDocumentHash, String signedDocumentHash, String textHash, Instant archivingDate, Instant retentionEndDate, SignatureTypeDto signatureType, DocumentProcessingStatusDto processingStatus, DocumentExtractionStatusDto extractionStatus, String errorMessage, List<ReadDocumentAttributeDto> documentAttributes, DocumentProcessingDto processing) {
        this.id = id;
        this.archiveId = archiveId;
        this.documentName = documentName;
        this.documentSize = documentSize;
        this.originalDocumentHash = originalDocumentHash;
        this.signedDocumentHash = signedDocumentHash;
        this.textHash = textHash;
        this.archivingDate = archivingDate;
        this.retentionEndDate = retentionEndDate;
        this.signatureType = signatureType;
        this.processingStatus = processingStatus;
        this.extractionStatus = extractionStatus;
        this.errorMessage = errorMessage;
        this.documentAttributes = documentAttributes;
        this.processing = processing;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(UUID archiveId) {
        this.archiveId = archiveId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Long getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

    public String getOriginalDocumentHash() {
        return originalDocumentHash;
    }

    public void setOriginalDocumentHash(String originalDocumentHash) {
        this.originalDocumentHash = originalDocumentHash;
    }

    public String getSignedDocumentHash() {
        return signedDocumentHash;
    }

    public void setSignedDocumentHash(String signedDocumentHash) {
        this.signedDocumentHash = signedDocumentHash;
    }

    public String getTextHash() {
        return textHash;
    }

    public void setTextHash(String textHash) {
        this.textHash = textHash;
    }

    public Instant getArchivingDate() {
        return archivingDate;
    }

    public void setArchivingDate(Instant archivingDate) {
        this.archivingDate = archivingDate;
    }

    public Instant getRetentionEndDate() {
        return retentionEndDate;
    }

    public void setRetentionEndDate(Instant retentionEndDate) {
        this.retentionEndDate = retentionEndDate;
    }

    public SignatureTypeDto getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(SignatureTypeDto signatureType) {
        this.signatureType = signatureType;
    }

    public DocumentProcessingStatusDto getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(DocumentProcessingStatusDto processingStatus) {
        this.processingStatus = processingStatus;
    }

    public DocumentExtractionStatusDto getExtractionStatus() {
        return extractionStatus;
    }

    public void setExtractionStatus(DocumentExtractionStatusDto extractionStatus) {
        this.extractionStatus = extractionStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ReadDocumentAttributeDto> getDocumentAttributes() {
        return documentAttributes;
    }

    public void setDocumentAttributes(List<ReadDocumentAttributeDto> documentAttributes) {
        this.documentAttributes = documentAttributes;
    }

    public DocumentProcessingDto getProcessing() {
        return processing;
    }

    public void setProcessing(DocumentProcessingDto processing) {
        this.processing = processing;
    }
}
