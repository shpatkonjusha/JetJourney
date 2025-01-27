package com.example.jetjourney.models;

import com.example.jetjourney.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String flightNumber;

    @Column(nullable = false,length = 100)
    private String origin;

    @Column(nullable = false,length = 100)
    private String destination;

    @Column(nullable = false,length = 100)
    private String departureTime;

    @Column(nullable = false,length = 100)
    private String arrivalTime;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;




    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
}
