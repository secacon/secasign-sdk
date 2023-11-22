package com.secacon.secasignbox.sdk.dto.document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class DocumentProcessingDto {

    private Instant creationDate;

    private UUID processingRuleId;

    private DocumentLocationDto inputLocation;

    private List<DocumentLocationDto> outputLocations;

    public DocumentProcessingDto() {
    }

    public DocumentProcessingDto(Instant creationDate, UUID processingRuleId, DocumentLocationDto inputLocation, List<DocumentLocationDto> outputLocations) {
        this.creationDate = creationDate;
        this.processingRuleId = processingRuleId;
        this.inputLocation = inputLocation;
        this.outputLocations = outputLocations;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getProcessingRuleId() {
        return processingRuleId;
    }

    public void setProcessingRuleId(UUID processingRuleId) {
        this.processingRuleId = processingRuleId;
    }

    public DocumentLocationDto getInputLocation() {
        return inputLocation;
    }

    public void setInputLocation(DocumentLocationDto inputLocation) {
        this.inputLocation = inputLocation;
    }

    public List<DocumentLocationDto> getOutputLocations() {
        return outputLocations;
    }

    public void setOutputLocations(List<DocumentLocationDto> outputLocations) {
        this.outputLocations = outputLocations;
    }
}
