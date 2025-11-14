package com.microservice.booking.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookingDTO(
        @NotNull
        @FutureOrPresent(message = "The start date of the booking has to be in the present or future.")
        LocalDate startDate,
        @NotNull
        @Future(message = "The end date has to be in the future.")
        LocalDate endDate,
        @NotNull
        Long idHost,
        @NotNull
        Long idRoom
) {}
