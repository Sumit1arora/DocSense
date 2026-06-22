package com.sumit.docsensex.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTenantRequest {
    @NotBlank(message = "Name is Required.")
    private String name;
}
