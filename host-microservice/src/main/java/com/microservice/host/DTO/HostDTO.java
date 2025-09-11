package com.microservice.host.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

@Builder
public record HostDTO(
        Long id,
        @NotNull
        boolean isVipHost,
        @NotNull
        boolean isRegularHost,
        @NotNull
        int document,
        @NotEmpty
        String name
) {}
