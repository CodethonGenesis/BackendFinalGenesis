package com.basebackend.base_backend.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitFilter extends OncePerRequestFilter {
    
    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private static final long ONE_MINUTE = 60 * 1000L;
    
    private final Map<String, RequestCount> requestCounts = new ConcurrentHashMap<>();
    
    private static class RequestCount {
        final AtomicInteger count;
        final long timestamp;
        
        RequestCount() {
            this.count = new AtomicInteger(1);
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) 
            throws ServletException, IOException {
        
        String clientIp = getClientIP(request);
        
        if (isRateLimitExceeded(clientIp)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Please try again later.");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isRateLimitExceeded(String clientIp) {
        long currentTime = System.currentTimeMillis();
        
        requestCounts.compute(clientIp, (key, oldValue) -> {
            if (oldValue == null || currentTime - oldValue.timestamp > ONE_MINUTE) {
                return new RequestCount();
            }
            oldValue.count.incrementAndGet();
            return oldValue;
        });
        
        RequestCount currentCount = requestCounts.get(clientIp);
        return currentCount.count.get() > MAX_REQUESTS_PER_MINUTE;
    }
    
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
} 