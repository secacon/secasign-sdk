package com.secacon.secasignbox.sdk.dto;

public record SealsignSignatureStrategyDto(

    SignatureStrategyTypeDto type,

    String encodedGraphic,

    String signatureDescription,

    SealsignSignatureAppearanceDto signatureAppearance,

    SealsignSignatureRenderingDto signatureRendering,

    String signerName,

    String signatureReason,

    String signatureLocation,

    String signatureContact,

    Integer signaturePreservationSize,

    String signatureFieldName,

    Integer signaturePage,

    Integer signatureLeft,

    Integer signatureTop,

    Integer signatureWidth,

    Integer signatureHeight,

    String signatureFont,

    Integer signatureFontSize,

    Integer signatureFontRgb1,

    Integer signatureFontRgb2,

    Integer signatureFontRgb3
) implements SignatureStrategyDto {

    @Override
    public SignatureStrategyTypeDto getType() {
        return type;
    }
}
