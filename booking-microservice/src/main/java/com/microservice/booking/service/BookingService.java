package com.microservice.booking.service;


import com.microservice.booking.Client.HostClient;
import com.microservice.booking.Client.RoomClient;
import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.ResponseHostObj;
import com.microservice.booking.Client.model.ResponseRoomObj;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.DTO.BookingDTO;
import com.microservice.booking.DTO.ResponseBookingDTO;
import com.microservice.booking.Entity.Booking;
import com.microservice.booking.exceptions.BookingNotFoundException;
import com.microservice.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
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
        if(bookingListEntity.isEmpty()){
            throw new BookingNotFoundException();
        }
        List<ResponseBookingDTO> responseBookingDTOList = bookingListEntity.stream()
                .map((bookingEntity) -> {
                    ResponseRoomObj<RoomDTO> roomDTOFound = roomClient.getRoomClient(bookingEntity.idRoom);
                    ResponseHostObj<HostDTO> hostDTOFound = hostClient.getHost(bookingEntity.idHost);

                    RoomDTO roomData = roomDTOFound.data();
                    HostDTO hostData = hostDTOFound.data();

                    return ResponseBookingDTO.builder()
                            .idBooking(bookingEntity.idBooking)
                            .startDate(bookingEntity.startDate)
                            .endDate(bookingEntity.endDate)
                            .rooms(roomData)
                            .hosts(hostData)
                            .build();
                })
                .toList();

        return responseBookingDTOList;
    }


    public ResponseBookingDTO getBooking(Long idBooking) {
        if(idBooking == null){
            throw new IllegalArgumentException();
        }
        Booking bookingFound = bookingRepository.findById(idBooking).orElseThrow(BookingNotFoundException::new);
        ResponseHostObj<HostDTO> hostDTOFound = hostClient.getHost(bookingFound.idHost);
        ResponseRoomObj<RoomDTO> roomDTOFound = roomClient.getRoomClient(bookingFound.idRoom);
        RoomDTO roomData = roomDTOFound.data();
        HostDTO hostData = hostDTOFound.data();

        ResponseBookingDTO bookingDTO = ResponseBookingDTO.builder()
                .idBooking(bookingFound.idBooking)
                .startDate(bookingFound.startDate)
                .endDate(bookingFound.endDate)
                .rooms(roomData)
                .hosts(hostData)
                .build();

        return bookingDTO;
    }


    public ResponseBookingDTO createBooking (BookingDTO bookingDTO){
        ResponseHostObj<HostDTO> hostDTO = hostClient.getHost(bookingDTO.idHost());
        ResponseRoomObj<RoomDTO> roomDTO = roomClient.getRoomClient(bookingDTO.idRoom());
        HostDTO hostData = hostDTO.data();
        RoomDTO roomData = roomDTO.data();

        System.out.println("data of HostDTO:" +  hostData);
        System.out.println("data of RoomDTODTO:" +  hostData);
        Booking bookingEntity = Booking.builder()
                .startDate(bookingDTO.startDate())
                .endDate(bookingDTO.endDate())
                .idHost(bookingDTO.idHost())
                .idRoom(bookingDTO.idRoom())
                .build();

        Booking bookingCreated = bookingRepository.save(bookingEntity);

        return ResponseBookingDTO.builder()
                .idBooking(bookingCreated.idBooking)
                .startDate(bookingCreated.startDate)
                .endDate(bookingCreated.endDate)
                .rooms(roomData)
                .hosts(hostData)
                .build();
    }
}
