package com.example.jetjourney.services.impls;


import com.example.jetjourney.enums.FlightStatus;
import com.example.jetjourney.models.Flight;
import com.example.jetjourney.repositories.FlightRepository;
import com.example.jetjourney.services.FlightService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    // Constructor for dependency injection
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Check if the flight number is unique
    public boolean isFlightNumberUnique(String flightNumber) {
        return flightRepository.existsByFlightNumber(flightNumber);
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public Flight add(Flight flight) {
        // Check if the flight number is unique before saving
        if (isFlightNumberUnique(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Flight number must be unique.");
        }
        System.out.println("Adding flight: " + flight);
        return flightRepository.save(flight);
    }

    @Override
    public Flight modify(Flight flight) {
        if (flightRepository.existsById(flight.getId())) {
            // Check if the flight number has changed and if it's unique
            if (isFlightNumberUnique(flight.getFlightNumber())) {
                throw new IllegalArgumentException("Flight number must be unique.");
            }
            return flightRepository.save(flight);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        var existingFlight = findById(id);
        if (!existingFlight.isPresent()) {
            System.out.println("Flight with id " + id + " does not exist.");
            return;
        }
        flightRepository.deleteById(id);
    }

    @Override
    public List<Flight> findAllByOrigin(String origin) {
        return flightRepository.findAllByOrigin(origin);
    }

    @Override
    public List<Flight> findAllByDestination(String destination) {
        return flightRepository.findAllByDestination(destination);
    }

    @Override
    public List<Flight> findAllByStatus(String status) {
        try {
            FlightStatus flightStatus = FlightStatus.valueOf(status.toUpperCase());
            return flightRepository.findAllByStatus(flightStatus);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }
}
