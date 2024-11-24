package com.secacon.secasign.sdk.document;

import com.secacon.secasign.extension.SdkConfiguration;
import com.secacon.secasign.extension.SdkTest;
import com.secacon.secasign.extension.SdkTestRequirements;
import com.secacon.secasign.sdk.SecasignHttpClient;
import com.secacon.secasign.sdk.dto.authentication.AuthenticationDto;
import com.secacon.secasign.sdk.dto.authentication.TokenDto;
import com.secacon.secasign.sdk.dto.document.*;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for showing document information, searching and downloading.
 *
 * @author Simon Wächter
 */
@SdkTest
public class DocumentTest {

    /**
     * Show the document information.
     *
     * @param sdkConfiguration SDK configuration
     * @throws Exception Exception in case of a problem
     */
    @SdkTestRequirements(isDocumentAvailable = true)
    public void testDocumentInformation(SdkConfiguration sdkConfiguration) throws Exception {
        // Create the HTTP client
        SecasignHttpClient secasignHttpClient = new SecasignHttpClient(sdkConfiguration.getUrl());

        // Obtain the token
        TokenDto tokenDto = secasignHttpClient.login(new AuthenticationDto(sdkConfiguration.getEmailAddress(), sdkConfiguration.getPassword()));

        // Get the document information by the document ID
        ReadDocumentDto readDocumentDto = secasignHttpClient.getDocumentById(tokenDto, sdkConfiguration.getDocumentId());
        assertEquals(sdkConfiguration.getDocumentId(), readDocumentDto.getId());
        assertEquals(SignatureTypeDto.PDF, readDocumentDto.getSignatureType());
    }

    /**
     * Search for documents and download them.
     *
     * @param sdkConfiguration SDK configuration
     * @throws Exception Exception in case of a problem
     */
    @SdkTestRequirements(isArchiveSearchAvailable = true)
    public void testDocumentSearchAndDownload(SdkConfiguration sdkConfiguration) throws Exception {
        // Create the HTTP client
        SecasignHttpClient secasignHttpClient = new SecasignHttpClient(sdkConfiguration.getUrl());

        // Obtain the token
        TokenDto tokenDto = secasignHttpClient.login(new AuthenticationDto(sdkConfiguration.getEmailAddress(), sdkConfiguration.getPassword()));

        // Search for the latest documents: First page with index 0, at most 10 document per page and by archive attribute with value
        List<ReadDocumentAttributeDto> readDocumentAttributeDtos = Collections.singletonList(new ReadDocumentAttributeDto(sdkConfiguration.getArchiveAttributeId(), sdkConfiguration.getArchiveAttributeValue()));
        DocumentSearchCriteriaDto documentSearchCriteriaDto = new DocumentSearchCriteriaDto(0, 10, null, null, null, null, null, null, null, null, null, null, null, readDocumentAttributeDtos);
        DocumentSearchResultDto documentSearchResultDto = secasignHttpClient.searchDocuments(tokenDto, documentSearchCriteriaDto);

        // Fail if no documents were found
        if (documentSearchResultDto.getTotalDocuments() == 0) {
            Assertions.fail("No documents were found.");
        }

        // Get the first document to get its document ID
        ReadDocumentDto readDocumentDto = documentSearchResultDto.getDocuments().get(0);
        assertEquals(SignatureTypeDto.PDF, readDocumentDto.getSignatureType());
        UUID documentId = readDocumentDto.getId();

        // Get the current timestamp
        long timestamp = System.currentTimeMillis();

        // Download the original PDF document
        byte[] originalPdfDocumentData = secasignHttpClient.downloadOriginalDocument(tokenDto, documentId);
        File originalPdfDocument = new File("../data/Downloaded_Original_" + timestamp + ".pdf");
        Files.write(originalPdfDocument.toPath(), originalPdfDocumentData);
        System.out.println("The original document was successfully downloaded. Storing it at " + originalPdfDocument.getAbsolutePath());

        // Download the signed PDF document
        byte[] signedPdfDocumentData = secasignHttpClient.downloadSignedDocument(tokenDto, documentId);
        File signedPdfDocument = new File("../data/Downloaded_Signed_" + timestamp + ".pdf");
        Files.write(signedPdfDocument.toPath(), signedPdfDocumentData);
        System.out.println("The signed document was successfully downloaded. Storing it at " + signedPdfDocument.getAbsolutePath());
    }
}
