package com.microservice.booking.Client;

import com.microservice.booking.Client.model.ResponseRoomObj;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.Utils.Response;
import com.microservice.booking.config.feignClient.FeignConfiguration;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "RoomClient",
        url = "http://localhost:9093",
        configuration = FeignConfiguration.class)
public interface RoomClient {
    @GetMapping("/api/v1/rooms/{id}")
    ResponseRoomObj<RoomDTO> getRoomClient (@PathVariable("id") Long idRoom);

    @PutMapping("/api/v1/rooms/update/{id}")
    ResponseRoomObj<RoomDTO> updateRoom(@Valid @RequestBody RoomDTO roomToUpdate, @PathVariable("id") Long idRoom);
}
