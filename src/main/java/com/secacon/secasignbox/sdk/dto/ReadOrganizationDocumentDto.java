package com.secacon.secasignbox.sdk.dto;

public record ReadOrganizationDocumentDto(

    DocumentSigningStatusDto signingStatus,

    String errorMessage,

    String documentName,

    String encodedSignedDocumentOrSignature,

    SignatureTypeDto signatureType

    // Commented out. Sign without archiving always returns null for this value, so we don't need it
    //ReadDocumentDto document
) {
}
