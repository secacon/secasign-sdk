namespace Secasign.dto.document;

public record DocumentSearchResultDto(List<ReadDocumentDto> documents, int totalDocuments, int totalPages, int page, int size);
