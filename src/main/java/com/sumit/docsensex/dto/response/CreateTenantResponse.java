package com.sumit.docsensex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor

public class CreateTenantResponse {
    private UUID tenantId;
    private String name;
    private String apiKey;
    private String note;
}
