package com.secacon.secasign.sdk.dto.document;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DocumentSearchCriteriaDto {

    private Integer page;

    private Integer size;

    private Set<UUID> archiveIds;

    private UUID documentId;

    private String documentName;

    private Long documentSize;

    private Instant startDate;

    private Instant endDate;

    private Boolean expired;

    private String text;

    private DocumentSearchStatusDto status;

    private DocumentProcessingStatusDto processingStatus;

    private DocumentExtractionStatusDto extractionStatus;

    private List<ReadDocumentAttributeDto> documentAttributes;

    public DocumentSearchCriteriaDto() {
    }

    public DocumentSearchCriteriaDto(Integer page, Integer size, Set<UUID> archiveIds, UUID documentId, String documentName, Long documentSize, Instant startDate, Instant endDate, Boolean expired, String text, DocumentSearchStatusDto status, DocumentProcessingStatusDto processingStatus, DocumentExtractionStatusDto extractionStatus, List<ReadDocumentAttributeDto> documentAttributes) {
        this.page = page;
        this.size = size;
        this.archiveIds = archiveIds;
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentSize = documentSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expired = expired;
        this.text = text;
        this.status = status;
        this.processingStatus = processingStatus;
        this.extractionStatus = extractionStatus;
        this.documentAttributes = documentAttributes;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Set<UUID> getArchiveIds() {
        return archiveIds;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public Long getDocumentSize() {
        return documentSize;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public String getText() {
        return text;
    }

    public DocumentSearchStatusDto getStatus() {
        return status;
    }

    public DocumentProcessingStatusDto getProcessingStatus() {
        return processingStatus;
    }

    public DocumentExtractionStatusDto getExtractionStatus() {
        return extractionStatus;
    }

    public List<ReadDocumentAttributeDto> getDocumentAttributes() {
        return documentAttributes;
    }
}
