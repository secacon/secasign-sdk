package com.secacon.secasignbox.sdk.dto.sign;

import com.secacon.secasignbox.sdk.dto.document.DocumentSigningStatusDto;
import com.secacon.secasignbox.sdk.dto.document.ReadDocumentDto;
import com.secacon.secasignbox.sdk.dto.document.SignatureTypeDto;

public record ReadOrganizationDocumentDto(

    DocumentSigningStatusDto signingStatus,

    String errorMessage,

    String documentName,

    String encodedSignedDocumentOrSignature,

    SignatureTypeDto signatureType,

    ReadDocumentDto document
) {
}
