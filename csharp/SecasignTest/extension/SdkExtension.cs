namespace SecasignTest.extension;

public class SdkExtension(params string[] conditions) : FactAttribute, IClassFixture<SdkConfiguration>
{
    public static SdkConfiguration SdkConfiguration { get; } = ReadSdkConfiguration("../../../../../configuration.properties");

    public override string? Skip
    {
        get
        {
            // Check all conditions
            var isLoginAvailable = SdkConfiguration.Url != null && SdkConfiguration is { EmailAddress: not null, Password: not null };
            var isSigningAvailable = SdkConfiguration.SigningId != null;
            var isArchivingAvailable = SdkConfiguration.ProcessingRuleId != null;
            var isDocumentAvailable = SdkConfiguration.DocumentId != null;
            var isArchiveSearchAvailable = SdkConfiguration is { ArchiveAttributeId: not null, ArchiveAttributeValue: not null };

            // Check all conditions
            foreach (var condition in conditions)
            {
                // Check if login is required and available
                if (condition.Equals(SdkTestRequirements.IsLoginAvailable) && !isLoginAvailable)
                {
                    return "Please set the credentials to non-null values in the Values.cs file!";
                }

                // Check if signing is required and available
                if (condition.Equals(SdkTestRequirements.IsSigningAvailable) && (!isLoginAvailable || !isSigningAvailable))
                {
                    return "Please set the credentials/signing ID to non-null values in the Values.cs file!";
                }

                // Check if archiving is required and available
                if (condition.Equals(SdkTestRequirements.IsArchivingAvailable) && (!isLoginAvailable || !isArchivingAvailable))
                {
                    return "Please set the credentials/processing rule ID to non-null values in the Values.cs file!";
                }

                // Check if document information is required and available
                if (condition.Equals(SdkTestRequirements.IsDocumentAvailable) && (!isLoginAvailable || !isDocumentAvailable))
                {
                    return "Please set the credentials/processing rule ID to non-null values in the Values.java file!";
                }

                // Check if archive search is required and available
                if (condition.Equals(SdkTestRequirements.IsArchiveSearchAvailable) && (!isLoginAvailable || !isArchiveSearchAvailable))
                {
                    return "Please set the credentials/archive attribute ID/value to non-null values in the Values.java file!";
                }
            }

            // All requirements are fulfilled
            return null;
        }
    }

    private static SdkConfiguration ReadSdkConfiguration(string configurationPropertieName)
    {
        // Read all values but don't parse them
        var properties = ReadPropertiesFile(configurationPropertieName);
        var url = properties!.GetValueOrDefault("secasign.url", null);
        var emailAddress = properties!.GetValueOrDefault("secasign.email_address", null);
        var password = properties!.GetValueOrDefault("secasign.password", null);
        var signingId = properties!.GetValueOrDefault("secasign.signing_id", null);
        var processingRuleId = properties!.GetValueOrDefault("secasign.processing_rule_id", null);
        var documentId = properties!.GetValueOrDefault("secasign.document_id", null);
        var archiveAttributeId = properties!.GetValueOrDefault("secasign.archive_attribute_id", null);
        var archiveAttributeValue = properties!.GetValueOrDefault("secasign.archive_attribute_value", null);

        // Check and parse the values and return the configuration
        return new SdkConfiguration
        {
            Url = IsValueNotBlank(url) ? url : null,
            EmailAddress = IsValueNotBlank(emailAddress) ? emailAddress : null,
            Password = IsValueNotBlank(password) ? password : null,
            SigningId = IsValueNotBlank(signingId) ? Guid.Parse(signingId!) : null,
            ProcessingRuleId = IsValueNotBlank(processingRuleId) ? Guid.Parse(processingRuleId!) : null,
            DocumentId = IsValueNotBlank(documentId) ? Guid.Parse(documentId!) : null,
            ArchiveAttributeId = IsValueNotBlank(archiveAttributeId) ? Guid.Parse(archiveAttributeId!) : null,
            ArchiveAttributeValue = IsValueNotBlank(archiveAttributeValue) ? archiveAttributeValue : null,
        };
    }

    private static Dictionary<string, string> ReadPropertiesFile(string configurationPropertieName)
    {
        // Define the property dictionary
        var properties = new Dictionary<string, string>();

        // Read all lines
        foreach (var line in File.ReadAllLines(configurationPropertieName))
        {
            // Ignore empty lines
            if (string.IsNullOrWhiteSpace(line) || line.TrimStart().StartsWith('#'))
            {
                continue;
            }

            // Skip lines that do not contain two elements
            var parameters = line.Split(['='], 2); // Split at most two elements
            if (parameters.Length != 2)
            {
                continue;
            }

            // Get the key and value
            var key = parameters[0].Trim();
            var value = parameters[1].Trim();

            // Add the property value
            properties.Add(key, value);
        }

        // Return the properties
        return properties;
    }

    private static bool IsValueNotBlank(string? value)
    {
        return !string.IsNullOrEmpty(value) && !string.IsNullOrWhiteSpace(value);
    }
}
