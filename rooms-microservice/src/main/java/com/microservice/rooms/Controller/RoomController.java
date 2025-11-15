package com.microservice.rooms.Controller;

import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.Entity.Room;
import com.microservice.rooms.Service.RoomService;
import com.microservice.rooms.Utils.Response;
import jakarta.validation.Valid;
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
    public ResponseEntity<Response<List<RoomDTO>>> getAllRooms() {
        List<RoomDTO> roomList = roomService.getAllRooms();
        return ResponseEntity.ok(new Response<List<RoomDTO>>("Rooms fetched successfully", LocalDateTime.now(), roomList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<RoomDTO>> getOneRoom(@PathVariable("id") Long idRoom) {
        RoomDTO roomDTOFound = roomService.getOneRoom(idRoom);
        return ResponseEntity.ok(new Response<RoomDTO>("Room fetched successfully", LocalDateTime.now(), roomDTOFound));
    }

    @PostMapping("/create")
    public ResponseEntity<Response<RoomDTO>> createRoom(@Valid @RequestBody RoomDTO requesRoomDTO) {
        RoomDTO roomDTOCreated = roomService.createRoom(requesRoomDTO);
        return ResponseEntity.ok(new Response<RoomDTO>("Room created successfully", LocalDateTime.now(), roomDTOCreated));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<RoomDTO>> updateRoom(@Valid @RequestBody RoomDTO roomToUpdate, @PathVariable("id") Long idRoom) {
        System.out.println("body request: " + roomToUpdate);
        RoomDTO roomUpdated = roomService.updateRoom(roomToUpdate, idRoom);
        return ResponseEntity.ok(new Response<RoomDTO>("Room updated successfully", LocalDateTime.now(), roomUpdated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteRoom(@PathVariable("id") Long idRoom) {
        roomService.deleteRoom(idRoom);
        return ResponseEntity.ok(new Response<String>("Room deleted successfully", LocalDateTime.now(), "no data"));
    }
}
