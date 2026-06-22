package com.sumit.docsensex.service;

import com.sumit.docsensex.dto.request.CreateTenantRequest;
import com.sumit.docsensex.dto.response.CreateTenantResponse;
import com.sumit.docsensex.model.Tenant;
import com.sumit.docsensex.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    public CreateTenantResponse createTenant(CreateTenantRequest createTenantRequest) {
        String rawKey = generateApiKey();
        String hash   = sha256(rawKey);
        String prefix = rawKey.substring(0, 9);
        Tenant tenant = new Tenant();
        tenant.setName(createTenantRequest.getName());
        tenant.setApiKeyHash(hash);
        tenant.setApiKeyPrefix(prefix);
        tenantRepository.save(tenant);

        return new CreateTenantResponse(
                tenant.getId(),
                tenant.getName(),
                rawKey,
                "Store this key securely. It will not be shown again."
        );
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

    private String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[12];
        random.nextBytes(keyBytes);
        String randomKey = Base64.getEncoder().withoutPadding().encodeToString(keyBytes).substring(0, 16);
        return "ds_"+ randomKey;
    }

}
