package com.microservice.rooms.Controller;

import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.DTO.RoomResponseDTO;
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
    public ResponseEntity<Response<List<RoomResponseDTO>>> getAllRooms() {
        List<RoomResponseDTO> roomList = roomService.getAllRooms();
        return ResponseEntity.ok(new Response<List<RoomResponseDTO>>("Rooms fetched successfully", LocalDateTime.now(), roomList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<RoomResponseDTO>> getOneRoom(@PathVariable("id") Long idRoom) {
        RoomResponseDTO roomDTOFound = roomService.getOneRoom(idRoom);
        return ResponseEntity.ok(new Response<RoomResponseDTO>("Room fetched successfully", LocalDateTime.now(), roomDTOFound));
    }

    @PostMapping("/create")
    public ResponseEntity<Response<RoomResponseDTO>> createRoom(@Valid @RequestBody RoomDTO requestRoomDTO) {
        RoomResponseDTO roomDTOCreated = roomService.createRoom(requestRoomDTO);
        return ResponseEntity.ok(new Response<RoomResponseDTO>("Room created successfully", LocalDateTime.now(), roomDTOCreated));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<RoomResponseDTO>> updateRoom(@Valid @RequestBody RoomDTO roomToUpdate, @PathVariable("id") Long idRoom) {
        RoomResponseDTO roomUpdated = roomService.updateRoom(roomToUpdate, idRoom);
        return ResponseEntity.ok(new Response<RoomResponseDTO>("Room updated successfully", LocalDateTime.now(), roomUpdated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteRoom(@PathVariable("id") Long idRoom) {
        roomService.deleteRoom(idRoom);
        return ResponseEntity.ok(new Response<String>("Room deleted successfully", LocalDateTime.now(), "no data"));
    }
}
