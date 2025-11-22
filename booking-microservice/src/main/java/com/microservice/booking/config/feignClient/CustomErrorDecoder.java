package com.microservice.booking.config.feignClient;

import com.microservice.booking.exceptions.ResourceNotFoundException;
import com.microservice.booking.exceptions.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        HttpStatus statusResponse = HttpStatus.valueOf(response.status());
        switch (statusResponse){
            case NOT_FOUND -> {
                return new ResourceNotFoundException();
            }
            case SERVICE_UNAVAILABLE -> {
                return new ServiceUnavailableException();
            }
            default -> {
                return new RuntimeException("An error has occurred. ERROR: " + statusResponse + " " + response.body());
            }
        }
    }
}
