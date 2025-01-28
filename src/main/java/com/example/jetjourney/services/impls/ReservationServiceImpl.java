package com.example.jetjourney.services.impls;

import com.example.jetjourney.models.Flight;
import com.example.jetjourney.models.Reservation;
import com.example.jetjourney.repositories.FlightRepository;
import com.example.jetjourney.repositories.ReservationRepository;
import com.example.jetjourney.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, FlightRepository flightRepository) {
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll(); // Retrieve all reservations
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id); // Return Optional from repository
    }



    @Override
    public Reservation add(Reservation reservation) {
        // Find the associated flight
        Long flightId = reservation.getFlight().getId();
        Flight flight = flightRepository.findById(flightId).orElseThrow(() ->
                new IllegalArgumentException("Flight with ID " + flightId + " not found"));

        // Check seat availability
        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No available seats for flight " + flight.getFlightNumber());
        }

        // Save the reservation and update available seats
        Reservation savedReservation = reservationRepository.save(reservation);
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        return savedReservation;
    }

    @Override
    public Reservation modify(Reservation reservation) {
        if (reservationRepository.existsById(reservation.getId())) {
            return reservationRepository.save(reservation); // Update reservation if it exists
        }
        return null; // If reservation doesn't exist, return null
    }

    @Override
    public void deleteById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            // Retrieve the associated flight
            Flight flight = reservation.get().getFlight();
            // Increase available seats after deleting the reservation
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            flightRepository.save(flight);

            reservationRepository.deleteById(id); // Delete the reservation
        } else {
            System.out.println("Reservation with ID " + id + " not found.");
        }
    }

    @Override
    public List<Reservation> findByFlightId(Long flightId) {
        return reservationRepository.findAllByFlightId(flightId); // Retrieve reservations for a specific flight
    }

    @Override
    public List<Reservation> findByPersonalId(String personalId) {
        return reservationRepository.findAllByPersonalId(personalId); // Retrieve reservations by personal ID
    }
}
