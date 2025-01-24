package com.example.jetjourney.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "reservations")
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flights flight;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private LocalDateTime reservationDate;

    private boolean status; // true for Confirmed, false for Cancelled

    // Getters and Setters
}
