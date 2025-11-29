package com.microservice.booking.exceptions;

public class CheckInStatusActiveException extends RuntimeException{
    public CheckInStatusActiveException(String message){
        super(message);
    }
}
