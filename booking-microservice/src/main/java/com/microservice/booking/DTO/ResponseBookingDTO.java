package com.microservice.booking.DTO;

import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.Utils.StatusBooking;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ResponseBookingDTO(
        Long idBooking,
        LocalDate startDate,
        LocalDate endDate,
        float totalPrice,
        HostDTO hosts,
        RoomDTO room,
        StatusBooking status
) {}
