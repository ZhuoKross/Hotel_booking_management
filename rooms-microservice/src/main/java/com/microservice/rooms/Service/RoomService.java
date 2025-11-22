package com.microservice.rooms.Service;

import com.microservice.rooms.DTO.CategoryDTO;
import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.DTO.RoomResponseDTO;
import com.microservice.rooms.Entity.Category;
import com.microservice.rooms.Entity.Room;
import com.microservice.rooms.Repository.RoomRepository;
import com.microservice.rooms.Utils.RoomUtils;
import com.microservice.rooms.exceptions.RoomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;
    private CategoryService categoryService;
    private RoomUtils roomUtils;

    public RoomService(RoomRepository roomRepository, CategoryService categoryService, RoomUtils roomUtils) {
        this.roomRepository = roomRepository;
        this.categoryService = categoryService;
        this.roomUtils = roomUtils;
    }

    public List<RoomResponseDTO> getAllRooms() {
        List<Room> roomListEntity = roomRepository.findAll();
        if (roomListEntity.isEmpty()) {
            throw new RoomNotFoundException();
        }


        return roomListEntity.stream().map((room) -> {
            List<Category> categoriesAssociated = room.categories;
            List<CategoryDTO> categoryDTOList = categoriesAssociated.stream().map((category) -> {
                return CategoryDTO.builder().id(category.Id).name(category.name).idRoom(category.room.id).build();
            }).toList();

            return RoomResponseDTO.builder()
                    .id(room.id)
                    .hasWifi(room.hasWifi)
                    .hasTv(room.hasTv)
                    .numBeds(room.numBeds)
                    .personsCapacity(room.personsCapacity)
                    .isOccupied(room.isOccupied)
                    .price(room.price)
                    .categories(categoryDTOList)
                    .build();
        }).toList();
    }

    public RoomResponseDTO getOneRoom(Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException();
        }

        Room roomEntity = roomRepository.findById(idRoom).orElseThrow(RoomNotFoundException::new);
        List<CategoryDTO> categoriesAssociated = roomEntity.categories.stream().map((category) -> {
            return CategoryDTO.builder().id(category.Id).name(category.name).idRoom(category.room.id).build();
        }).toList();
        return RoomResponseDTO.builder()
                .id(roomEntity.id)
                .numBeds(roomEntity.numBeds)
                .hasWifi(roomEntity.hasWifi)
                .hasTv(roomEntity.hasTv)
                .price(roomEntity.price)
                .personsCapacity(roomEntity.personsCapacity)
                .isOccupied(roomEntity.isOccupied)
                .categories(categoriesAssociated)
                .build();
    }


    public RoomResponseDTO createRoom(RoomDTO roomDTO) {
        float valueOfRoom = roomUtils.calculateValueRoom(roomDTO);
        Room roomEntity = Room.builder()
                .numBeds(roomDTO.numBeds())
                .hasWifi(roomDTO.hasWifi())
                .hasTv(roomDTO.hasTv())
                .price(valueOfRoom)
                .personsCapacity(roomDTO.personsCapacity())
                .isOccupied(false)
                .build();
        List<Category> categories = roomDTO.categories().stream().map((categoryDTO) -> {
            return Category.builder().Id(categoryDTO.id()).name(categoryDTO.name()).room(roomEntity).build();
        }).toList();

        roomEntity.setCategories(categories);

        Room roomCreated = roomRepository.save(roomEntity);
        List<CategoryDTO> categoriesAssociated = roomCreated.categories.stream().map((category) -> {
            return CategoryDTO.builder().id(category.Id).name(category.name).idRoom(roomCreated.id).build();
        }).toList();

        return RoomResponseDTO.builder()
                .id(roomCreated.id)
                .hasTv(roomCreated.hasTv)
                .hasWifi(roomCreated.hasWifi)
                .price(roomCreated.price)
                .personsCapacity(roomCreated.personsCapacity)
                .isOccupied(true)
                .categories(categoriesAssociated)
                .build();
    }

    public RoomResponseDTO updateRoom(RoomDTO roomToUpdate, Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException();
        }
        float valueOfRoom = roomUtils.calculateValueRoom(roomToUpdate);
        Room roomFound = roomRepository.findById(idRoom).orElseThrow(RoomNotFoundException::new);
        roomFound.setHasTv(roomToUpdate.hasTv());
        roomFound.setHasWifi(roomToUpdate.hasWifi());
        roomFound.setPrice(valueOfRoom);
        roomFound.setPersonsCapacity(roomToUpdate.personsCapacity());
        roomFound.setOccupied(roomToUpdate.isOccupied());
        roomFound.setNumBeds(roomToUpdate.numBeds());
        List<Category> categoryListEntities = new ArrayList<>();
        for(var categoryDTO : roomToUpdate.categories()){
            categoryListEntities.add(
                    Category.builder().Id(categoryDTO.id()).name(categoryDTO.name()).room(roomFound).build()
            );
        }
        roomFound.setCategories(categoryListEntities);

        Room roomCreated = roomRepository.save(roomFound);
        List<CategoryDTO> categoryDTOList = roomCreated.categories.stream().map((category) -> {
            return CategoryDTO.builder().id(category.Id).name(category.name).idRoom(category.room.id).build();
        }).toList();

        return RoomResponseDTO.builder()
                .id(roomCreated.id)
                .hasTv(roomCreated.hasTv)
                .hasWifi(roomCreated.hasWifi)
                .price(roomCreated.price)
                .personsCapacity(roomCreated.personsCapacity)
                .isOccupied(roomCreated.isOccupied)
                .categories(categoryDTOList)
                .build();
    }

    public void deleteRoom(Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException();
        }

        roomRepository.deleteById(idRoom);
    }


}
