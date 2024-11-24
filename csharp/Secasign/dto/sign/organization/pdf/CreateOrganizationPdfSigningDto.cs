namespace Secasign.dto.sign.organization.pdf;

public record CreateOrganizationPdfSigningDto(Guid signingId, List<CreateOrganizationPdfDocumentDto> documents);
