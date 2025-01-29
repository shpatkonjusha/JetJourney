package com.example.jetjourney.controllers;

import com.example.jetjourney.models.Flight;
import com.example.jetjourney.models.Reservation;
import com.example.jetjourney.services.FlightService;
import com.example.jetjourney.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ReservationService reservationService;
    private final FlightService flightService;



    public ClientController(ReservationService reservationService, FlightService flightService) {
        this.reservationService = reservationService;
        this.flightService = flightService;
    }

    // Display all reservations
    @GetMapping("")
    public String flights(Model model) {
        model.addAttribute("flights", flightService.findAll());
        return "client/list";
    }

    // Display form to create a new reservation

    // Display form to create a new reservation
    @GetMapping("{flightId}/create")
    public String createReservationForm(@PathVariable Long flightId, Model model) {
        Optional<Flight> flight = flightService.findById(flightId);

        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());
            model.addAttribute("reservation", new Reservation());
            return "client/create"; // Correct template for form
        } else {
            return "redirect:/client"; // Redirect if flight not found
        }
    }

    // Handle POST request to create a new reservation
    @PostMapping("{flightId}/create")
    public String createReservation(
            @PathVariable Long flightId,
            @Valid Reservation reservation,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        Optional<Flight> flight = flightService.findById(flightId);

        if (!flight.isPresent()) {
            return "redirect:/flights";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("flight_id", flightId);
            model.addAttribute("flight", flight.get());
            return "client/create";
        }

        reservation.setFlight(flight.get()); // Set flight before saving
        reservationService.add(reservation); // Save the reservation, ID will be generated here
        return "redirect:/client";
    }


    // Edit a specific reservation by ID






    // Delete a specific reservation by ID


    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model) {
        Optional<Flight> flight = flightService.findById(id);

        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());  // Pass flight object to the template
            return "client/details";
        } else {
            return "redirect:/client";  // Redirect if flight not found
        }
    }


    // View details of a specific reservation

}
