package com.microservice.booking.exceptions;


import com.microservice.booking.Utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleIllegalArgument (IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>(exception.getMessage(), LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handleValidations (MethodArgumentNotValidException errors){
        Map<String, String> validationsErrors = new HashMap<>();

        errors.getBindingResult().getFieldErrors().forEach((err) ->{
            validationsErrors.put(err.getField(), err.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<Map<String, String>>("Malformed request body", LocalDateTime.now(), validationsErrors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleMalformedRequestBody (){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>("Malformed request body. It couldn't be possible deserialize the object", LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Response<String>> handleBookingNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<String>("Booking not Found. Try again later", LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response<String>> handleResourceNotFoundException(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<String>("The resource wasn't found, try again.", LocalDateTime.now(), exception.getMessage()));
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Response<String>> handleServiceUnavailableException(ServiceUnavailableException exception){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Response<String>("The service is unavailable right now, please try again later.", LocalDateTime.now(), exception.getMessage()));
    }

    @ExceptionHandler(RangeDateNotValidException.class)
    public ResponseEntity<Response<String>> handleRangeDateNotValidException(RangeDateNotValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>("The range of the date of the booking is not valid", LocalDateTime.now(), exception.getMessage()));
    }

    @ExceptionHandler(RoomIsAlreadyOccupied.class)
    public ResponseEntity<Response<String>> handleRoomIsAlreadyOccupied (RoomIsAlreadyOccupied exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>(exception.getMessage(), LocalDateTime.now(), "no data"));
    }

}
