package com.microservice.booking.Client.model;

import lombok.Builder;

@Builder
public record HostDTO(
        boolean isVipHost,
        boolean isRegularHost,
        int document,
        String name) {
}
