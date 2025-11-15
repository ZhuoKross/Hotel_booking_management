package com.microservice.rooms.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryDTO(

    Long id,
    @NotEmpty
    String name
) { }
