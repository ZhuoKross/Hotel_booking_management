package com.microservice.booking.Client.model;

import lombok.Builder;

@Builder
public record CategoryChildDTO(
        Long id,
        String name,
        Long idRoom
) {}
