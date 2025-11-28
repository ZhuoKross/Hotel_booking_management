package com.microservice.rooms.Repository;

import com.microservice.rooms.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    public boolean existsRoomByIdAndIsOccupied(Long idRoom, boolean isOccupied);
}
