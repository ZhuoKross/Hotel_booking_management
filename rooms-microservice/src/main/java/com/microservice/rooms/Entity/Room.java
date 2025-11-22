package com.microservice.rooms.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Table(name = "rooms")
@Entity
@Setter
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
    public float price;
    @Column(name = "persons_capacity")
    public int personsCapacity;
    @Column(name = "is_occupied")
    public boolean isOccupied;
    @OneToMany(targetEntity = Category.class, fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.PERSIST)
    public List<Category> categories;
}