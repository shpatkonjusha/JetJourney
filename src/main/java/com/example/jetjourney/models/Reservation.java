package com.example.jetjourney.models;

import com.example.jetjourney.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations_seq")
    @SequenceGenerator(name = "reservations_seq", sequenceName = "reservations_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email; // Unique because it's often used to identify reservations

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 20)
    private String personalId; // National ID or passport number



    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight; // Linking the reservation to the Flight entity

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status; // Enum for reservation status (CONFIRMED, CANCELLED, etc.)
}
