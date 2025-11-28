package com.microservice.booking.service;


import com.microservice.booking.Client.HostClient;
import com.microservice.booking.Client.RoomClient;
import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.ResponseHostObj;
import com.microservice.booking.Client.model.ResponseRoomObj;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.DTO.BookingDTO;
import com.microservice.booking.DTO.DeleteBookingDTO;
import com.microservice.booking.DTO.ResponseBookingDTO;
import com.microservice.booking.DTO.ServiceBookingResponseDTO;
import com.microservice.booking.Entity.Booking;
import com.microservice.booking.Utils.BookingUtils;
import com.microservice.booking.Utils.StatusBooking;
import com.microservice.booking.exceptions.BookingNotFoundException;
import com.microservice.booking.exceptions.RangeDateNotValidException;
import com.microservice.booking.exceptions.RoomIsAlreadyOccupied;
import com.microservice.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private HostClient hostClient;
    private RoomClient roomClient;
    private BookingUtils bookingUtils;

    public BookingService(BookingRepository bookingRepository, HostClient hostClient, RoomClient roomClient, BookingUtils bookingUtils) {
        this.bookingRepository = bookingRepository;
        this.hostClient = hostClient;
        this.roomClient = roomClient;
        this.bookingUtils = bookingUtils;
    }


    public List<ResponseBookingDTO> getAllBookings() {
        List<Booking> bookingListEntity = bookingRepository.findAll();
        if (bookingListEntity.isEmpty()) {
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
                            .totalPrice(bookingEntity.totalPrice)
                            .room(roomData)
                            .hosts(hostData)
                            .build();
                })
                .toList();

        return responseBookingDTOList;
    }


    public ResponseBookingDTO getBooking(Long idBooking) {
        if (idBooking == null) {
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
                .totalPrice(bookingFound.totalPrice)
                .room(roomData)
                .hosts(hostData)
                .build();

        return bookingDTO;
    }


    public ResponseBookingDTO createBooking(BookingDTO bookingDTO) {
        int startDayBooking = bookingDTO.startDate().getDayOfMonth();
        int endDayBooking = bookingDTO.endDate().getDayOfMonth();
        if (startDayBooking > endDayBooking) {
            throw new RangeDateNotValidException("the start date of the booking cannot be earlier than the end date of the booking");
        }
        ResponseHostObj<HostDTO> hostDTOReceived = hostClient.getHost(bookingDTO.idHost());
        ResponseRoomObj<RoomDTO> roomDTOReceived = roomClient.getRoomClient(bookingDTO.idRoom());
        HostDTO hostData = hostDTOReceived.data();
        RoomDTO roomData = roomDTOReceived.data();

        HostDTO hostToUpdate = HostDTO.builder()
                .name(hostData.name())
                .isVipHost(hostData.isVipHost())
                .isRegularHost(hostData.isRegularHost())
                .document(hostData.document())
                .numVisits(hostData.numVisits() + 1)
                .build();
        ResponseHostObj<HostDTO> updatedHost = hostClient.updateHost(hostData.id(), hostToUpdate);

        RoomDTO roomToUpdate = RoomDTO.builder()
                .id(roomData.id())
                .hasTv(roomData.hasTv())
                .hasWifi(roomData.hasWifi())
                .price(roomData.price())
                .personsCapacity(roomData.personsCapacity())
                .isOccupied(true)
                .numBeds(roomData.numBeds())
                .categories(roomData.categories())
                .build();
        ResponseRoomObj<RoomDTO> roomUpdated = roomClient.updateRoom(roomToUpdate, roomData.id());

        float totalPriceBooking = bookingUtils.calculateTotalPriceBooking(roomData, updatedHost.data(), startDayBooking, endDayBooking);
        Booking bookingEntity = Booking.builder()
                .startDate(bookingDTO.startDate())
                .endDate(bookingDTO.endDate())
                .totalPrice(totalPriceBooking)
                .idHost(bookingDTO.idHost())
                .idRoom(bookingDTO.idRoom())
                .build();

        Booking bookingCreated = bookingRepository.save(bookingEntity);

        return ResponseBookingDTO.builder()
                .idBooking(bookingCreated.idBooking)
                .startDate(bookingCreated.startDate)
                .endDate(bookingCreated.endDate)
                .totalPrice(bookingCreated.totalPrice)
                .room(roomData)
                .hosts(updatedHost.data())
                .build();
    }


    public ResponseBookingDTO updateBooking(Long idBooking, BookingDTO bookingDTO) {
        int startDayBooking = bookingDTO.startDate().getDayOfMonth();
        int endDayBooking = bookingDTO.endDate().getDayOfMonth();
        if (startDayBooking > endDayBooking) {
            throw new RangeDateNotValidException("the end date of the booking cannot be earlier than the start date of the booking");
        }

        ResponseBookingDTO existentEntity = getBooking(idBooking);
        boolean hostHasChanged = false;
        boolean roomHasChanged = false;
        boolean hostIsDifferent = !Objects.equals(bookingDTO.idHost(), existentEntity.hosts().id());
        boolean roomIsDifferent = !Objects.equals(bookingDTO.idRoom(), existentEntity.room().id());
        boolean roomIsAlreadyOccupied = roomClient.isRoomOccupied(bookingDTO.idRoom()).data().isRoomOccupied();
        ServiceBookingResponseDTO serviceBookingResponse = ServiceBookingResponseDTO.builder()
                .idBooking(existentEntity.idBooking())
                .startDate(bookingDTO.startDate())
                .endDate(bookingDTO.endDate())
                .build();

        if (!roomIsAlreadyOccupied) {
            if (hostIsDifferent) {
                ResponseHostObj<HostDTO> hostDTOReceived = hostClient.getHost(bookingDTO.idHost());
                HostDTO hostData = hostDTOReceived.data();
                HostDTO hostToUpate = HostDTO.builder()
                        .id(hostData.id())
                        .name(hostData.name())
                        .isVipHost(hostData.isVipHost())
                        .isRegularHost(hostData.isRegularHost())
                        .document(hostData.document())
                        .numVisits(hostData.numVisits() + 1)
                        .build();
                ResponseHostObj<HostDTO> updatedHost = hostClient.updateHost(hostToUpate.id(), hostToUpate);
                serviceBookingResponse.setHosts(updatedHost.data());
                hostHasChanged = true;
            }
            if (roomIsDifferent) {
                ResponseRoomObj<RoomDTO> roomDTOReceived = roomClient.getRoomClient(bookingDTO.idRoom());
                RoomDTO roomData = roomDTOReceived.data();
                RoomDTO roomToUpdate = RoomDTO.builder()
                        .id(roomData.id())
                        .hasTv(roomData.hasTv())
                        .hasWifi(roomData.hasWifi())
                        .numBeds(roomData.numBeds())
                        .price(roomData.price())
                        .personsCapacity(roomData.personsCapacity())
                        .isOccupied(true)
                        .categories(roomData.categories())
                        .build();
                ResponseRoomObj<RoomDTO> updatedRoom = roomClient.updateRoom(roomToUpdate, roomData.id());
                serviceBookingResponse.setRoom(updatedRoom.data());
                roomHasChanged = true;
            }
            if (!roomHasChanged && hostHasChanged) {
                ResponseRoomObj<RoomDTO> roomAssociated = roomClient.getRoomClient(bookingDTO.idRoom());
                ResponseHostObj<HostDTO> hostAssociated = hostClient.getHost(serviceBookingResponse.getHosts().id());
                float totalPriceBooking = bookingUtils.calculateTotalPriceBooking(roomAssociated.data(), hostAssociated.data(), startDayBooking, endDayBooking);
                serviceBookingResponse.setRoom(roomAssociated.data());
                serviceBookingResponse.setHosts(hostAssociated.data());
                serviceBookingResponse.setTotalPrice(totalPriceBooking);
                Booking bookingToUpdate = Booking.builder()
                        .idBooking(existentEntity.idBooking())
                        .startDate(bookingDTO.startDate())
                        .endDate(bookingDTO.endDate())
                        .idHost(hostAssociated.data().id())
                        .idRoom(roomAssociated.data().id())
                        .totalPrice(totalPriceBooking)
                        .status(StatusBooking.INACTIVE)
                        .build();
                bookingRepository.save(bookingToUpdate);
            }
            if (roomHasChanged && !hostHasChanged) {
                ResponseHostObj<HostDTO> hostAssociated = hostClient.getHost(bookingDTO.idHost());
                ResponseRoomObj<RoomDTO> roomAssociated = roomClient.getRoomClient(serviceBookingResponse.getIdBooking());
                float totalPriceBooking = bookingUtils.calculateTotalPriceBooking(roomAssociated.data(), hostAssociated.data(), startDayBooking, endDayBooking);
                serviceBookingResponse.setTotalPrice(totalPriceBooking);
                serviceBookingResponse.setHosts(hostAssociated.data());
                serviceBookingResponse.setRoom(roomAssociated.data());
                Booking bookingToUpdate = Booking.builder()
                        .idBooking(existentEntity.idBooking())
                        .startDate(bookingDTO.startDate())
                        .endDate(bookingDTO.endDate())
                        .idHost(hostAssociated.data().id())
                        .idRoom(roomAssociated.data().id())
                        .totalPrice(totalPriceBooking)
                        .status(StatusBooking.INACTIVE)
                        .build();
                bookingRepository.save(bookingToUpdate);
            }
            if(roomHasChanged && hostHasChanged){
                ResponseRoomObj<RoomDTO> roomAssociated = roomClient.getRoomClient(serviceBookingResponse.getRoom().id());
                ResponseHostObj<HostDTO> hostAssociated = hostClient.getHost(serviceBookingResponse.getHosts().id());
                float totalPriceBooking = bookingUtils.calculateTotalPriceBooking(roomAssociated.data(), hostAssociated.data(), startDayBooking, endDayBooking);
                serviceBookingResponse.setTotalPrice(totalPriceBooking);
                serviceBookingResponse.setRoom(roomAssociated.data());
                serviceBookingResponse.setHosts(hostAssociated.data());
                Booking bookingToUpdate = Booking.builder()
                        .idBooking(existentEntity.idBooking())
                        .startDate(bookingDTO.startDate())
                        .endDate(bookingDTO.endDate())
                        .idHost(hostAssociated.data().id())
                        .idRoom(roomAssociated.data().id())
                        .totalPrice(totalPriceBooking)
                        .status(StatusBooking.INACTIVE)
                        .build();
                bookingRepository.save(bookingToUpdate);
            }
            if(!roomHasChanged && !hostIsDifferent){
                ResponseRoomObj<RoomDTO> roomAssociated = roomClient.getRoomClient(bookingDTO.idRoom());
                ResponseHostObj<HostDTO> hostAssociated = hostClient.getHost(bookingDTO.idHost());
                float totalPriceBooking = bookingUtils.calculateTotalPriceBooking(roomAssociated.data(), hostAssociated.data(), startDayBooking, endDayBooking);
                serviceBookingResponse.setTotalPrice(totalPriceBooking);
                serviceBookingResponse.setHosts(hostAssociated.data());
                serviceBookingResponse.setRoom(roomAssociated.data());
                Booking bookingToUpdate = Booking.builder()
                        .idBooking(existentEntity.idBooking())
                        .startDate(bookingDTO.startDate())
                        .endDate(bookingDTO.endDate())
                        .idHost(hostAssociated.data().id())
                        .idRoom(roomAssociated.data().id())
                        .totalPrice(totalPriceBooking)
                        .status(StatusBooking.INACTIVE)
                        .build();
                bookingRepository.save(bookingToUpdate);
            }
        }else {
            throw new RoomIsAlreadyOccupied("The room is already occupied by other reservation");
        }


        return ResponseBookingDTO.builder()
                .idBooking(serviceBookingResponse.getIdBooking())
                .startDate(serviceBookingResponse.getStartDate())
                .endDate(serviceBookingResponse.getEndDate())
                .totalPrice(serviceBookingResponse.getTotalPrice())
                .hosts(serviceBookingResponse.getHosts())
                .room(serviceBookingResponse.getRoom())
                .status(StatusBooking.INACTIVE)
                .build();
    }


    public void deleteBooking(Long idBooking) {
        if(idBooking == null){
            throw new IllegalArgumentException("the id has to be a number");
        }
        bookingRepository.deleteById(idBooking);
    }
}