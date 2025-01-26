package com.example.jetjourney.repositories;

import com.example.jetjourney.enums.FlightStatus;
import com.example.jetjourney.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findAllByOrigin(String origin);

    List<Flight> findAllByDestination(String destination);

    List<Flight> findAllByStatus(FlightStatus status);
    List<Flight> findAllByStatusAndOrigin(FlightStatus status, String origin);
    List<Flight> findAllByStatusAndDestination(FlightStatus status, String destination);

    List<Flight> findAllByDepartureTime(String departureTime);
}
