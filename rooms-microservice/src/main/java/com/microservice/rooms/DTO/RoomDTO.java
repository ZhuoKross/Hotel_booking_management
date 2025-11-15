package com.microservice.rooms.DTO;

import com.microservice.rooms.Entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

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
        @NotNull(message = "the price field must not be null nor empty")
        double price,
        @NotNull(message = "the persons capacity field must not be null")
        @Range(min = 1, max = 4, message = "the persons capacity must be at least 1 or max 4")
        int personsCapacity,
        @NotNull
        CategoryDTO category
) {}
