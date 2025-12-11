package com.microservice.rooms.Controller;

import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.DTO.RoomResponseDTO;
import com.microservice.rooms.DTO.isRoomOccupiedValidationDTO;
import com.microservice.rooms.Service.RoomService;
import com.microservice.rooms.Utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "rooms", description = "controller for room operations like add, get, update, delete and test if a room is occupied")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    @Operation(
            method = "GET",
            tags = {"rooms", "get"},
            summary = "Method to get all the rooms stored in the DB.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            },
                            description = "Rooms fetched successfully."

                    )
            }

    )
    public ResponseEntity<Response<List<RoomResponseDTO>>> getAllRooms() {
        List<RoomResponseDTO> roomList = roomService.getAllRooms();
        return ResponseEntity.ok(new Response<List<RoomResponseDTO>>("Rooms fetched successfully", LocalDateTime.now(), roomList));
    }


    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            tags = {"rooms", "get"},
            summary = "Method to get ONE room stored in DB by the id.",
            parameters = {
                    @Parameter(
                            required = true,
                            in = ParameterIn.PATH,
                            name = "idRoom",
                            description = "The id of the room that will be queried."
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Response<RoomResponseDTO>> getOneRoom(@PathVariable("id") Long idRoom) {
        RoomResponseDTO roomDTOFound = roomService.getOneRoom(idRoom);
        return ResponseEntity.ok(new Response<RoomResponseDTO>("Room fetched successfully", LocalDateTime.now(), roomDTOFound));
    }

    @Operation(
            method = "POST",
            tags = {"rooms", "post"},
            summary = "Method to create a new room.",
            requestBody = @RequestBody(
                    description = "Request object that has the properties to create a new room.",
                    required = true,
                    content = {
                            @Content(
                                schema = @Schema(
                                        implementation = RoomDTO.class
                                )
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            description = "Room created successfully.",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Response<RoomResponseDTO>> createRoom(@Valid @RequestBody RoomDTO requestRoomDTO) {
        RoomResponseDTO roomDTOCreated = roomService.createRoom(requestRoomDTO);
        return ResponseEntity.ok(new Response<RoomResponseDTO>("Room created successfully", LocalDateTime.now(), roomDTOCreated));
    }

    @Operation(
            method = "PUT",
            summary = "Method to update an existing room.",
            description = "It takes two arguments: a request body that has the changes and the id that is passed from the path or url.",
            tags = {"rooms", "put"},
            parameters = {
                    @Parameter(
                            name = "idRoom",
                            description = "the id of the room that will be updated.",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            requestBody = @RequestBody(
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = RoomDTO.class
                                    )
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room updated successfully.",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Response<RoomResponseDTO>> updateRoom(@Valid @RequestBody RoomDTO roomToUpdate, @PathVariable("id") Long idRoom) {
        RoomResponseDTO roomUpdated = roomService.updateRoom(roomToUpdate, idRoom);
        return ResponseEntity.ok(new Response<RoomResponseDTO>("Room updated successfully", LocalDateTime.now(), roomUpdated));
    }

    @Operation(
            method = "DELETE",
            summary = "Method to delete a room.",
            tags = {"rooms", "delete"},
            parameters = {
                    @Parameter(
                            name = "idRoom",
                            required = true,
                            description = "The id of the room that will be deleted.",
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room deleted successfully.",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = Response.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteRoom(@PathVariable("id") Long idRoom) {
        roomService.deleteRoom(idRoom);
        return ResponseEntity.ok(new Response<String>("Room deleted successfully", LocalDateTime.now(), "no data"));
    }

    @Operation(
            method = "GET",
            tags = {"rooms","get"},
            summary = "Method to verify is a room is occupied.",
            parameters = {
                    @Parameter(
                            name = "idRoom",
                            required = true,
                            in = ParameterIn.PATH,
                            description = "The id of the room that will be queried."
                    )
            },
            responses = {
                    @ApiResponse(
                            description = "Action performed successfully.",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping("/isOccupied/{id}")
    public  ResponseEntity<Response<isRoomOccupiedValidationDTO>> isRoomOccupied(@PathVariable("id") Long idRoom){
        isRoomOccupiedValidationDTO roomIsOccupied = roomService.existsRoomOccupied(idRoom);
        return ResponseEntity.ok(new Response<isRoomOccupiedValidationDTO>("Action performed successfully", LocalDateTime.now(), roomIsOccupied));
    }
}
