package com.microservice.rooms.Service;

import com.microservice.rooms.DTO.RoomDTO;
import com.microservice.rooms.Entity.Room;
import com.microservice.rooms.Repository.RoomRepository;
import com.microservice.rooms.exceptions.RoomNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

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
        Room roomEntity = roomRepository.findById(idRoom).orElse(null);
        if(roomEntity == null){
            throw new RoomNotFoundException();
        }
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

}
