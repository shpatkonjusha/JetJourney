package com.example.jetjourney.services;

import com.example.jetjourney.models.Flight;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FlightService {

    List<Flight> findAll();  // Merr të gjitha fluturimet

    boolean isFlightNumberUnique(String flightNumber);

    Optional<Flight> findById(Long id); // Update to return Optional

    Flight add(Flight flight);  // Shton fluturim të ri

    Flight modify(Flight flight);  // Modifikon fluturimin

    void deleteById(Long id);  // Fshin fluturimin me ID

    List<Flight> findAllByOrigin(String origin);  // Gjen fluturimet nga origjina

    List<Flight> findAllByDestination(String destination);  // Gjen fluturimet për destinacionin

    List<Flight> findAllByStatus(String status);  // Gjen fluturimet për statusin

}
