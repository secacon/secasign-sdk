package com.secacon.secasignbox.sdk.dto.document;

import java.util.UUID;

public record DocumentLocationDto(

    UUID locationId,

    String errorMessage
) {
}
