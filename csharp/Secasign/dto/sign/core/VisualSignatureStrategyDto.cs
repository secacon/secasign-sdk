namespace Secasign.dto.sign.core;

public record VisualSignatureStrategyDto(string? encodedGraphic, string? signatureDescription, SignatureAppearanceDto? signatureAppearance, SignatureRenderingDto? signatureRendering, string? signerName, string? signatureContact, string? signatureReason, string? signatureLocation, int? signaturePreservationSize, string? signatureFieldName, int? signaturePage, bool? signatureUsePoints, int? signatureLeft, int? signatureTop, int? signatureBottom, int? signatureWidth, int? signatureHeight, string? signatureFont, int? signatureFontSize, int? signatureFontRgb1, int? signatureFontRgb2, int? signatureFontRgb3) : ISignatureStrategyDto
{
    public SignatureStrategyTypeDto type()
    {
        return SignatureStrategyTypeDto.VISUAL_SIGNATURE;
    }
}
