package com.microservice.rooms.Service;

import com.microservice.rooms.DTO.CategoryDTO;
import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.Entity.Category;
import com.microservice.rooms.Entity.Room;
import com.microservice.rooms.Repository.RoomRepository;
import com.microservice.rooms.Utils.Response;
import com.microservice.rooms.exceptions.RoomNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;
    private CategoryService categoryService;

    public RoomService(RoomRepository roomRepository, CategoryService categoryService) {
        this.roomRepository = roomRepository;
        this.categoryService = categoryService;
    }

    public List<RoomDTO> getAllRooms() {
        List<Room> roomListEntity = roomRepository.findAll();
        if (roomListEntity.isEmpty()) {
            throw new RoomNotFoundException();
        }

        return roomListEntity.stream().map((room) -> {
            CategoryDTO categoryAssociated = categoryService.getOneCategory(room.category.Id);
            return RoomDTO.builder()
                    .id(room.id)
                    .hasWifi(room.hasWifi)
                    .hasTv(room.hasTv)
                    .numBeds(room.numBeds)
                    .personsCapacity(room.personsCapacity)
                    .isOccupied(room.isOccupied)
                    .category(categoryAssociated)
                    .build();
        }).toList();
    }

    public RoomDTO getOneRoom(Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException();
        }

        Room roomEntity = roomRepository.findById(idRoom).orElseThrow(RoomNotFoundException::new);
        CategoryDTO categoryAssociated = categoryService.getOneCategory(roomEntity.category.Id);
        return RoomDTO.builder()
                .id(roomEntity.id)
                .numBeds(roomEntity.numBeds)
                .hasWifi(roomEntity.hasWifi)
                .hasTv(roomEntity.hasTv)
                .price(roomEntity.price)
                .personsCapacity(roomEntity.personsCapacity)
                .isOccupied(roomEntity.isOccupied)
                .category(categoryAssociated)
                .build();
    }


    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room roomEntity = Room.builder()
                .numBeds(roomDTO.numBeds())
                .hasWifi(roomDTO.hasWifi())
                .hasTv(roomDTO.hasTv())
                .price(roomDTO.price())
                .personsCapacity(roomDTO.personsCapacity())
                .isOccupied(true)
                .category(Category.builder()
                        .Id(roomDTO.category().id())
                        .name(roomDTO.category().name())
                        .build())
                .build();

        Room roomCreated = roomRepository.save(roomEntity);
        CategoryDTO categoryAssociated = categoryService.getOneCategory(roomCreated.category.Id);

        return RoomDTO.builder()
                .id(roomCreated.id)
                .hasTv(roomCreated.hasTv)
                .hasWifi(roomCreated.hasWifi)
                .price(roomCreated.price)
                .personsCapacity(roomCreated.personsCapacity)
                .isOccupied(true)
                .category(categoryAssociated)
                .build();
    }

    public RoomDTO updateRoom(RoomDTO roomToUpdate, Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException();
        }

        Room roomFound = roomRepository.findById(idRoom).orElseThrow(RoomNotFoundException::new);
        roomFound.hasTv = roomToUpdate.hasTv();
        roomFound.hasWifi = roomToUpdate.hasWifi();
        roomFound.price = roomToUpdate.price();
        roomFound.personsCapacity = roomToUpdate.personsCapacity();
        roomFound.category = Category.builder()
                .Id(roomToUpdate.category().id())
                .name(roomToUpdate.category().name())
                .build();
        roomFound.numBeds = roomToUpdate.numBeds();

        roomRepository.save(roomFound);

        return RoomDTO.builder()
                .id(roomFound.id)
                .hasTv(roomFound.hasTv)
                .hasWifi(roomFound.hasWifi)
                .price(roomFound.price)
                .personsCapacity(roomFound.personsCapacity)
                .category(CategoryDTO.builder()
                        .id(roomFound.category.Id)
                        .name(roomFound.category.name)
                        .build())
                .build();
    }

    public void deleteRoom(Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException();
        }

        roomRepository.deleteById(idRoom);
    }


}
