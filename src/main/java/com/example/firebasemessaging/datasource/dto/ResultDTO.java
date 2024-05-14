package com.example.firebasemessaging.datasource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultDTO {
    private String token;
    private Boolean pwdErr; // 비번 틀림
    private Boolean noUser; // 계정 미존재
    private Boolean pwdExp; // 비번 만료

    @Builder
    public ResultDTO(String token, Boolean pwdErr, Boolean noUser, Boolean pwdExp) {
        this.token = token;
        this.pwdErr = pwdErr;
        this.noUser = noUser;
        this.pwdExp = pwdExp;
    }
}
