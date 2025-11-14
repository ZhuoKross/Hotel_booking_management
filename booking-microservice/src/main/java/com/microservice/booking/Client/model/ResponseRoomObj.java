package com.microservice.booking.Client.model;


import java.time.LocalDateTime;

public record ResponseRoomObj<T>(
        String message,
        LocalDateTime date,
        T data
) {}
