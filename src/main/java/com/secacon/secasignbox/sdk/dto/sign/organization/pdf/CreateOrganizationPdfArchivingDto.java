package com.secacon.secasignbox.sdk.dto.sign.organization.pdf;

import java.util.List;
import java.util.UUID;

public record CreateOrganizationPdfArchivingDto(

    UUID processingRuleId,

    List<CreateOrganizationPdfDocumentDto> documents
) {
}
