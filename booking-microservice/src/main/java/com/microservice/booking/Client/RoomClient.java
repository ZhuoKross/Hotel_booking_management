package com.microservice.booking.Client;

import com.microservice.booking.Client.model.ResponseRoomObj;
import com.microservice.booking.Client.model.RoomDTO;
import com.microservice.booking.Utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "RoomClient", url = "http://localhost:9093", path = "/api/v1/rooms/")
public interface RoomClient {
    @GetMapping("/{id}")
    ResponseRoomObj<RoomDTO> getRoomClient (@PathVariable("id") Long idRoom);
}
