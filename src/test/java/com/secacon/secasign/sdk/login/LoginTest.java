package com.secacon.secasign.sdk.login;

import com.secacon.secasign.extension.SdkTest;
import com.secacon.secasign.extension.SdkTestRequirements;
import com.secacon.secasign.sdk.Values;
import com.secacon.secasign.sdk.client.SecasignHttpClient;
import com.secacon.secasign.sdk.dto.authentication.AuthenticationDto;
import com.secacon.secasign.sdk.dto.authentication.TokenDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test a simple login.
 *
 * @author Simon Wächter
 */
@SdkTest
public class LoginTest {

    /**
     * Test the login.
     *
     * @throws Exception Exception
     */
    @SdkTestRequirements(isLoginAvailable = true)
    public void testLogin() throws Exception {
        // Create HTTP client
        SecasignHttpClient secasignHttpClient = new SecasignHttpClient(Values.URL);

        // Obtain the token
        TokenDto tokenDto = secasignHttpClient.login(new AuthenticationDto(Values.USERNAME, Values.PASSWORD));
        assertNotNull(tokenDto.getToken());
    }
}
