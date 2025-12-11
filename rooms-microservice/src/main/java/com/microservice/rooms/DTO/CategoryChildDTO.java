package com.microservice.rooms.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
@Schema(description = "DTO to create a new category")
public record CategoryChildDTO(
    @NotEmpty
    String name,

    Long idRoom
) { }
