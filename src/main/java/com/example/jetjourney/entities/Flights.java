package com.example.jetjourney.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "flights")
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String departure;

    private String destination;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private double price;

    private int availableSeats;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Reservations> reservations;

    // Getters and Setters
}
