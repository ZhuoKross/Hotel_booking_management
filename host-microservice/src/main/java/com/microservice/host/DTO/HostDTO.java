package com.microservice.host.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

@Builder
public record HostDTO(
        Long id,
        boolean isVipHost,
        boolean isRegularHost,
        @NotNull
        int document,
        int numVisits,
        @NotEmpty
        String name
) {}
