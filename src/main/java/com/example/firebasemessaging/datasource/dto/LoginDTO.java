package com.example.firebasemessaging.datasource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDTO {

    private String username;
    private String password;

    private String pwEndDt;

    private String deviceToken;
    private String osType;

    private String encryptUsername;
    private String encryptPassword;

    @Builder
    public LoginDTO(String username, String password, String pwEndDt, String deviceToken, String osType, String encryptUsername, String encryptPassword) {
        this.username = username;
        this.password = password;
        this.pwEndDt = pwEndDt;
        this.deviceToken = deviceToken;
        this.osType = osType;
        this.encryptUsername = encryptUsername;
        this.encryptPassword = encryptPassword;
    }
}
