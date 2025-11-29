package com.microservice.booking.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@JsonIgnoreProperties({"date"})
public class FeignResponseError {
    public String message;
    public Date date;
    public String data;
}
