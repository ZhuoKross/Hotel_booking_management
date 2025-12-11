package com.microservice.rooms.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Specific response DTO created for validate if a room is occupied.")
public record isRoomOccupiedValidationDTO(
    @Schema(description = "Defines if the room is currently occupied or not")
    boolean isRoomOccupied
) {}
