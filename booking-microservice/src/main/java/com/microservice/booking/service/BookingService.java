package com.microservice.booking.service;


import com.microservice.booking.Client.HostClient;
import com.microservice.booking.Client.RoomClient;
import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.DTO.BookingDTO;
import com.microservice.booking.DTO.ResponseBookingDTO;
import com.microservice.booking.Entity.Booking;
import com.microservice.booking.Utils.Response;
import com.microservice.booking.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private HostClient hostClient;
    private RoomClient roomClient;

    public BookingService(BookingRepository bookingRepository, HostClient hostClient, RoomClient roomClient) {
        this.bookingRepository = bookingRepository;
        this.hostClient = hostClient;
        this.roomClient = roomClient;
    }


    public List<ResponseBookingDTO> getAllBookings() {
        List<Booking> bookingListEntity = bookingRepository.findAll();
        List<ResponseBookingDTO> responseBookingDTOList = bookingListEntity.stream()
                .map((bookingEntity) -> {
                    RoomDTO roomDTOFound = roomClient.getRoomClient(bookingEntity.idRoom);
                    HostDTO hostDTOFound = hostClient.getHost(bookingEntity.idHost);
                    return ResponseBookingDTO.builder()
                            .startDate(bookingEntity.startDate)
                            .endDate(bookingEntity.endDate)
                            .rooms(roomDTOFound)
                            .hosts(hostDTOFound)
                            .build();
                })
                .toList();

        return responseBookingDTOList;
    }


    public ResponseBookingDTO getBooking(Long idBooking) {
        Booking bookingFound = bookingRepository.findById(idBooking).orElse(null);
        HostDTO hostDTOFound = hostClient.getHost(bookingFound.idHost);
        RoomDTO roomDTOFound = roomClient.getRoomClient(bookingFound.idRoom);

        ResponseBookingDTO bookingDTO = ResponseBookingDTO.builder()
                .startDate(bookingFound.startDate)
                .endDate(bookingFound.endDate)
                .rooms(roomDTOFound)
                .hosts(hostDTOFound)
                .build();

        return bookingDTO;
    }


    public ResponseBookingDTO createBooking (BookingDTO bookingDTO){
        HostDTO hostDTO = hostClient.getHost(bookingDTO.idHost());
        RoomDTO roomDTO = roomClient.getRoomClient(bookingDTO.idRoom());
        Booking bookingEntity = Booking.builder()
                .startDate(bookingDTO.startDate())
                .endDate(bookingDTO.endDate())
                .idHost(bookingDTO.idHost())
                .idRoom(bookingDTO.idRoom())
                .build();

        Booking bookingCreated = bookingRepository.save(bookingEntity);

        return ResponseBookingDTO.builder()
                .startDate(bookingCreated.startDate)
                .endDate(bookingCreated.endDate)
                .rooms(roomDTO)
                .hosts(hostDTO)
                .build();
    }
}
