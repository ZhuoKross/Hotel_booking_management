package com.microservice.booking.Client;

import com.microservice.booking.Client.model.ResponseRoomObj;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.Client.model.isRoomOccupiedValidationDTO;
import com.microservice.booking.Utils.Response;
import com.microservice.booking.config.feignClient.FeignConfiguration;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "RoomClient",
        url = "http://localhost:9093",
        configuration = FeignConfiguration.class,
        path = "/api/v1/rooms"
)
public interface RoomClient {
    @GetMapping("/{id}")
    ResponseRoomObj<RoomDTO> getRoomClient (@PathVariable("id") Long idRoom);

    @PutMapping("/update/{id}")
    ResponseRoomObj<RoomDTO> updateRoom(@Valid @RequestBody RoomDTO roomToUpdate, @PathVariable("id") Long idRoom);

    @GetMapping("/isOccupied/{id}")
    Response<isRoomOccupiedValidationDTO> isRoomOccupied(@PathVariable("id") Long idRoom);
}
