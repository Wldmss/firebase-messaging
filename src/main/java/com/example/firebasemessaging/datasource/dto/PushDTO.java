package com.example.firebasemessaging.datasource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PushDTO {
    private String deviceToken;

    @Builder
    public PushDTO(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
