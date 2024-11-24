using Secasign;
using Secasign.dto.authentication;
using Secasign.dto.document;
using SecasignTest.extension;

namespace SecasignTest.sdk.document;

public class DocumentTest
{
    private static SdkConfiguration SdkConfiguration => SdkExtension.SdkConfiguration;

    [SdkExtension(SdkTestRequirements.IsDocumentAvailable)]
    public async Task TestDocumentInformation()
    {
        // Create the HTTP client
        var secasignHttpClient = new SecasignHttpClient(SdkConfiguration.Url!);

        // Login and obtain a JWT token
        var authenticationDto = new AuthenticationDto(SdkConfiguration.EmailAddress!, SdkConfiguration.Password!);
        var tokenDto = await secasignHttpClient.Login(authenticationDto);

        // Get the document ID
        var documentId = (Guid)SdkConfiguration.DocumentId!;

        // Get the document information by the document ID
        var readDocumentDto = await secasignHttpClient.GetDocumentById(tokenDto, documentId);
        Assert.Equal(SdkConfiguration.DocumentId, readDocumentDto.id);
        Assert.Equal(SignatureTypeDto.PDF, readDocumentDto.signatureType);
    }

    [SdkExtension(SdkTestRequirements.IsArchiveSearchAvailable)]
    public async Task TestDocumentSearchAndDownload()
    {
        // Create the HTTP client
        var secasignHttpClient = new SecasignHttpClient(SdkConfiguration.Url!);

        // Login and obtain a JWT token
        var authenticationDto = new AuthenticationDto(SdkConfiguration.EmailAddress!, SdkConfiguration.Password!);
        var tokenDto = await secasignHttpClient.Login(authenticationDto);

        // Get the archive attribute ID
        var archiveAttributeId = (Guid)SdkConfiguration.ArchiveAttributeId!;

        // Search for the latest documents: First page with index 0, at most 10 document per page and by archive attribute with value
        List<ReadDocumentAttributeDto> readDocumentAttributeDtos = [new(archiveAttributeId, SdkConfiguration.ArchiveAttributeValue!)];
        var documentSearchCriteriaDto = new DocumentSearchCriteriaDto(0, 10, null, null, null, null, null, null, null, null, null, null, null, readDocumentAttributeDtos);
        var documentSearchResultDto = await secasignHttpClient.SearchDocuments(tokenDto, documentSearchCriteriaDto);

        // Fail if no documents were found
        if (documentSearchResultDto.totalDocuments == 0)
        {
            Assert.Fail("No documents were found.");
        }

        // Get the first document to get its document ID
        var readDocumentDto = documentSearchResultDto.documents.First();
        Assert.Equal(SignatureTypeDto.PDF, readDocumentDto.signatureType);
        var documentId = readDocumentDto.id;

        // Get the current timestamp
        var timestamp = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();

        // Download the original PDF document
        var originalPdfDocumentData = await secasignHttpClient.DownloadOriginalDocument(tokenDto, documentId);
        var originalPdfDocument = new FileInfo("../../../../../data/Downloaded_Original_" + timestamp + ".pdf");
        await File.WriteAllBytesAsync(originalPdfDocument.FullName, originalPdfDocumentData);

        // Download the signed PDF document
        var signedPdfDocumentData = await secasignHttpClient.DownloadSignedDocument(tokenDto, documentId);
        var signedPdfDocument = new FileInfo("../../../../../data/Downloaded_Signed_" + timestamp + ".pdf");
        await File.WriteAllBytesAsync(signedPdfDocument.FullName, signedPdfDocumentData);
    }
}
