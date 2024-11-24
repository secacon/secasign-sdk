using System.Globalization;
using Secasign;
using Secasign.dto.authentication;
using Secasign.dto.document;
using Secasign.dto.sign.core;
using Secasign.dto.sign.organization.pdf;
using SecasignTest.extension;

namespace SecasignTest.sdk.sign;

public class ArchiveOrganizationPdfTest
{
    private static SdkConfiguration SdkConfiguration => SdkExtension.SdkConfiguration;

    [SdkExtension(SdkTestRequirements.IsArchivingAvailable)]
    public async Task TestArchiveOrganizationPdfDocumentWithVisualSignature()
    {
        // Create the HTTP client
        var secasignHttpClient = new SecasignHttpClient(SdkConfiguration.Url!);

        // Login and obtain a JWT token
        var authenticationDto = new AuthenticationDto(SdkConfiguration.EmailAddress!, SdkConfiguration.Password!);
        var tokenDto = await secasignHttpClient.Login(authenticationDto);
        Assert.NotNull(tokenDto.token);

        // Define the processing rule ID that processes the documents for the archive
        var processingRuleId = (Guid)SdkConfiguration.ProcessingRuleId!;

        // Read the unsigned PDF document and Base64 encode it
        var unsignedPdfDocument = new FileInfo("../../../../../data/Unsigned.pdf");
        Assert.True(unsignedPdfDocument.Exists);
        var unsignedPdfDocumentData = await File.ReadAllBytesAsync(unsignedPdfDocument.FullName);
        var encodedUnsignedPdfDocumentData = Convert.ToBase64String(unsignedPdfDocumentData);

        // Read the PNG/JPG signature and Base64 encode it
        var pngSignature = new FileInfo("../../../../../data/Signature.png");
        Assert.True(pngSignature.Exists);
        var pngSignatureData = await File.ReadAllBytesAsync(pngSignature.FullName);
        var encodedPngSignature = Convert.ToBase64String(pngSignatureData);

        // Flag whether we rip out the signature/PDF encryption in case a PDF is "encrypted" (Encrypted means you are not allowed to change a signature/add a new signature - it is not really encrypted).
        // This is like a gentleman's agreement: You can follow it and are unable to sign the PDF document or just rip it out (Protection/existing signatures) and sign the document
        // Set this value to null or false if you don't know what this means
        bool? protectedPdfSigning = null; // or false

        // Get the date and time
        var timeZone = TimeZoneInfo.FindSystemTimeZoneById("Europe/Zurich");
        var localDateTime = TimeZoneInfo.ConvertTimeFromUtc(DateTime.UtcNow, timeZone);
        var date = localDateTime.ToString("yyyy.MM.dd", CultureInfo.InvariantCulture);
        var time = localDateTime.ToString("HH:mm", CultureInfo.InvariantCulture);

        // Define the visual signature
        var signatureStrategyDto = new VisualSignatureStrategyDto(
            encodedPngSignature, // Set visible signature graphic to null because we don't want a visible signature
            "Digitally signed by\nSimon Wächter\nDate: " + date + "\n" + time + " CET", // Set visible signature description. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Barack Obama)
            null, // Set appearance to null. Internally NOT_CERTIFIED will be used and the document signature gets a green tick
            SignatureRenderingDto.GRAPHIC_AND_DESCRIPTION, // Render the PNG signature and the signature description above. Several different renderings are possible. See the documentation
            "Simon Wächter", // Person that signed the document. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Barack Obama)
            "simon.waechter@secacon.com", // Contact for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. barack.obama@whitehouse.gov)
            "Secasign SDK Showcase", // Reason for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Aliens in Area 51)
            "Basel", // Location for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Moon)
            null, // Preservation size of the signature. Null because we use the default value of 30720 bytes
            null, // Name of the signature field. Null because we choose it internally
            1, // Page number of the signature, here first/only page
            null, // Use mm instead of points
            75, // Left page distance in millimeters of the signature, here 75 mm
            50, // Top page distance in millimeters of the signature, here 50 mm
            null, // Bottom page distance in millimeters of the signature, here null because top page distance is set
            75, // With in millimeters of the signature, here 75 mm
            25, // Height in millimeters of the signature, here 25 mm
            "Helvetica", // Font type of the signature
            12, // Font size of the signature, here 12
            0, // Use a black signature description font color
            0, // Use a black signature description font color
            0 // Use a black signature description font color
        );

        // Sign and archive the document
        var createOrganizationPdfDocumentDto = new CreateOrganizationPdfDocumentDto(encodedUnsignedPdfDocumentData, unsignedPdfDocument.Name, protectedPdfSigning, signatureStrategyDto);
        var createOrganizationPdfArchivingDto = new CreateOrganizationPdfArchivingDto(processingRuleId, [createOrganizationPdfDocumentDto]);
        var readOrganizationDocumentDtos = await secasignHttpClient.SignAndArchiveOrganizationPdfDocuments(tokenDto, createOrganizationPdfArchivingDto);

        // Handle the result
        var readOrganizationDocumentDto = readOrganizationDocumentDtos.First();
        if (readOrganizationDocumentDto is { signingStatus: DocumentSigningStatusDto.SIGNED, encodedSignedDocumentOrSignature: not null })
        {
            // Check the archived document
            var readDocumentDto = readOrganizationDocumentDto.document;
            Assert.NotNull(readDocumentDto);

            // Write the signed file
            var signedPdfDocument = new FileInfo("../../../../../data/Signed_" + DateTimeOffset.UtcNow.ToUnixTimeMilliseconds() + ".pdf");
            await File.WriteAllBytesAsync(signedPdfDocument.FullName, Convert.FromBase64String(readOrganizationDocumentDto.encodedSignedDocumentOrSignature));
        }
        else if (readOrganizationDocumentDto.signingStatus == DocumentSigningStatusDto.FAILED)
        {
            Assert.Fail("Unable to sign and archive the document. Error: " + readOrganizationDocumentDto.errorMessage);
        }
    }
}
