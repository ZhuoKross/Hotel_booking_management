package com.microservice.rooms.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomResponseDTO(
        Long id,
        int numBeds,
        boolean hasWifi,
        boolean hasTv,
        float price,
        int personsCapacity,
        boolean isOccupied,
        List<CategoryDTO> categories
) {}
