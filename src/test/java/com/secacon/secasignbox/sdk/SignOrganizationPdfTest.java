package com.secacon.secasignbox.sdk;

import com.secacon.secasignbox.sdk.client.SecasignBoxHttpClient;
import com.secacon.secasignbox.sdk.dto.*;
import com.secacon.secasignbox.sdk.utils.Base64Utils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to sign PDF documents via organization signing with an invisible and visible signature.
 *
 * @author Simon Wächter
 */
public class SignOrganizationPdfTest {

    /**
     * Sign a PDF document via organization signing and add an invisible signature on page 1.
     *
     * @throws Exception Exception
     */
    @Test
    public void testSignOrganizationPdfDocumentViaInvisibleSealsignSignature() throws Exception {
        // Create HTTP client
        SecasignBoxHttpClient secasignBoxHttpClient = new SecasignBoxHttpClient(Values.URL);

        // Obtain the token
        TokenDto tokenDto = secasignBoxHttpClient.login(new AuthenticationDto(Values.USERNAME, Values.PASSWORD));

        // Define the signing it that represents the organization certificate in the Secasign-Box (The link to the real certificate in the Swisscom-AIS HSM)
        UUID signingId = Values.SIGNING_ID;

        // Read the unsigned PDF document
        File unsignedPdfDocument = new File("data/Unsigned.pdf");
        assertTrue(unsignedPdfDocument.isFile());
        byte[] unsignedPdfDocumentData = Files.readAllBytes(unsignedPdfDocument.toPath());

        // Flag whether we rip out the signature/PDF encryption in case a PDF is "encrypted" (Encrypted means you are not allowed to change a signature/add a new signature - it is not really encrypted).
        // This is like a gentleman's agreement: You can follow it and are unable to sign the PDF document or just rip it out (Protection/existing signatures) and sign the document
        // Set this value to null or false if you don't know what this means
        Boolean protectedPdfSigning = null; // or false

        // Create the invisible Sealsign signature
        SignatureStrategyDto signatureStrategyDto = new SealsignSignatureStrategyDto(
            SignatureStrategyTypeDto.SEALSIGN, // Use the Sealsign signing strategy
            null, // Set visible signature graphic to null because we don't want a visible signature
            null, // Set visible signature description to null because we don't want a visible signature
            null, // Set appearance to null. Internally NOT_CERTIFIED will be used which prevents any modification (even form filling/annotations)
            null, // Set rendering to null because we don't want a visible signature
            "Simon Wächter", // Person that signed the document. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Barack Obama)
            "Secasign-Box SDK Showcase", // Reason for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Aliens in Area 51)
            "Basel", // Location for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Moon)
            "simon.waechter@secacon.com", // Contact for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. barack.obama@whitehouse.gov)
            null, // Preservation size of the signature. Null because we use the default value of 30720 bytes
            null, // Name of the signature field. Null because we choose it internally
            null, // Page number of the signature. Null because we don't want a visible signature. Internally a "visible" signature at page 1 with coordinates 0/0 and width/height 0/0 is used (Aka invisible)
            null, // Left page distance in millimeters of the signature. Null because we don't want a visible signature
            null, // Top page distance in millimeters of the signature. Null because we don't want a visible signature
            null, // With in millimeters of the signature. Null because we don't want a visible signature
            null, // Height in millimeters of the signature. Null because we don't want a visible signature
            null, // Font type of the signature. Null because we don't want a visible signature
            null, // Font size of the signature. Null because we don't want a visible signature
            null, // Red color part of the signature description. Null because we don't want a visible signature
            null, // Green color of the signature description. Null because we don't want a visible signature
            null // Blue color part of the signature description. Null because we don't want a visible signature
        );

        // Sign the document
        ReadOrganizationDocumentDto readOrganizationDocumentDto = secasignBoxHttpClient.signOrganizationPdfDocuments(tokenDto, signingId, unsignedPdfDocumentData, unsignedPdfDocument.getName(), protectedPdfSigning, signatureStrategyDto);

        // Handle the result
        if (readOrganizationDocumentDto.signingStatus() == DocumentSigningStatusDto.SIGNED) {
            File signedPdfDocument = new File("data/Signed_" + System.currentTimeMillis() + ".pdf");
            Files.write(signedPdfDocument.toPath(), Base64Utils.decodeString(readOrganizationDocumentDto.encodedSignedDocumentOrSignature()));
            System.out.println("The document was successfully signed. Storing it at " + signedPdfDocument.getAbsolutePath());
        } else if (readOrganizationDocumentDto.signingStatus() == DocumentSigningStatusDto.FAILED) {
            System.out.println("Unable to sign the document. Error: " + readOrganizationDocumentDto.errorMessage());
        }
    }

