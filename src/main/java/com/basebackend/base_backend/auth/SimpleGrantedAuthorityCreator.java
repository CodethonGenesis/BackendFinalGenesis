package com.basebackend.base_backend.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Helper class for deserializing SimpleGrantedAuthority objects from JSON.
 * This class provides a static factory method that can be used by Jackson
 * to create SimpleGrantedAuthority instances during JSON deserialization.
 */
public class SimpleGrantedAuthorityCreator {
    /**
     * Creates a new SimpleGrantedAuthority from a role string.
     * 
     * @param role The authority string (e.g., "ROLE_USER")
     * @return A new SimpleGrantedAuthority instance
     */
    @JsonCreator
    public static SimpleGrantedAuthority create(@JsonProperty("authority") String role) {
        return new SimpleGrantedAuthority(role);
    }
}
