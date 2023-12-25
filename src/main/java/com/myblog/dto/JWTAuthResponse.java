package com.myblog.dto;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String accessToken;
    private String tokentype = "Bearer";

    public JWTAuthResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
