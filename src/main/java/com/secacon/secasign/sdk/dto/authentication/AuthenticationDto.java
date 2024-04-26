package com.secacon.secasign.sdk.dto.authentication;

public class AuthenticationDto {

    private String emailAddress;

    private String password;

    public AuthenticationDto() {
    }

    public AuthenticationDto(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
