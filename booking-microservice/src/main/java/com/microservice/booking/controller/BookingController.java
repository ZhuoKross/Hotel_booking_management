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

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings (){
        try{
            List<ResponseBookingDTO> bookingList = bookingService.getAllBookings();
            return ResponseEntity.ok(new Response<List<ResponseBookingDTO>>("Bookings fetched succesfully", LocalDateTime.now(), bookingList));

        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(new Response<String>("Couldn't get the list of bookings", LocalDateTime.now(), e.getMessage()));
        }
    };

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking (@PathVariable Long id){
        try {
            ResponseBookingDTO bookingDTO = bookingService.getBooking(id);
            return ResponseEntity.ok(new Response<ResponseBookingDTO>("Booking fetched succesfully", LocalDateTime.now(), bookingDTO));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(new Response<String>("Couldn't get the booking", LocalDateTime.now(), e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking (@RequestBody BookingDTO requestBookingDTO){
        ResponseBookingDTO responseBookingDTO = bookingService.createBooking(requestBookingDTO);
        return ResponseEntity.ok(new Response<ResponseBookingDTO>("Booking created succesfully", LocalDateTime.now(), responseBookingDTO));
        /*try {

        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(new Response<String>("Couldn't create the booking", LocalDateTime.now(), e.getMessage()));
        }*/
    }
}
