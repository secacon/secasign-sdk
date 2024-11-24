namespace SecasignTest.extension;

public class SdkConfiguration
{
    public string? Url { get; init; }

    public string? EmailAddress { get; init; }

    public string? Password { get; init; }

    public Guid? SigningId { get; init; }

    public Guid? ProcessingRuleId { get; init; }

    public Guid? DocumentId { get; init; }

    public Guid? ArchiveAttributeId { get; init; }

    public string? ArchiveAttributeValue { get; init; }
}
