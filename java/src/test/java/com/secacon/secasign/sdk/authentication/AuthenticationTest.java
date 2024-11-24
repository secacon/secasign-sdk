package com.secacon.secasign.sdk.authentication;

import com.secacon.secasign.extension.SdkConfiguration;
import com.secacon.secasign.extension.SdkTest;
import com.secacon.secasign.extension.SdkTestRequirements;
import com.secacon.secasign.sdk.SecasignHttpClient;
import com.secacon.secasign.sdk.dto.authentication.AuthenticationDto;
import com.secacon.secasign.sdk.dto.authentication.TokenDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test a simple login.
 *
 * @author Simon Wächter
 */
@SdkTest
public class AuthenticationTest {

    /**
     * Test the login.
     *
     * @param sdkConfiguration SDK configuration
     * @throws Exception Exception in case of a problem
     */
    @SdkTestRequirements(isLoginAvailable = true)
    public void testAuthentication(SdkConfiguration sdkConfiguration) throws Exception {
        // Create the HTTP client
        SecasignHttpClient secasignHttpClient = new SecasignHttpClient(sdkConfiguration.getUrl());

        // Obtain the token
        TokenDto tokenDto = secasignHttpClient.login(new AuthenticationDto(sdkConfiguration.getEmailAddress(), sdkConfiguration.getPassword()));
        assertNotNull(tokenDto.getToken());
    }
}
