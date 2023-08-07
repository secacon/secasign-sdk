package com.secacon.secasignbox.sdk.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.secacon.secasignbox.sdk.dto.*;
import com.secacon.secasignbox.sdk.utils.Base64Utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SecasignBoxHttpClient {

    static {
        // See available output: https://stackoverflow.com/questions/53215038/how-to-log-request-response-using-java-net-http-httpclient
        System.setProperty("jdk.httpclient.HttpClient.log", "requests,responses,headers,content,frames,all");
    }

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient;

    private final String url;

    public SecasignBoxHttpClient(String url) {
        // Create the object mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Create the HTTP client
        this.httpClient = HttpClient.newHttpClient();
        this.url = url;
    }

    public TokenDto login(AuthenticationDto authenticationDto) throws Exception {
        HttpResponse<String> httpResponse = executeRequest("POST", "/api/login", null, authenticationDto);
        return objectMapper.readValue(httpResponse.body(), TokenDto.class);
    }

    public ReadOrganizationDocumentDto signOrganizationPdfDocuments(TokenDto tokenDto, UUID signingId, byte[] documentData, String documentName, Boolean protectedPdfSigning, SignatureStrategyDto signatureStrategyDto) throws Exception {
        CreateOrganizationPdfDocumentDto createOrganizationPdfDocumentDto = new CreateOrganizationPdfDocumentDto(Base64Utils.encodeBytes(documentData), documentName, protectedPdfSigning, signatureStrategyDto);
        CreateOrganizationPdfSigningDto createOrganizationPdfSigningDto = new CreateOrganizationPdfSigningDto(signingId, List.of(createOrganizationPdfDocumentDto));
        HttpResponse<String> httpResponse = executeRequest("POST", "/api/sign/organization/sign/pdf", tokenDto, createOrganizationPdfSigningDto);
        return Arrays.asList(objectMapper.readValue(httpResponse.body(), ReadOrganizationDocumentDto[].class)).get(0);
    }

    public HttpResponse<String> executeRequest(String method, String uri, TokenDto tokenDto, Object object) throws Exception {
        // Create the HTTP request
        URI fullUri = URI.create(url + uri);
        HttpRequest.Builder httpRequestBuilder = handleHttpMethod(HttpRequest.newBuilder(fullUri), method, object);
        httpRequestBuilder.setHeader("Content-Type", "application/json");
        httpRequestBuilder.setHeader("Accept", "application/json");

        // Add an optional JWT token
        if (tokenDto != null) {
            httpRequestBuilder.header("Authorization", "Bearer " + tokenDto.token());
        }

        // Execute request
        HttpRequest httpRequest = httpRequestBuilder.build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        // Check the result
        if (httpResponse.statusCode() != 200) {
            throw new RuntimeException("HTTP response status code is not 200 but " + httpResponse.statusCode() + ": " + httpResponse.body());
        }

        // Return the response
        return httpResponse;
    }

    public HttpRequest.BodyPublisher getBodyPublisher(Object object) throws Exception {
        // Check if body
        if (object != null) {
            String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println("Body: " + body);
            return HttpRequest.BodyPublishers.ofString(body);
        } else {
            System.out.println("No body");
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    public HttpRequest.Builder handleHttpMethod(HttpRequest.Builder httpRequestBuilder, String method, Object object) throws Exception {
        // Create the body
        HttpRequest.BodyPublisher bodyPublisher = getBodyPublisher(object);

        // Set the method and body
        return switch (method) {
            case "GET" -> httpRequestBuilder.GET();
            case "POST" -> httpRequestBuilder.POST(bodyPublisher);
            case "PUT" -> httpRequestBuilder.PUT(bodyPublisher);
            case "DELETE" -> httpRequestBuilder.DELETE();
            default -> throw new RuntimeException("Unsupported method " + method);
        };
    }
}
