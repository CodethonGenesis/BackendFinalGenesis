package com.basebackend.base_backend.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SimpleGrantedAuthorityJsonCreator {
    @JsonCreator
    public static SimpleGrantedAuthority create(@JsonProperty("authority") String role) {
        return new SimpleGrantedAuthority(role);
    }
}
