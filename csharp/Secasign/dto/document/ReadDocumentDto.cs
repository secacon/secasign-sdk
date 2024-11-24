using Secasign.dto.sign.core;

namespace Secasign.dto.document;

public record ReadDocumentDto(Guid id, Guid archiveId, string documentName, long documentSize, string originalDocumentHash, string signedDocumentHash, string textHash, DateTimeOffset archivingDate, DateTimeOffset retentionEndDate, SignatureTypeDto signatureType, DocumentProcessingStatusDto processingStatus, DocumentExtractionStatusDto extractionStatus, string? errorMessage, List<ReadDocumentAttributeDto> documentAttributes, DocumentProcessingDto? processing);
