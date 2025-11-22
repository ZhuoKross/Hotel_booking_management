package com.microservice.booking.Client;

import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.ResponseHostObj;
import com.microservice.booking.config.feignClient.FeignConfiguration;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "hostClient",
        url = "http://localhost:9092",
        configuration = FeignConfiguration.class)
public interface HostClient {
    @GetMapping("/api/v1/hosts/{id}")
    ResponseHostObj<HostDTO> getHost(@PathVariable("id") Long id);

    @PutMapping("/api/v1/hosts/update/{id}")
    ResponseHostObj<HostDTO> updateHost(@PathVariable("id") Long idHost, @RequestBody @Valid HostDTO hostDTO);
}
