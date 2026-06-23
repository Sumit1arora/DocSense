package com.sumit.docsensex.repository;

import com.sumit.docsensex.model.Documents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DocumentRepository extends MongoRepository<Documents, String> {
    Optional<Documents> findByIdAndTenantId(String id, String tenantId);

    Page<Documents> findByTenantId(String tenantId, Pageable pageable);

    Page<Documents> findByTenantIdAndStatus(String tenantId, String status, Pageable pageable);

    void deleteByIdAndTenantId(String id, String tenantId);
}
