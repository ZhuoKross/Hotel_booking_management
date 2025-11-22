package com.microservice.booking.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "bookings")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idBooking;
    @Column(name = "start_date")
    public LocalDate startDate;
    @Column(name = "end_date")
    public LocalDate endDate;
    @Column(name = "id_host")
    public Long idHost;
    @Column(name = "id_room")
    public Long idRoom;
    @Column(name = "tota_price")
    public float totalPrice;

}
