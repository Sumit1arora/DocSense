package com.sumit.docsensex.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "documents")
@Data
public class Documents {
    @Id
    private String id;

    @Indexed
    private String tenantId;

    private String fileName;
    private long fileSizeBytes;
    private String mimeType;

    @Indexed
    private String status; //PENDING | PROCESSING | READY | FAILED

    private int chunkCount;
    private String errorMessage;
    private String webhookUrl;
    private Boolean webhookDelivered;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
