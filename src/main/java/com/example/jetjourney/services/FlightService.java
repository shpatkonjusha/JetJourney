package com.example.jetjourney.services;

import com.example.jetjourney.enums.FlightStatus;
import com.example.jetjourney.models.Flight;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    List<Flight> findAll();  // Merr të gjitha fluturimet

    Flight findById(Long id);  // Gjen fluturimin me ID

    Flight add(Flight flight);  // Shton fluturim të ri

    Flight modify(Flight flight);  // Modifikon fluturimin

    void deleteById(Long id);  // Fshin fluturimin me ID

    List<Flight> findAllByOrigin(String origin);  // Gjen fluturimet nga origjina

    List<Flight> findAllByDestination(String destination);  // Gjen fluturimet për destinacionin

    List<Flight> findAllByStatus(String status);  // Gjen fluturimet për statusin
}
