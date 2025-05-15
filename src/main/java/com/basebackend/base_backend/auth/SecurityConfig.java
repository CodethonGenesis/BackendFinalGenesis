package com.basebackend.base_backend.auth;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.basebackend.base_backend.auth.filter.JWTAuthenticationFilter;
import com.basebackend.base_backend.auth.filter.JWTValidationFilter;
import com.basebackend.base_backend.auth.filter.RateLimitFilter;
import com.basebackend.base_backend.auth.filter.XSSFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 /*        @Value("${app.cors.allowed-origins:http://localhost:4200}")
        private String[] allowedOrigins; */

        private final AuthenticationConfiguration authenticationConfiguration;
        private final TokenJWTConfig tokenConfig;

        public SecurityConfig(
                        AuthenticationConfiguration authenticationConfiguration,
                        TokenJWTConfig tokenConfig) {
                this.authenticationConfiguration = authenticationConfiguration;
                this.tokenConfig = tokenConfig;
        }

        @Bean
        public AuthenticationManager authenticationManager() throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/api/users/usersByName/{name}").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/page/{page},{pageSize}", "/api/users/fixed", "/api/users/{userId}/categorias", "/api/users/{userId}/logros",  "/api/users/email-exists/{email}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/{id}")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // verificationcode
                .requestMatchers(HttpMethod.GET, "/api/verificationcodes", "/api/verificationcodes/page/{page}", "/api/verificationcodes/email/{email}", "/api/verificationcodes/id/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/verificationcodes").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/verificationcodes/{id}").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/send-code", "/verify-code", "/reset-password-with-code").permitAll()
                // evento
                .requestMatchers(HttpMethod.GET, "/api/eventos", "/api/eventos/id/{id}", "/api/eventos/page/{page},{pageSize}", "/api/eventos/name/{name}", "/api/eventos/estado/{estadoId}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/eventos").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/eventos/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/eventos/{id}").permitAll()
                // estado
                .requestMatchers(HttpMethod.GET, "/api/estados", "/api/estados/page/{page},{pageSize}", "/api/estados/page{page}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/estados").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/estados/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/estados/{id}").permitAll()
                // preferencias
                .requestMatchers(HttpMethod.GET, "/api/preferencias", "/api/preferencias/id/{id}", "/api/preferencias/page/{page},{pageSize}", "/api/preferencias/tema/{tema}", "/api/preferencias/daltonico/{daltonico}", "/api/preferencias/discapacidadVisual/{discapacidadVisual}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/preferencias").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/preferencias/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/preferencias/{id}").permitAll()
                // comentario
                .requestMatchers(HttpMethod.GET, "/api/comentarios", "/api/comentarios/id/{id}", "/api/comentarios/page/{page},{pageSize}", "/api/comentarios/autor/{idAutor}", "/api/comentarios/ordenar/{sort}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/comentarios").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/comentarios/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/comentarios/{id}").permitAll()
                // feed
                .requestMatchers(HttpMethod.GET, "/api/feed", "/api/feed/id/{id}", "/api/feed/page/{page},{pageSize}", "/api/feed/evento/{IdEvento}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/feed").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/feed/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/feed/{id}", "/addComentario/{id}").permitAll()
                // logro
                .requestMatchers(HttpMethod.GET, "/api/logros", "/api/logros/page/{page},{pageSize}", "/api/logros/id/{id}", "/api/logros/name/{name}", "/api/logros/descripcion/{descripcion}", "/api/logros/usuario/{idUsuario}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/logros").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/logros/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/logros/{id}").permitAll()
                // categoria
                .requestMatchers(HttpMethod.GET, "/api/categorias", "/api/categorias/page/{page},{pageSize}", "/api/categoriasid/{id}", "/api/categorias/name/{name}", "/api/categorias/descripcion/{descripcion}", "/api/categorias/nameContains/{name}" ,"api/categorias/eventosPorCategoria/{idCategoria}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categorias").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/categorias/{id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/categorias/{id}").permitAll()
                .anyRequest().permitAll())
            .cors(cors -> cors.configurationSource(configurationSource()))
            .addFilterBefore(new XSSFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new RateLimitFilter(), JWTAuthenticationFilter.class)
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), tokenConfig))
            .addFilter(new JWTValidationFilter(authenticationManager(), tokenConfig))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(management -> 
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers
                .frameOptions(frame -> frame.deny())
                .xssProtection(xss -> xss.disable()) // Using custom XSSFilter instead
                .contentSecurityPolicy(csp -> 
                    csp.policyDirectives(
                        "default-src 'self'; " +
                        "script-src 'self'; " +
                        "style-src 'self'; " +
                        "img-src 'self' data:; " +
                        "font-src 'self'; " +
                        "frame-ancestors 'none'; " +
                        "form-action 'self';"
                    ))
                .permissionsPolicy(permissions -> 
                    permissions.policy("camera=(), microphone=(), geolocation=()")))
            .build();
    }

        @Bean
        public CorsConfigurationSource configurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOriginPatterns(Arrays.asList(
                    "http://localhost:4200",
                    "http://192.168.1.253:*",
                    "https://codebackgenesis.csanchezm.es:*",
                    "https://codegenesis.csanchezm.es",
                    "https://codegenesis.csanchezm.es:*",
                    "https://codebackgenesis.csanchezm.es"
                ));
                config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList(
                                "Authorization",
                                "Content-Type",
                                "X-Requested-With",
                                "Accept",
                                "Origin",
                                "Access-Control-Request-Method",
                                "Access-Control-Request-Headers"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}
