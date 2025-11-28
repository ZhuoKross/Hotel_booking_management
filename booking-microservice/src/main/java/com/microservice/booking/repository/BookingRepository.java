package com.microservice.booking.repository;

import com.microservice.booking.Entity.Booking;
import com.microservice.booking.Utils.StatusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public boolean existsByIdRoom(Long idRoom);
    public boolean existsByIdHostAndStatus(Long idHost, StatusBooking statusBooking);
    public boolean existsByStartDateAndEndDateAndIdRoom(LocalDate startDate, LocalDate endDate, Long idRoom);
}
