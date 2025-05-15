package com.basebackend.base_backend.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SimpleGrantedAuthorityDeserializer extends JsonDeserializer<SimpleGrantedAuthority> {
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        // Handle both formats: with quotes and without quotes
        if (node.has("authority")) {
            return new SimpleGrantedAuthority(node.get("authority").asText());
        } else {
            // Try to parse from string representation like "authority=ROLE_USER"
            String text = node.toString();
            if (text.contains("=")) {
                String[] parts = text.replace("{", "").replace("}", "").split("=");
                if (parts.length == 2) {
                    return new SimpleGrantedAuthority(parts[1].trim());
                }
            }
            // Fallback to the entire node text
            return new SimpleGrantedAuthority(text);
        }
    }
}