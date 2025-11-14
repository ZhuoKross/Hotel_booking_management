package com.microservice.booking.Client;

import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.ResponseHostObj;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "hostClient", url = "http://localhost:9092", path = "/api/v1/hosts/")
public interface HostClient {
    @GetMapping("/{id}")
    ResponseHostObj<HostDTO> getHost(@PathVariable("id") Long id);

}
