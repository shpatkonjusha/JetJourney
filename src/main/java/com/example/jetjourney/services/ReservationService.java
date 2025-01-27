package com.example.jetjourney.services;

import com.example.jetjourney.models.Reservation;

import java.util.List;
import java.util.Optional;  // Import Optional

public interface ReservationService {

    List<Reservation> findAll(); // Retrieve all reservations

    Reservation findById(Long id);

    Reservation add(Reservation reservation); // Add a new reservation

    Reservation modify(Reservation reservation); // Update an existing reservation

    void deleteById(Long id); // Delete a reservation by ID

    List<Reservation> findByFlightId(Long flightId); // Retrieve all reservations for a specific flight

    List<Reservation> findByPersonalId(String personalId); // Retrieve reservations by personal ID
}
