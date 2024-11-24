namespace Secasign.dto.document;

public record DocumentSearchCriteriaDto(int page, int size, HashSet<Guid>? archiveIds, Guid? documentId, string? documentName, long? documentSize, DateTimeOffset? startDate, DateTimeOffset? endDate, bool? expired, string? text, DocumentSearchStatusDto? status, DocumentProcessingStatusDto? procesingStatus, DocumentExtractionStatusDto? extractionStatus, List<ReadDocumentAttributeDto>? documentAttributes);
