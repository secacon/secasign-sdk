package com.secacon.secasignbox.sdk.dto.document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReadDocumentDto(

    UUID archiveId,

    UUID documentId,

    String documentName,

    Long documentSize,

    String originalDocumentHash,

    String signedDocumentHash,

    String textHash,

    Instant archivingDate,

    Instant retentionEndDate,

    SignatureTypeDto signatureType,

    DocumentSigningStatusDto signingStatus,

    DocumentProcessingStatusDto processingStatus,

    DocumentExtractionStatusDto extractionStatus,

    String errorMessage,

    List<ReadDocumentAttributeDto> documentAttributes,

    DocumentProcessingDto processing
) {
}
