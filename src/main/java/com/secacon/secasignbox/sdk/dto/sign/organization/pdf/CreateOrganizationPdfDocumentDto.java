package com.secacon.secasignbox.sdk.dto.sign.organization.pdf;

import com.secacon.secasignbox.sdk.dto.sign.general.SignatureStrategyDto;

public record CreateOrganizationPdfDocumentDto(

    String encodedDocument,

    String documentName,

    Boolean protectedPdfSigning,

    SignatureStrategyDto signatureStrategy
) {
}
