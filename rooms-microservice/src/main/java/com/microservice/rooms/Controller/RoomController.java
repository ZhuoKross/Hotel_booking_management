package com.microservice.rooms.Controller;

import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.Entity.Room;
import com.microservice.rooms.Service.RoomService;
import com.microservice.rooms.Utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRooms() {
        List<RoomDTO> roomList = roomService.getAllRooms();
        return ResponseEntity.ok(new Response<List<RoomDTO>>("Rooms fetched successfully", LocalDateTime.now(), roomList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneRoom(@PathVariable("id") Long idRoom) {
        RoomDTO roomDTOFound = roomService.getOneRoom(idRoom);
        return ResponseEntity.ok(new Response<RoomDTO>("Room fetched successfully", LocalDateTime.now(), roomDTOFound));
    }


    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody RoomDTO requesRoomDTO) {
        try {
            RoomDTO roomDTOCreated = roomService.createRoom(requesRoomDTO);
            return ResponseEntity.ok(new Response<RoomDTO>("Room created successfully", LocalDateTime.now(), roomDTOCreated));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(new Response<String>("Couldn't create the room", LocalDateTime.now(), e.getMessage()));
        }
    }
}
