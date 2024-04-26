package com.secacon.secasign.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.secacon.secasign.sdk.dto.authentication.AuthenticationDto;
import com.secacon.secasign.sdk.dto.authentication.TokenDto;
import com.secacon.secasign.sdk.dto.sign.ReadOrganizationDocumentDto;
import com.secacon.secasign.sdk.dto.sign.general.SignatureStrategyDto;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfArchivingDto;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfDocumentDto;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfSigningDto;
import com.secacon.secasign.sdk.utils.Base64Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class SecasignHttpClient {

    private final ObjectMapper objectMapper;

    private final String baseUrl;

    public SecasignHttpClient(String baseUrl) {
        this(baseUrl, true);
    }

    public SecasignHttpClient(String baseUrl, boolean prettyPrint) {
        // Create the object mapper with the new Java time module
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        // Enable pretty print if desired
        if (prettyPrint) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        // Set the base URL
        this.baseUrl = baseUrl;
    }

    public TokenDto login(AuthenticationDto authenticationDto) throws Exception {
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/login", null, authenticationDto);
        return objectMapper.readValue(readResponse(httpUrlConnection, false), TokenDto.class);
    }

    public ReadOrganizationDocumentDto signOrganizationPdfDocuments(TokenDto tokenDto, UUID signingId, byte[] documentData, String documentName, Boolean protectedPdfSigning, SignatureStrategyDto signatureStrategyDto) throws Exception {
        CreateOrganizationPdfDocumentDto createOrganizationPdfDocumentDto = new CreateOrganizationPdfDocumentDto(Base64Utils.encodeBytes(documentData), documentName, protectedPdfSigning, signatureStrategyDto);
        CreateOrganizationPdfSigningDto createOrganizationPdfSigningDto = new CreateOrganizationPdfSigningDto(signingId, Collections.singletonList(createOrganizationPdfDocumentDto));
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/sign/organization/sign/pdf", tokenDto, createOrganizationPdfSigningDto);
        return Arrays.asList(objectMapper.readValue(readResponse(httpUrlConnection, false), ReadOrganizationDocumentDto[].class)).get(0);
    }

    public ReadOrganizationDocumentDto signAndArchiveOrganizationPdfDocuments(TokenDto tokenDto, UUID processingRuleId, byte[] documentData, String documentName, Boolean protectedPdfSigning, SignatureStrategyDto signatureStrategyDto) throws Exception {
        CreateOrganizationPdfDocumentDto createOrganizationPdfDocumentDto = new CreateOrganizationPdfDocumentDto(Base64Utils.encodeBytes(documentData), documentName, protectedPdfSigning, signatureStrategyDto);
        CreateOrganizationPdfArchivingDto createOrganizationPdfArchivingDto = new CreateOrganizationPdfArchivingDto(processingRuleId, Collections.singletonList(createOrganizationPdfDocumentDto));
        HttpURLConnection httpUrlConnection = executeRequest("POST", "/api/sign/organization/archive/pdf", tokenDto, createOrganizationPdfArchivingDto);
        return Arrays.asList(objectMapper.readValue(readResponse(httpUrlConnection, false), ReadOrganizationDocumentDto[].class)).get(0);
    }

    public HttpURLConnection executeRequest(String method, String uri, TokenDto tokenDto, Object object) throws Exception {
        // Create the connection
        URL url = URI.create(baseUrl + uri).toURL();
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod(method);
        httpUrlConnection.setRequestProperty("User-Agent", "Secasign SDK");
        httpUrlConnection.setDoOutput(method.equals("POST") || method.equals("PUT"));
        httpUrlConnection.setRequestProperty("Content-Type", "application/json");
        httpUrlConnection.setRequestProperty("Accept", "application/json");

        // Add the authentication header
        if (tokenDto != null) {
            httpUrlConnection.setRequestProperty("Authorization", "Bearer " + tokenDto.getToken());
        }

        // Write the request body if available
        if (object != null) {
            String body = objectMapper.writeValueAsString(object);
            System.out.println("Request body for " + uri + ":");
            System.out.println(body);
            try (OutputStream outputStream = httpUrlConnection.getOutputStream()) {
                outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }

        // Check the response status code
        int statusCode = httpUrlConnection.getResponseCode();
        if (statusCode != 200) {
            throw new RuntimeException("Invalid HTTP status code " + statusCode + ": " + readResponse(httpUrlConnection, true));
        }

        // Return the URL connection
        return httpUrlConnection;
    }

    protected String readResponse(HttpURLConnection connection, boolean useErrorStream) throws IOException {
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
}
