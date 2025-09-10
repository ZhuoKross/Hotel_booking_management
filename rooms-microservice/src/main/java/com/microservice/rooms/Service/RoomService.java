package com.microservice.rooms.Service;

import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.Entity.Room;
import com.microservice.rooms.Repository.RoomRepository;
import com.microservice.rooms.Utils.Response;
import com.microservice.rooms.exceptions.RoomNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<RoomDTO> getAllRooms (){
        List<Room> roomListEntity = roomRepository.findAll();
        if(roomListEntity.isEmpty()){
            throw new RoomNotFoundException();
        }
        List<RoomDTO> roomDTOList = roomListEntity.stream()
                .map((room) -> {
                            return RoomDTO.builder()
                                    .id(room.id)
                                    .numBeds(room.numBeds)
                                    .hasWifi(room.hasWifi)
                                    .hasTv(room.hasTv)
                                    .price(room.price)
                                    .personsCapacity(room.personsCapacity)
                                    .build();}
                ).toList();

        return roomDTOList;
    }

    public RoomDTO getOneRoom (Long idRoom){
        if(idRoom == null){
            throw new IllegalArgumentException();
        }
        Room roomEntity = roomRepository.findById(idRoom).orElseThrow(RoomNotFoundException::new);
        RoomDTO roomDTO = RoomDTO.builder()
                .id(roomEntity.id)
                .numBeds(roomEntity.numBeds)
                .hasWifi(roomEntity.hasWifi)
                .hasTv(roomEntity.hasTv)
                .price(roomEntity.price)
                .personsCapacity(roomEntity.personsCapacity)
                .build();

        return roomDTO;
    }


    public RoomDTO createRoom (RoomDTO roomDTO){
        Room roomEntity = Room.builder()
                .numBeds(roomDTO.numBeds())
                .hasWifi(roomDTO.hasWifi())
                .hasTv(roomDTO.hasTv())
                .price(roomDTO.price())
                .personsCapacity(roomDTO.personsCapacity())
                .build();

        Room roomCreated = roomRepository.save(roomEntity);

        return roomDTO;
    }

    public RoomDTO updateRoom (RoomDTO roomToUpdate, Long idRoom){
        if (idRoom == null){
            throw new IllegalArgumentException();
        }

        Room roomFound = roomRepository.findById(idRoom).orElseThrow(RoomNotFoundException::new);
        roomFound.hasTv = roomToUpdate.hasTv();
        roomFound.hasWifi = roomToUpdate.hasWifi();
        roomFound.price = roomToUpdate.price();
        roomFound.personsCapacity = roomToUpdate.personsCapacity();
        roomFound.numBeds = roomToUpdate.numBeds();

        roomRepository.save(roomFound);
        return roomToUpdate;
    }

    public boolean deleteRoom(Long idRoom){
        if(idRoom == null){
            throw new IllegalArgumentException();
        }

        roomRepository.deleteById(idRoom);
        return true;
    }


}
