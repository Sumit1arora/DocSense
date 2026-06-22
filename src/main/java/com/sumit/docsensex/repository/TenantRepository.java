package com.sumit.docsensex.repository;

import com.sumit.docsensex.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    Optional<Tenant> findByApiKeyHashAndIsActiveTrue(String apiKeyHash);
}
