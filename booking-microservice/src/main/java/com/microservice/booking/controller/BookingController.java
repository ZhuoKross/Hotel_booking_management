package com.microservice.booking.controller;

import com.microservice.booking.DTO.BookingDTO;
import com.microservice.booking.DTO.ResponseBookingDTO;
import com.microservice.booking.Entity.Booking;
import com.microservice.booking.Utils.Response;
import com.microservice.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {


    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<ResponseBookingDTO>>> getAllBookings() {
        List<ResponseBookingDTO> bookingList = bookingService.getAllBookings();
        return ResponseEntity.ok(new Response<List<ResponseBookingDTO>>("Bookings fetched succesfully", LocalDateTime.now(), bookingList));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<ResponseBookingDTO>> getBooking(@PathVariable Long id) {
        ResponseBookingDTO bookingDTO = bookingService.getBooking(id);
        return ResponseEntity.ok(new Response<ResponseBookingDTO>("Booking fetched succesfully", LocalDateTime.now(), bookingDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<Response<ResponseBookingDTO>> createBooking(@RequestBody BookingDTO requestBookingDTO) {
        ResponseBookingDTO responseBookingDTO = bookingService.createBooking(requestBookingDTO);
        return ResponseEntity.ok(new Response<ResponseBookingDTO>("Booking created succesfully", LocalDateTime.now(), responseBookingDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteBooking (@PathVariable("id") Long idBooking){
        bookingService.deleteBooking(idBooking);
        return ResponseEntity.ok(new Response<String>("Booking deleted succesfully.", LocalDateTime.now(), "no data"));
    }
}
