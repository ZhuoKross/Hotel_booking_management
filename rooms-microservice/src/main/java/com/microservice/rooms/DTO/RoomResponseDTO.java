package com.microservice.rooms.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "DTO response when a room is created or updated.")
public record RoomResponseDTO(
        @Schema(description = "The id of the room")
        Long id,
        @Schema(description = "Number of beds that has the room.")
        int numBeds,
        @Schema(description = "Defines whether a room has wifi or not")
        boolean hasWifi,
        @Schema(description = "Defines whether a room has TV")
        boolean hasTv,
        @Schema(description = "The price of the room.", type = "number")
        float price,
        @Schema(description = "The number of persons that the room can hold.")
        int personsCapacity,
        @Schema(description = "Defines whether the room is currently occupied or not")
        boolean isOccupied,
        @Schema(description = "The list of categories associated with the room", anyOf = {CategoryDTO.class})
        List<CategoryDTO> categories
) {}
