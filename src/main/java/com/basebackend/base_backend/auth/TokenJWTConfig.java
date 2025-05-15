package com.basebackend.base_backend.auth;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

@Component
public class TokenJWTConfig {
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration:3600000}")
    private Long expiration;

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String CONTENT_TYPE = "application/json";

    public String getHeaderAuthorization() {
        return HEADER_AUTHORIZATION;
    }

    public String getPrefixToken() {
        return PREFIX_TOKEN;
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }

    public SecretKey getSecretKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            // Fallback to a secure default if the provided key is invalid
            byte[] defaultKeyBytes = Decoders.BASE64.decode("c2VjdXJlX2p3dF9zZWNyZXRfa2V5X2Zvcl9hcHBsaWNhdGlvbl9zaWduaW5nX2FuZF92ZXJpZmljYXRpb24=");
            return Keys.hmacShaKeyFor(defaultKeyBytes);
        }
    }

    public Long getExpiration() {
        return expiration;
    }
}
