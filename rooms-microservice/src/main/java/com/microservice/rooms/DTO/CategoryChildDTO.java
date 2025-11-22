package com.microservice.rooms.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CategoryChildDTO(
    @NotEmpty
    String name,
    Long idRoom
) { }
