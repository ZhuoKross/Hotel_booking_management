package com.microservice.booking.Client.model;

import lombok.Builder;

@Builder
public record HostDTO(
        Long id,
        boolean isVipHost,
        boolean isRegularHost,
        int document,
        int numVisits,
        String name
) {}
