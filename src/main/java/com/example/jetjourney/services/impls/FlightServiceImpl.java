package com.example.jetjourney.services.impls;


import com.example.jetjourney.enums.FlightStatus;
import com.example.jetjourney.models.Flight;
import com.example.jetjourney.repositories.FlightRepository;
import com.example.jetjourney.services.FlightService;
import jdk.jfr.Timestamp;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public void add() {
        Flight flight = new Flight();
        flight.setFlightNumber("AA123");
        flight.setDepartureTime("2021-12-31 23:59:59");
        flight.setArrivalTime("2022-01-01 06:00:00");
        flight.setOrigin("New York");
        flight.setDestination("Los Angeles");
        flight.setAvailableSeats(150);
        flight.setPrice(321.0);
        flight.setStatus(FlightStatus.valueOf("ACTIVE"));

        flightRepository.save(flight);  // Don't set ID manually
    }

    // Konstruktor për injektimin manual të vargut të kërkuar (për shembull, nëse është testuar manualisht)
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();  // Merr të gjitha fluturimet nga repository
    }

    @Override
    public Flight findById(Long id) {
        Optional<Flight> flight = flightRepository.findById(id);  // Gjen fluturimin me ID
        return flight.orElse(null);  // Kthe fluturimin nëse ekziston, ndryshe kthe null
    }

    @Override
    public Flight add(Flight flight) {
        System.out.println("Adding flight: " + flight);  // Log to check if flight is being passed correctly
        return flightRepository.save(flight);
    }

    @Override
    public Flight modify(Flight flight) {
        if (flightRepository.existsById(flight.getId())) {
            return flightRepository.save(flight);  // Përditëso fluturimin në repository
        }
        return null;  // Nëse fluturimi nuk ekziston, kthe null
    }
    @Override
    public void deleteById(Long id) {
        var existingFlight = findById(id);
        if (existingFlight == null) {
            System.out.println("Flight me kete id nuk ekziston: " + id);
            return;
        }
        flightRepository.deleteById(id);
//        repository.delete(existingDriver);
    }



    @Override
    public List<Flight> findAllByOrigin(String origin) {
        return flightRepository.findAllByOrigin(origin);  // Gjen fluturimet nga origjina e caktuar
    }

    @Override
    public List<Flight> findAllByDestination(String destination) {
        return flightRepository.findAllByDestination(destination);  // Gjen fluturimet për destinacionin e caktuar
    }


    @Override
    public List<Flight> findAllByStatus(String status) {
        try {
            FlightStatus flightStatus = FlightStatus.valueOf(status.toUpperCase());  // Konvertoni String në enum
            return flightRepository.findAllByStatus(flightStatus);  // Përdorni FlightStatus në repository
        } catch (IllegalArgumentException e) {
            // Nëse statusi nuk është valid, mund të trajtoni gabimin ose të ktheni një listë të zbrazët
            return new ArrayList<>();  // Mund të ktheni një listë të zbrazët ose mund të shtoni logjikë për trajtimin e gabimit
        }
    }

}
