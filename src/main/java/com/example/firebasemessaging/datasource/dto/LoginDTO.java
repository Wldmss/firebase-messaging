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

    private String encryptUsername;
    private String encryptPassword;

    @Builder
    public LoginDTO(String username, String password, String pwEndDt, String encryptUsername, String encryptPassword) {
        this.username = username;
        this.password = password;
        this.pwEndDt = pwEndDt;
        this.encryptUsername = encryptUsername;
        this.encryptPassword = encryptPassword;
    }
}
