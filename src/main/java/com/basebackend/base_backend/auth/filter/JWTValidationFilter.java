package com.basebackend.base_backend.auth.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.basebackend.base_backend.auth.SimpleGrantedAuthorityDeserializer;
import com.basebackend.base_backend.auth.TokenJWTConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    private final TokenJWTConfig tokenConfig;
    private final ObjectMapper objectMapper;

    public JWTValidationFilter(AuthenticationManager authenticationManager, TokenJWTConfig tokenConfig) {
        super(authenticationManager);
        this.tokenConfig = tokenConfig;
        
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer());
        this.objectMapper.registerModule(module);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(tokenConfig.getHeaderAuthorization());

        if (header == null || !header.startsWith(tokenConfig.getPrefixToken())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(tokenConfig.getPrefixToken(), "");

        try {
            Claims claims = Jwts.parser()
                .verifyWith(tokenConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
                
            String username = claims.getSubject();
            Object authoritiesClaims = claims.get("authorities");
            
            Collection<? extends GrantedAuthority> roles;
            try {
                // Try to deserialize using our custom deserializer
                roles = Arrays.asList(
                    objectMapper.readValue(
                        authoritiesClaims.toString().getBytes(), 
                        SimpleGrantedAuthority[].class
                    )
                );
            } catch (Exception e) {
                // Fallback: manually parse the authorities if the JSON format is non-standard
                String authString = authoritiesClaims.toString();
                if (authString.startsWith("[") && authString.endsWith("]")) {
                    // Remove the outer brackets
                    authString = authString.substring(1, authString.length() - 1);
                    
                    // Split by comma if there are multiple authorities
                    String[] authParts = authString.split(",");
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    
                    for (String part : authParts) {
                        part = part.trim();
                        if (part.contains("authority=")) {
                            String role = part.substring(part.indexOf("=") + 1).trim();
                            // Remove any trailing braces
                            if (role.endsWith("}")) {
                                role = role.substring(0, role.length() - 1).trim();
                            }
                            authorities.add(new SimpleGrantedAuthority(role));
                        }
                    }
                    
                    roles = authorities;
                } else {
                    // If we can't parse it, just create a default role
                    roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                }
            }
            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, 
                null, 
                roles
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "Invalid token");
            
            response.setStatus(401);
            response.setContentType(tokenConfig.getContentType());
            response.getWriter().write(objectMapper.writeValueAsString(body));
        }
    }
}
