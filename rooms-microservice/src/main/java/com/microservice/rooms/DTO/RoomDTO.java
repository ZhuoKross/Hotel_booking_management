package com.microservice.rooms.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Builder
@Schema(description = "DTO to create a new room.")
public record RoomDTO(
        @NotNull(message = "the num of beds can't be null")
        @Range(min = 1, max = 2, message = "the num of beds must be at least 1 and max 2")
        @Schema(description = "Number of beds that has the room.")
        int numBeds,
        @NotNull(message = "the hasWifi field must not be null nor empty")
        @Schema(description = "Defines whether a room has wifi or not")
        boolean hasWifi,
        @NotNull(message = "the hasTv field must not be null nor empty")
        @Schema(description = "Defines whether a room has TV")
        boolean hasTv,
        @Schema(description = "The price of the room.", type = "number")
        float price,
        @NotNull(message = "the persons capacity field must not be null")
        @Range(min = 1, max = 4, message = "the persons capacity must be at least 1 or max 4")
        @Schema(description = "The number of persons that the room can hold.")
        int personsCapacity,
        @Schema(description = "Defines whether the room is currently occupied or not")
        boolean isOccupied,
        @NotNull
        @Schema(description = "The list of categories associated with the room", anyOf = {CategoryDTO.class})
        List<CategoryDTO> categories
) {}
