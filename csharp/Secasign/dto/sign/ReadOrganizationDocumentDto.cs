using Secasign.dto.document;

namespace Secasign.dto.sign;

public record ReadOrganizationDocumentDto(DocumentSigningStatusDto? signingStatus, string? errorMessage, string documentName, string? encodedSignedDocumentOrSignature, SignatureTypeDto? signatureType, ReadDocumentDto? document);
