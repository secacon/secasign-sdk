package com.secacon.secasignbox.sdk.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrganizationPdfSigningDto(

    UUID signingId,

    List<CreateOrganizationPdfDocumentDto> documents
) {
}
