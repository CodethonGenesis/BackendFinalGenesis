package com.basebackend.base_backend.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Перехват 403
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDenied(AccessDeniedException ex) {
        logger.error("403 FORBIDDEN: {}", ex.getMessage());
        return Map.of("error", "Access denied", "details", ex.getMessage());
    }

    // Перехват всех остальных ошибок
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGenericException(Exception ex) {
        logger.error("500 ERROR: {}", ex.getMessage(), ex);
        return Map.of("error", "Internal server error", "details", ex.getMessage());
    }
}
