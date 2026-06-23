package com.sumit.docsensex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadResponse {
    private String documentId;
    private String fileName;
    private String status;
    private String message;
}
