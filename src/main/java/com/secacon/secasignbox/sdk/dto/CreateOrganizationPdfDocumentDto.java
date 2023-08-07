package com.secacon.secasignbox.sdk.dto;

public record CreateOrganizationPdfDocumentDto(

    String encodedDocument,

    String documentName,

    Boolean protectedPdfSigning,

    SignatureStrategyDto signatureStrategy
) {
}
