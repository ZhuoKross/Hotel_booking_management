package com.microservice.booking.config.feignClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.booking.DTO.FeignResponseError;
import com.microservice.booking.exceptions.ResourceNotFoundException;
import com.microservice.booking.exceptions.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        HttpStatus statusResponse = HttpStatus.valueOf(response.status());
        ObjectMapper objectMapper = new ObjectMapper();
        FeignResponseError feignResponseError = null;

        try (InputStream bodyResponse = response.body().asInputStream()){
            feignResponseError = objectMapper.readValue(bodyResponse,FeignResponseError.class);
        } catch (IOException e) {
            throw new RuntimeException("Error formating response");
        }

        switch (statusResponse){
            case NOT_FOUND -> {
                return new ResourceNotFoundException(feignResponseError.getMessage());
            }
            case SERVICE_UNAVAILABLE -> {
                return new ServiceUnavailableException(feignResponseError.getMessage());
            }
            case BAD_REQUEST -> {
                return new BadRequestException(feignResponseError.getMessage());
            }
            default -> {
                return new RuntimeException("An error has occurred. ERROR: " + statusResponse + " " + feignResponseError.getMessage());
            }
        }
    }
}
