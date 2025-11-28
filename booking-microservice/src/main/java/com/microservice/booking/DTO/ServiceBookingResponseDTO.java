package com.microservice.booking.DTO;

import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.Utils.StatusBooking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ServiceBookingResponseDTO {
    public Long idBooking;
    public LocalDate startDate;
    public LocalDate endDate;
    public float totalPrice;
    public HostDTO hosts;
    public RoomDTO room;
    public StatusBooking status;
}
