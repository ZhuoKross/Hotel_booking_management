package com.microservice.host.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "hosts")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "is_vip_host")
    public boolean isVipHost;
    @Column(name = "is_regular_host")
    public boolean isRegularHost;
    @Column
    public int document;
    @Column(name = "num_visits")
    public int numVisits;
    @Column
    public String name;
}
