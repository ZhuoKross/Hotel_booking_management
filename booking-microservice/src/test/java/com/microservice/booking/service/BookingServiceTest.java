package com.microservice.booking.service;

import com.microservice.booking.Client.HostClient;
import com.microservice.booking.Client.RoomClient;
import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.DTO.ResponseBookingDTO;
import com.microservice.booking.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith( MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    HostClient hostClient;

    @Mock
    RoomClient roomClient;


    @Test
    void getAllBookings() {
        when(bookingRepository.findAll()).thenReturn(DataMocked.getAllBookingsMock());
        when(hostClient.getHost(anyLong())).thenReturn(DataMocked.getHostClientMock());
        when(roomClient.getRoomClient(anyLong())).thenReturn(DataMocked.getRoomClientMock());

        List<ResponseBookingDTO> listResponseBookings = bookingService.getAllBookings();

        assertNotNull(listResponseBookings);
        listResponseBookings.forEach((booking) -> assertInstanceOf(HostDTO.class, booking.hosts()) );
    }

    @Test
    void getBooking() {
    }

    @Test
    void createBooking() {
    }
}