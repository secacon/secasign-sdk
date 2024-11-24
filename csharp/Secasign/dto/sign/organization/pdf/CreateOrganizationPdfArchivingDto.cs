namespace Secasign.dto.sign.organization.pdf;

public record CreateOrganizationPdfArchivingDto(Guid processingRuleId, List<CreateOrganizationPdfDocumentDto> documents);
