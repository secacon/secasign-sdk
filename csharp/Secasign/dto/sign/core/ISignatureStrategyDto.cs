using System.Text.Json.Serialization;

namespace Secasign.dto.sign.core;

[JsonPolymorphic(TypeDiscriminatorPropertyName = "type")]
[JsonDerivedType(typeof(VisualSignatureStrategyDto), "VISUAL_SIGNATURE")]
[JsonDerivedType(typeof(SignatureFieldSignatureStrategyDto), "SIGNATURE_FIELD")]
[JsonDerivedType(typeof(InvisibleSignatureStrategyDto), "INVISIBLE_SIGNATURE")]
public interface ISignatureStrategyDto
{
    SignatureStrategyTypeDto type();
}
