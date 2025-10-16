package com.secacon.secasign.sdk;

import com.secacon.secasign.sdk.dto.authentication.AuthenticationDto;
import com.secacon.secasign.sdk.dto.authentication.TokenDto;
import com.secacon.secasign.sdk.dto.document.DocumentSearchCriteriaDto;
import com.secacon.secasign.sdk.dto.document.DocumentSearchResultDto;
import com.secacon.secasign.sdk.dto.document.ReadDocumentDto;
import com.secacon.secasign.sdk.dto.sign.ReadOrganizationDocumentDto;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfArchivingDto;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfSigningDto;
import tools.jackson.databind.json.JsonMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SecasignHttpClient {

    private final JsonMapper jsonMapper;

    private final String baseUrl;

    public SecasignHttpClient(String baseUrl) {
        this(baseUrl, true);
    }

    public SecasignHttpClient(String baseUrl, boolean prettyPrint) {
        // Create the JSON builder
        JsonMapper.Builder builder = JsonMapper.builder();

        // Enable pretty print if desired
        if (prettyPrint) {
            builder.enable(tools.jackson.databind.SerializationFeature.INDENT_OUTPUT);
        }

        // Build the JSON mapper and set the base URL
        this.jsonMapper = builder.build();
        this.baseUrl = baseUrl;
    }

    public TokenDto login(AuthenticationDto authenticationDto) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/login", null, authenticationDto);
        return jsonMapper.readValue(readStringResponse(httpUrlConnection, false), TokenDto.class);
    }

    public ReadDocumentDto getDocumentById(TokenDto tokenDto, UUID documentId) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("GET", "/api/documents/" + documentId, tokenDto, null);
        return jsonMapper.readValue(readStringResponse(httpUrlConnection, false), ReadDocumentDto.class);
    }

    public byte[] downloadOriginalDocument(TokenDto tokenDto, UUID documentId) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("GET", "/api/documents/original/" + documentId, tokenDto, null);
        return readByteResponse(httpUrlConnection);
    }

    public byte[] downloadSignedDocument(TokenDto tokenDto, UUID documentId) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("GET", "/api/documents/signed/" + documentId, tokenDto, null);
        return readByteResponse(httpUrlConnection);
    }

    public DocumentSearchResultDto searchDocuments(TokenDto tokenDto, DocumentSearchCriteriaDto documentSearchCriteriaDto) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/documents/search", tokenDto, documentSearchCriteriaDto);
        return jsonMapper.readValue(readStringResponse(httpUrlConnection, false), DocumentSearchResultDto.class);
    }

    public List<ReadOrganizationDocumentDto> signOrganizationPdfDocuments(TokenDto tokenDto, CreateOrganizationPdfSigningDto createOrganizationPdfSigningDto) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/sign/organization/sign/pdf", tokenDto, createOrganizationPdfSigningDto);
        return Arrays.asList(jsonMapper.readValue(readStringResponse(httpUrlConnection, false), ReadOrganizationDocumentDto[].class));
    }

    public List<ReadOrganizationDocumentDto> signAndArchiveOrganizationPdfDocuments(TokenDto tokenDto, CreateOrganizationPdfArchivingDto createOrganizationPdfArchivingDto) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/sign/organization/archive/pdf", tokenDto, createOrganizationPdfArchivingDto);
        return Arrays.asList(jsonMapper.readValue(readStringResponse(httpUrlConnection, false), ReadOrganizationDocumentDto[].class));
    }

    private HttpURLConnection executeRequest(String method, String uri, TokenDto tokenDto, Object payload) throws Exception {
        // Create the connection
        URL url = URI.create(baseUrl + uri).toURL();
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod(method);
        httpUrlConnection.setRequestProperty("User-Agent", "Secasign SDK");
        httpUrlConnection.setDoOutput(method.equals("POST") || method.equals("PUT"));
        httpUrlConnection.setRequestProperty("Accept", "*/*");

        // For POST, PUT and DELETE set JSON as content type
        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
        }

        // Add the authentication header
        if (tokenDto != null) {
            httpUrlConnection.setRequestProperty("Authorization", "Bearer " + tokenDto.getToken());
        }

        // Write the request body if available
        if (payload != null) {
            String body = jsonMapper.writeValueAsString(payload);
            System.out.println("Request body for " + uri + ":");
            System.out.println(body);
            try (OutputStream outputStream = httpUrlConnection.getOutputStream()) {
                outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }

        // Check the response status code
        int statusCode = httpUrlConnection.getResponseCode();
        if (statusCode != 200) {
            throw new RuntimeException("Invalid HTTP status code " + statusCode + ": " + readStringResponse(httpUrlConnection, true));
        }

        // Return the URL connection
        return httpUrlConnection;
    }

    private String readStringResponse(HttpURLConnection connection, boolean useErrorStream) throws IOException {
        // Read the response
        String line;
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(useErrorStream ? new InputStreamReader(connection.getErrorStream()) : new InputStreamReader(connection.getInputStream()))) {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        }

        // Get the response
        String body = builder.toString();

        // Log the response
        System.out.println("Response:");
        System.out.println(body);

        // Return the body
        return body;
    }

    private byte[] readByteResponse(HttpURLConnection connection) throws IOException {
        // Read the response
        InputStream inputStream = connection.getInputStream();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            for (int length = inputStream.read(buffer); length != -1; length = inputStream.read(buffer)) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toByteArray();
        }
    }
}
