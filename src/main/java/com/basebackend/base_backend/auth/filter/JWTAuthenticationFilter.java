package com.basebackend.base_backend.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.basebackend.base_backend.auth.TokenJWTConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenJWTConfig tokenConfig;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, TokenJWTConfig tokenConfig) {
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        String username;
        String password;

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            username = loginRequest.username;
            password = loginRequest.password;
        } catch (IOException e) {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }

        if (username == null || password == null) {
            throw new AuthenticationException("Username and password are required") {};
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal())
                .getUsername();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        String token = Jwts.builder()
                .subject(username)
                .claim("authorities", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenConfig.getExpiration()))
                .signWith(tokenConfig.getSecretKey())
                .compact();

        response.addHeader(tokenConfig.getHeaderAuthorization(), tokenConfig.getPrefixToken() + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hello %s, you have successfully logged in!", username));

        response.getWriter().write(objectMapper.writeValueAsString(body));
        response.setContentType(tokenConfig.getContentType());
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Authentication error: " + failed.getMessage());
        body.put("error", failed.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(tokenConfig.getContentType());
    }

    private static class LoginRequest {
        @JsonProperty("username")
        private String username;
        
        @JsonProperty("password")
        private String password;
    }
}
