namespace Secasign.dto.sign.core;

public record SignatureFieldSignatureStrategyDto(string? signerName, string? signatureContact, string? signatureReason, string? signatureLocation, int? signaturePreservationSize, string signatureFieldName) : ISignatureStrategyDto
{
    public SignatureStrategyTypeDto type()
    {
        return SignatureStrategyTypeDto.INVISIBLE_SIGNATURE;
    }
}
