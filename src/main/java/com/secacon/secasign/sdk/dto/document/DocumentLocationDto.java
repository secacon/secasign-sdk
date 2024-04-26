package com.secacon.secasign.sdk.dto.document;

import java.util.UUID;

public final class DocumentLocationDto {

    private UUID locationId;

    private String errorMessage;

    public DocumentLocationDto() {
    }

    public DocumentLocationDto(UUID locationId, String errorMessage) {
        this.locationId = locationId;
        this.errorMessage = errorMessage;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
