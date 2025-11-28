package com.microservice.host.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record HostResponseDTO(
        Long id,
        boolean isVipHost,
        boolean isRegularHost,
        @NotNull
        int document,
        int numVisits,
        @NotEmpty
        String name
) {}
