package com.microservice.host.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record HostInputDTO(
    @NotNull
    int document,
    @NotEmpty
    String name
) { }
