using System.Net;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using Secasign.dto.authentication;
using Secasign.dto.document;
using Secasign.dto.sign;
using Secasign.dto.sign.organization.pdf;

namespace Secasign;

public class SecasignHttpClient(string hostname)
{
    private readonly HttpClient _httpClient = new();

    public async Task<TokenDto> Login(AuthenticationDto authenticationDto)
    {
        var response = await ExecuteRequest(HttpMethod.Post, "/api/login", null, authenticationDto);
        return await ParseResponse<TokenDto>(response);
    }

    public async Task<ReadDocumentDto> GetDocumentById(TokenDto tokenDto, Guid documentId)
    {
        var response = await ExecuteRequest(HttpMethod.Get, "/api/documents/" + documentId, tokenDto, null);
        return await ParseResponse<ReadDocumentDto>(response);
    }

    public async Task<DocumentSearchResultDto> SearchDocuments(TokenDto tokenDto, DocumentSearchCriteriaDto documentSearchCriteriaDto)
    {
        var response = await ExecuteRequest(HttpMethod.Post, "/api/documents/search", tokenDto, documentSearchCriteriaDto);
        return await ParseResponse<DocumentSearchResultDto>(response);
    }

    public async Task<byte[]> DownloadOriginalDocument(TokenDto tokenDto, Guid documentId)
    {
        var response = await ExecuteRequest(HttpMethod.Get, "/api/documents/original/" + documentId, tokenDto, null);
        return await response.Content.ReadAsByteArrayAsync();
    }

    public async Task<byte[]> DownloadSignedDocument(TokenDto tokenDto, Guid documentId)
    {
        var response = await ExecuteRequest(HttpMethod.Get, "/api/documents/signed/" + documentId, tokenDto, null);
        return await response.Content.ReadAsByteArrayAsync();
    }

    public async Task<List<ReadOrganizationDocumentDto>> SignOrganizationPdfDocuments(TokenDto tokenDto, CreateOrganizationPdfSigningDto createOrganizationPdfSigningDto)
    {
        var response = await ExecuteRequest(HttpMethod.Post, "/api/sign/organization/sign/pdf", tokenDto, createOrganizationPdfSigningDto);
        return await ParseResponse<List<ReadOrganizationDocumentDto>>(response);
    }

    public async Task<List<ReadOrganizationDocumentDto>> SignAndArchiveOrganizationPdfDocuments(TokenDto tokenDto, CreateOrganizationPdfArchivingDto createOrganizationPdfArchivingDto)
    {
        var response = await ExecuteRequest(HttpMethod.Post, "/api/sign/organization/archive/pdf", tokenDto, createOrganizationPdfArchivingDto);
        return await ParseResponse<List<ReadOrganizationDocumentDto>>(response);
    }

    private async Task<HttpResponseMessage> ExecuteRequest(HttpMethod method, string uri, TokenDto? tokenDto, object? payload)
    {
        // Prepare the request
        var request = new HttpRequestMessage(method, hostname + uri);
        request.Headers.UserAgent.ParseAdd("Secasign-SDK");
        request.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("*/*"));

        // Add the authentication header
        if (tokenDto != null)
        {
            request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", tokenDto.token);
        }

        // Write the request body if available
        if (payload != null)
        {
            var json = JsonSerializer.Serialize(payload, BuildSerializerOptions());
            request.Content = new StringContent(json, Encoding.UTF8);
            request.Content.Headers.ContentType = new MediaTypeHeaderValue("application/json");
        }

        // Execute the request
        var response = await _httpClient.SendAsync(request);

        // Check the response status code
        var statusCode = response.StatusCode;
        if (statusCode != HttpStatusCode.OK)
        {
            throw new Exception($"Invalid HTTP status code {response.StatusCode}: {await response.Content.ReadAsStringAsync()}");
        }

        // Return the response
        return response;
    }

    private static async Task<T> ParseResponse<T>(HttpResponseMessage response)
    {
        // Get the content and ensure it is not empty
        var content = await response.Content.ReadAsStringAsync();
        if (string.IsNullOrEmpty(content))
        {
            throw new Exception("The response content is empty");
        }

        // Deserialize the body
        return JsonSerializer.Deserialize<T>(content, BuildSerializerOptions())!;
    }

    private static JsonSerializerOptions BuildSerializerOptions()
    {
        // Build custom options
        return new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true, // Ignore case insenitivity
            Converters = { new JsonStringEnumConverter() } // Handle enum as strings and not numbers
        };
    }
}