    /**
     * Sign a PDF document via organization signing and add a visible PNG signature + description on page 1.
     *
     * @throws Exception Exception
     */
    @Test
    public void testSignOrganizationPdfDocumentViaVisibleSealsignSignature() throws Exception {
        // Create HTTP client
        SecasignBoxHttpClient secasignBoxHttpClient = new SecasignBoxHttpClient(Values.URL);

        // Obtain the token
        TokenDto tokenDto = secasignBoxHttpClient.login(new AuthenticationDto(Values.USERNAME, Values.PASSWORD));

        // Define the signing it that represents the organization certificate in the Secasign-Box (The link to the real certificate in the Swisscom-AIS HSM)
        UUID signingId = Values.SIGNING_ID;

        // Read the unsigned PDF document
        File unsignedPdfDocument = new File("data/Unsigned.pdf");
        assertTrue(unsignedPdfDocument.isFile());
        byte[] unsignedPdfDocumentData = Files.readAllBytes(unsignedPdfDocument.toPath());

        // Read the PNG/JPG signature and Base64 encode it
        File pngSignature = new File("data/Signature.png");
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

        // Create the visible Sealsign signature
        SignatureStrategyDto signatureStrategyDto = new SealsignSignatureStrategyDto(
            SignatureStrategyTypeDto.SEALSIGN, // Use the Sealsign signing strategy
            encodedPngSignature, // Set visible signature graphic to null because we don't want a visible signature
            "Digitally signed by\nSimon Wächter\nDate: " + date + "\n" + time + " CET", // Set visible signature description. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Barack Obama)
            null, // Set appearance to null. Internally NOT_CERTIFIED will be used which prevents any modification (even form filling/annotations)
            SealsignSignatureRenderingDto.GRAPHIC_AND_DESCRIPTION, // Render the PNG signature and the signature description above. Several different renderings are possible. See the documentation
            "Simon Wächter", // Person that signed the document. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Barack Obama)
            "Secasign-Box SDK Showcase", // Reason for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Aliens in Area 51)
            "Basel", // Location for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. Moon)
            "simon.waechter@secacon.com", // Contact for the signature. Feel free to change this value to whatever value you like, but don't try to fool the viewer (E.g. barack.obama@whitehouse.gov)
            null, // Preservation size of the signature. Null because we use the default value of 30720 bytes
            null, // Name of the signature field. Null because we choose it internally
            1, // Page number of the signature, here first/only page
            50, // Left page distance in millimeters of the signature, here 50 mm
            50, // Top page distance in millimeters of the signature, here 50 mm
            75, // With in millimeters of the signature, here 75 mm
            25, // Height in millimeters of the signature, here 25 mm
            "Helvetica", // Font type of the signature
            12, // Font size of the signature, here 12
            0, // Use a black signature description font color
            0, // Use a black signature description font color
            0 // Use a black signature description font color
        );

        // Sign the document
        ReadOrganizationDocumentDto readOrganizationDocumentDto = secasignBoxHttpClient.signOrganizationPdfDocuments(tokenDto, signingId, unsignedPdfDocumentData, unsignedPdfDocument.getName(), protectedPdfSigning, signatureStrategyDto);

        // Handle the result
        if (readOrganizationDocumentDto.signingStatus() == DocumentSigningStatusDto.SIGNED) {
            File signedPdfDocument = new File("data/Signed_" + System.currentTimeMillis() + ".pdf");
            Files.write(signedPdfDocument.toPath(), Base64Utils.decodeString(readOrganizationDocumentDto.encodedSignedDocumentOrSignature()));
            System.out.println("The document was successfully signed. Storing it at " + signedPdfDocument.getAbsolutePath());
        } else if (readOrganizationDocumentDto.signingStatus() == DocumentSigningStatusDto.FAILED) {
            System.out.println("Unable to sign the document. Error: " + readOrganizationDocumentDto.errorMessage());
        }
    }
}
