package com.microservice.rooms.Utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Standard response object for API endpoints.")
public record Response<T>(
    @Schema(description = "The message of the response", type = "string", example = "Room created successfully.")
    String message,
    @Schema(description = "The date in which the response was generated.", type = "string", format = "date-time")
    LocalDateTime date,
    @Schema(description = "The payload of the response", type = "object", example = """
            {
                "id": 2,
                "numBeds": 1,
                "hasWifi": true,
                "hasTv": true,
                "price": 129.89,
                "personsCapacity": 2,
                "isOccupied": true,
                "categories": [
                    {
                        "id": 3,
                        "name": "LUXURY"
                    }
                ]
            }
            """)
    T data
) {}
