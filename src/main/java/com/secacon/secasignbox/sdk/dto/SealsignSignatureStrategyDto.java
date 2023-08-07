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

    Integer signatureRgb1,

    Integer signatureRgb2,

    Integer signatureRgb3
) implements SignatureStrategyDto {

    @Override
    public SignatureStrategyTypeDto getType() {
        return type;
    }
}
