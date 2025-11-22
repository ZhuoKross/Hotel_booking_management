package com.microservice.booking.exceptions;

public class RangeDateNotValidException extends RuntimeException{
    public RangeDateNotValidException(String message){
        super(message);
    }
}
