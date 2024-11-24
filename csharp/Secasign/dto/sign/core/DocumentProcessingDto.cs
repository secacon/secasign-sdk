using Secasign.dto.document;

namespace Secasign.dto.sign.core;

public record DocumentProcessingDto(DateTimeOffset creationDate, Guid processingRuleId, DocumentLocationDto? inputLocation, List<DocumentLocationDto>? outputLocations);
