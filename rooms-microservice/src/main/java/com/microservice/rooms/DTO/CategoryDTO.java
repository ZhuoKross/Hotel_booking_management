package com.microservice.rooms.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO response of a category")
public record CategoryDTO(
        @Schema(description = "The id of the category")
        Long id,
        @Schema(description = "The name of the category")
        String name,
        @Schema(description = "The id of the room associated")
        Long idRoom
) {}
