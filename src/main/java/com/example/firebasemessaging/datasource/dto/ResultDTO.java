package com.example.firebasemessaging.datasource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultDTO {
    private String token;

    @Builder
    public ResultDTO(String token) {
        this.token = token;
    }
}
