package com.microservice.rooms.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "rooms")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "num_beds")
    public int numBeds;
    @Column(name = "has_wifi")
    public boolean hasWifi;
    @Column(name = "has_tv")
    public boolean hasTv;
    @Column
    public double price;
    @Column(name = "persons_capacity")
    public int personsCapacity;

    @Column(name = "is_occupied")
    public boolean isOccupied;
    @OneToOne(targetEntity = Category.class)
    public Category category;

}
