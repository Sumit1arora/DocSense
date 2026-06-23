package com.sumit.docsensex.controller;

import com.sumit.docsensex.dto.response.UploadResponse;
import com.sumit.docsensex.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload")
    public UploadResponse upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "webhookUrl",  required = false) String webhookUrl
    ) {
        return  documentService.uploadDocument(file, webhookUrl);
    }

}
