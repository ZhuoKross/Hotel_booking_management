package com.microservice.booking.Client;

import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.config.feignClient.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "hostClient",
        url = "http://localhost:9092",
        configuration = FeignConfiguration.class)
public interface HostClient {
    @GetMapping("/api/v1/hosts/{id}")
    HostDTO getHost(@PathVariable Long id);

}
