package com.sumit.docsensex.service;

import com.sumit.docsensex.dto.response.UploadResponse;
import com.sumit.docsensex.model.Documents;
import com.sumit.docsensex.repository.DocumentRepository;
import com.sumit.docsensex.security.TenantContext;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

    public UploadResponse uploadDocument(MultipartFile file , String webhookUrl) {
        validateFile(file);

        Documents doc = new Documents();
        doc.setTenantId(TenantContext.getTenantId());
        doc.setFileName(file.getOriginalFilename());
        doc.setFileSizeBytes(file.getSize());
        doc.setMimeType(file.getContentType());
        doc.setStatus("PENDING");
        doc.setWebhookUrl(webhookUrl);

        documentRepository.save(doc);

        return new UploadResponse(
                doc.getId(),
                doc.getFileName(),
                doc.getStatus(),
                "Document accepted for processing."
        );
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }
        String contentType = file.getContentType();
        if (!"application/pdf".equals(contentType)) {
            throw new IllegalArgumentException("Only PDF files are supported");
        }
        if (file.getSize()> 20L * 1024 * 1024) {
            throw new IllegalArgumentException("File is exceeds 20 MB limit");
        }
    }
}
