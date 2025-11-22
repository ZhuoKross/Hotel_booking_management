package com.microservice.rooms.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Builder
public record RoomDTO(
        Long id,
        @NotNull(message = "the num of beds can't be null")
        @Range(min = 1, max = 2, message = "the num of beds must be at least 1 and max 2")
        int numBeds,
        @NotNull(message = "the hasWifi field must not be null nor empty")
        boolean hasWifi,
        @NotNull(message = "the hasTv field must not be null nor empty")
        boolean hasTv,
        float price,
        @NotNull(message = "the persons capacity field must not be null")
        @Range(min = 1, max = 4, message = "the persons capacity must be at least 1 or max 4")
        int personsCapacity,
        boolean isOccupied,
        @NotNull
        List<CategoryDTO> categories
) {}
