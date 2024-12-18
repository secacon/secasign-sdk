﻿namespace Secasign.dto.sign.core;

public record InvisibleSignatureStrategyDto(string? signerName, string? signerContact, string? signatureReason, string? signatureLocation, int? signaturePreservationSize, string? signatureFieldName) : ISignatureStrategyDto
{
    public SignatureStrategyTypeDto type()
    {
        return SignatureStrategyTypeDto.INVISIBLE_SIGNATURE;
    }
}
