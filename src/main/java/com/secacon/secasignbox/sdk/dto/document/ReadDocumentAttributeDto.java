package com.secacon.secasignbox.sdk.dto.document;

import java.util.UUID;

public record ReadDocumentAttributeDto(

    UUID archiveAttributeId,

    String value
) {
}
