package com.sumit.docsensex.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.docsensex.model.Tenant;
import com.sumit.docsensex.repository.TenantRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final TenantRepository tenantRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/v1/tenants") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-Key");
        if (apiKey == null || apiKey.isBlank()) {
            sendUnauthorized(response, "Missing X-API-Key header");
            return;
        }

        String hash = sha256(apiKey);
        Optional<Tenant> tenantOpt = tenantRepository.findByApiKeyHashAndIsActiveTrue(hash);
        if (tenantOpt.isEmpty()) {
            sendUnauthorized(response, "Invalid or inactive API key");
            return;
        }

        TenantContext.setTenantId(tenantOpt.get().getId().toString());
        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clearTenantId();
        }
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> body = Map.of(
                "error", "UNAUTHORIZED",
                "message", message,
                "status", 401,
                "timestamp", Instant.now().toString()
        );
        objectMapper.writeValue(response.getWriter(), body);
    }
}
