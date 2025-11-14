package com.microservice.booking.Client.model;

import lombok.Builder;

@Builder
public record RoomDTO(Long id, int numBeds, boolean hasWifi, boolean hasTv, double price, int personsCapacity) {
}

