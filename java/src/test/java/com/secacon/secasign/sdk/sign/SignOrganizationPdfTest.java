package com.secacon.secasign.sdk.sign;

import com.secacon.secasign.extension.SdkConfiguration;
import com.secacon.secasign.extension.SdkTest;
import com.secacon.secasign.extension.SdkTestRequirements;
import com.secacon.secasign.sdk.SecasignHttpClient;
import com.secacon.secasign.sdk.dto.authentication.AuthenticationDto;
import com.secacon.secasign.sdk.dto.authentication.TokenDto;
import com.secacon.secasign.sdk.dto.document.DocumentSigningStatusDto;
import com.secacon.secasign.sdk.dto.sign.ReadOrganizationDocumentDto;
import com.secacon.secasign.sdk.dto.sign.core.*;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfDocumentDto;
import com.secacon.secasign.sdk.dto.sign.organization.pdf.CreateOrganizationPdfSigningDto;
import com.secacon.secasign.sdk.utils.Base64Utils;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test to sign PDF documents via organization signing with an invisible and visible signature.
 *
 * @author Simon Wächter
 */
@SdkTest
public class SignOrganizationPdfTest {

    /**
     * Sign a PDF document via organization signing and add a visible PNG signature + description on page 1.
     *
     * @param sdkConfiguration SDK configuration
     * @throws Exception Exception in case of a problem
     */
    @SdkTestRequirements(isSigningAvailable = true)
    public void testSignOrganizationPdfDocumentWithVisualSignature(SdkConfiguration sdkConfiguration) throws Exception {
        // Create the HTTP client
        SecasignHttpClient secasignHttpClient = new SecasignHttpClient(sdkConfiguration.getUrl());

        // Obtain the token
        TokenDto tokenDto = secasignHttpClient.login(new AuthenticationDto(sdkConfiguration.getEmailAddress(), sdkConfiguration.getPassword()));

        // Define the signing it that represents the organization certificate in Secasign (The link to the real certificate in the Swisscom-AIS HSM)
        UUID signingId = sdkConfiguration.getSigningId();

        // Read the unsigned PDF document and Base64 encode it
        File unsignedPdfDocument = new File("../data/Unsigned.pdf");
        assertTrue(unsignedPdfDocument.isFile());
        byte[] unsignedPdfDocumentData = Files.readAllBytes(unsignedPdfDocument.toPath());
        String encodedUnsignedPdfDocumentData = Base64Utils.encodeBytes(unsignedPdfDocumentData);

        // Read the PNG/JPG signature and Base64 encode it
        File pngSignature = new File("../data/Signature.png");
        assertTrue(pngSignature.isFile());
        byte[] pngSignatureData = Files.readAllBytes(pngSignature.toPath());
        String encodedPngSignature = Base64Utils.encodeBytes(pngSignatureData);

        // Flag whether we rip out the signature/PDF encryption in case a PDF is "encrypted" (Encrypted means you are not allowed to change a signature/add a new signature - it is not really encrypted).
        // This is like a gentleman's agreement: You can follow it and are unable to sign the PDF document or just rip it out (Protection/existing signatures) and sign the document
        // Set this value to null or false if you don't know what this means
        Boolean protectedPdfSigning = null; // or false

        // Get the date and time
        Instant now = Instant.now();
        String date = DateTimeFormatter.ofPattern("yyyy.MM.dd").withZone(ZoneId.of("Europe/Zurich")).format(now);
        String time = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Europe/Zurich")).format(now);

        // Define the visual signature
        SignatureStrategyDto signatureStrategyDto = new VisualSignatureStrategyDto(
            SignatureStrategyTypeDto.VISUAL_SIGNATURE, // Use the visual signature strategy
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

        // Sign the document
        CreateOrganizationPdfDocumentDto createOrganizationPdfDocumentDto = new CreateOrganizationPdfDocumentDto(encodedUnsignedPdfDocumentData, unsignedPdfDocument.getName(), protectedPdfSigning, signatureStrategyDto);
        CreateOrganizationPdfSigningDto createOrganizationPdfSigningDto = new CreateOrganizationPdfSigningDto(signingId, Collections.singletonList(createOrganizationPdfDocumentDto));
        List<ReadOrganizationDocumentDto> readOrganizationDocumentDtos = secasignHttpClient.signOrganizationPdfDocuments(tokenDto, createOrganizationPdfSigningDto);

        // Handle the result
        ReadOrganizationDocumentDto readOrganizationDocumentDto = readOrganizationDocumentDtos.get(0);
        if (readOrganizationDocumentDto.getSigningStatus() == DocumentSigningStatusDto.SIGNED) {
            // Write the signed file
            File signedPdfDocument = new File("../data/Signed_" + System.currentTimeMillis() + ".pdf");
            Files.write(signedPdfDocument.toPath(), Base64Utils.decodeString(readOrganizationDocumentDto.getEncodedSignedDocumentOrSignature()));
            System.out.println("The document was successfully signed. Storing it at " + signedPdfDocument.getAbsolutePath());
        } else if (readOrganizationDocumentDto.getSigningStatus() == DocumentSigningStatusDto.FAILED) {
            fail("Unable to sign the document. Error: " + readOrganizationDocumentDto.getErrorMessage());
        }
    }

    /**
     * Sign a PDF document via organization signing and add an invisible signature on page 1.
     *
     * @param sdkConfiguration SDK configuration
     * @throws Exception Exception in case of a problem
     */
    @SdkTestRequirements(isSigningAvailable = true)
    public void testSignOrganizationPdfDocumentWithInvisibleSignature(SdkConfiguration sdkConfiguration) throws Exception {
        // Create the HTTP client
        SecasignHttpClient secasignHttpClient = new SecasignHttpClient(sdkConfiguration.getUrl());

        // Obtain the token
        TokenDto tokenDto = secasignHttpClient.login(new AuthenticationDto(sdkConfiguration.getEmailAddress(), sdkConfiguration.getPassword()));

        // Define the signing it that represents the organization certificate in Secasign (The link to the real certificate in the Swisscom-AIS HSM)
        UUID signingId = sdkConfiguration.getSigningId();

        // Read the unsigned PDF document and Base64 encode it
        File unsignedPdfDocument = new File("../data/Unsigned.pdf");
        assertTrue(unsignedPdfDocument.isFile());
        byte[] unsignedPdfDocumentData = Files.readAllBytes(unsignedPdfDocument.toPath());
        String encodedUnsignedPdfDocumentData = Base64Utils.encodeBytes(unsignedPdfDocumentData);

        // Flag whether we rip out the signature/PDF encryption in case a PDF is "encrypted" (Encrypted means you are not allowed to change a signature/add a new signature - it is not really encrypted).
        // This is like a gentleman's agreement: You can follow it and are unable to sign the PDF document or just rip it out (Protection/existing signatures) and sign the document
        // Set this value to null or false if you don't know what this means
        Boolean protectedPdfSigning = null; // or false

        // Define the invisible signature
        SignatureStrategyDto signatureStrategyDto = new InvisibleSignatureStrategyDto(
            SignatureStrategyTypeDto.INVISIBLE_SIGNATURE, // Use the visual signature strategy
            "Simon Wächter", // Person that signed the document. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Barack Obama)
            "simon.waechter@secacon.com", // Contact for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. barack.obama@whitehouse.gov)
            "Secasign SDK Showcase", // Reason for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Aliens in Area 51)
            "Basel", // Location for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Moon)
            null, // Preservation size of the signature. Null because we use the default value of 30720 bytes
            null // Name of the signature field. Null because we choose it internally
        );

        // Sign the document
        CreateOrganizationPdfDocumentDto createOrganizationPdfDocumentDto = new CreateOrganizationPdfDocumentDto(encodedUnsignedPdfDocumentData, unsignedPdfDocument.getName(), protectedPdfSigning, signatureStrategyDto);
        CreateOrganizationPdfSigningDto createOrganizationPdfSigningDto = new CreateOrganizationPdfSigningDto(signingId, Collections.singletonList(createOrganizationPdfDocumentDto));
        List<ReadOrganizationDocumentDto> readOrganizationDocumentDtos = secasignHttpClient.signOrganizationPdfDocuments(tokenDto, createOrganizationPdfSigningDto);

        // Handle the result
        ReadOrganizationDocumentDto readOrganizationDocumentDto = readOrganizationDocumentDtos.get(0);
        if (readOrganizationDocumentDto.getSigningStatus() == DocumentSigningStatusDto.SIGNED) {
            // Write the signed file
            File signedPdfDocument = new File("../data/Signed_" + System.currentTimeMillis() + ".pdf");
            Files.write(signedPdfDocument.toPath(), Base64Utils.decodeString(readOrganizationDocumentDto.getEncodedSignedDocumentOrSignature()));
            System.out.println("The document was successfully signed. Storing it at " + signedPdfDocument.getAbsolutePath());
        } else if (readOrganizationDocumentDto.getSigningStatus() == DocumentSigningStatusDto.FAILED) {
            fail("Unable to sign the document. Error: " + readOrganizationDocumentDto.getErrorMessage());
        }
    }
}
