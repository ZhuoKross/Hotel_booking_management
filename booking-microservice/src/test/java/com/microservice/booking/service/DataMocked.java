package com.microservice.booking.service;

import com.microservice.booking.Client.RoomClient;
import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.DTO.ResponseBookingDTO;
import com.microservice.booking.Entity.Booking;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DataMocked {

    public static List<Booking> getAllBookingsMock (){
        Booking data01 = Booking.builder()
                .startDate(LocalDate.of(2025, Month.FEBRUARY, 12))
                .endDate(LocalDate.of(2025, Month.FEBRUARY, 15))
                .idHost(3L)
                .idRoom(4L)
                .build();

        Booking data02 = Booking.builder()
                .startDate(LocalDate.of(2025, Month.APRIL, 12))
                .endDate(LocalDate.of(2025, Month.APRIL, 15))
                .idHost(1L)
                .idRoom(2L)
                .build();


        return List.of(data01, data02);
    }

    public static HostDTO getHostClientMock(){
        return HostDTO.builder()
                .isVipHost(false)
                .isRegularHost(true)
                .price(20.00)
                .document(456)
                .name("Santiago vega")
                .build();
    }

    public static RoomDTO getRoomClientMock(){
        return RoomDTO.builder()
                .hasWifi(true)
                .hasTv(true)
                .numBeds(1)
                .personsCapacity(2)
                .build();
    }
}
