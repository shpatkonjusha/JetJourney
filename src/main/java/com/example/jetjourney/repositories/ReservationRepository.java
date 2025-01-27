package com.example.jetjourney.repositories;

import com.example.jetjourney.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByFlightId(Long flightId); // Find reservations by flight ID
    List<Reservation> findAllByPersonalId(String personalId); // Find reservations by personal ID
}
