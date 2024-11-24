using Secasign.dto.sign.core;

namespace Secasign.dto.sign.organization.pdf;

public record CreateOrganizationPdfDocumentDto(string encodedDocument, string documentName, bool? protectedPdfSigning, ISignatureStrategyDto? signatureStrategy);
