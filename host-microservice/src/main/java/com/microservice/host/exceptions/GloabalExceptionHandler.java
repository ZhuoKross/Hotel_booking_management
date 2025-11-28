package com.microservice.host.exceptions;

import com.microservice.host.Utils.Response;
import org.springframework.dao.DataIntegrityViolationException;
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
public class GloabalExceptionHandler {

    @ExceptionHandler(HostNotFoundException.class)
    public ResponseEntity<Response<String>> handleHostNotFound (){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<String>("Host not found, please verify you are passing and try again", LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleIllegalArgument (){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>("Bad argument passed, verify you are passing and try again", LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleNotValidArgument (){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>("Malfomed body request, verify and try again.", LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleNotReadable (HttpMessageNotReadableException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>(exception.getMessage(), LocalDateTime.now(), "no data"));
    }


    @ExceptionHandler(DocumentLengthNotValidException.class)
    public ResponseEntity<Response<String>> handleDocumentNotValid (){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>("The length of the document has to be at least of 10 digits.", LocalDateTime.now(), "no data"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<String>> handleConstraintExceptions (DataIntegrityViolationException exception){
        String cause = exception.getMessage();
        String message = "";
        if(cause.contains("uc_document")){
            message = "The document already exists";
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<String>(message, LocalDateTime.now(), "no data"));
    }
}
