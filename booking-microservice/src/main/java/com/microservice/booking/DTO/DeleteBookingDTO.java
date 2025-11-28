package com.microservice.booking.DTO;

import jakarta.validation.constraints.NotNull;

public record DeleteBookingDTO(
        @NotNull
        Long idBooking,
        @NotNull
        Long idRoom
) {}
