package com.sumit.docsensex.controller;

import com.sumit.docsensex.dto.request.CreateTenantRequest;
import com.sumit.docsensex.dto.response.CreateTenantResponse;
import com.sumit.docsensex.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
@Slf4j
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTenantResponse createTenant(@Valid @RequestBody CreateTenantRequest createTenantRequest) {
        return tenantService.createTenant(createTenantRequest);

    }

}
