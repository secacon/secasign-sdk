package com.secacon.secasignbox.sdk.dto.document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record DocumentProcessingDto(

    Instant creationDate,

    UUID processingRuleId,

    DocumentLocationDto inputLocation,

    List<DocumentLocationDto> outputLocations
) {
}
