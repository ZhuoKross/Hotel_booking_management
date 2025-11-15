package com.microservice.rooms.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException (String message){
        super(message);
    }
}
