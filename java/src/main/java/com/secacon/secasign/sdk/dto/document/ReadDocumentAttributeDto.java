package com.secacon.secasign.sdk.dto.document;

import java.util.UUID;

public class ReadDocumentAttributeDto {

    private UUID archiveAttributeId;

    private String value;

    public ReadDocumentAttributeDto() {
    }

    public ReadDocumentAttributeDto(UUID archiveAttributeId, String value) {
        this.archiveAttributeId = archiveAttributeId;
        this.value = value;
    }

    public UUID getArchiveAttributeId() {
        return archiveAttributeId;
    }

    public void setArchiveAttributeId(UUID archiveAttributeId) {
        this.archiveAttributeId = archiveAttributeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
