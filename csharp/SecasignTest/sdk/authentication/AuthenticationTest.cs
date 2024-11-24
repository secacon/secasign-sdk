using Secasign;
using Secasign.dto.authentication;
using SecasignTest.extension;

namespace SecasignTest.sdk.authentication;

public class AuthenticationTest
{
    private static SdkConfiguration SdkConfiguration => SdkExtension.SdkConfiguration;

    [SdkExtension(SdkTestRequirements.IsLoginAvailable)]
    public async Task TestAuthentication()
    {
        // Create the HTTP client
        var secasignHttpClient = new SecasignHttpClient(SdkConfiguration.Url!);

        // Login and obtain a JWT token
        var authenticationDto = new AuthenticationDto(SdkConfiguration.EmailAddress!, SdkConfiguration.Password!);
        var tokenDto = await secasignHttpClient.Login(authenticationDto);
        Assert.NotNull(tokenDto.token);
    }
}
