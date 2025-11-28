package com.microservice.booking.exceptions;

public class RoomIsAlreadyOccupied extends RuntimeException{
    public RoomIsAlreadyOccupied(String message){
        super(message);
    }
}
