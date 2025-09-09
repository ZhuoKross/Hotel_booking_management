package com.microservice.rooms.exceptions;

import com.microservice.rooms.Utils.Response;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleIllegalArgument (){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>("Wrong input", LocalDateTime.now(), "Check the value you passed and try again."));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Response<String>> handleNotFound (){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<String>("Object not found", LocalDateTime.now(), "Check the URL and try again"));
    }
}
