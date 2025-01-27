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
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final FlightService flightService;
    public ReservationController(ReservationService reservationService, FlightService flightService) {
        this.reservationService = reservationService;
        this.flightService = flightService;
    }

    // Display all reservations
    @GetMapping("")
    public String reservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/list";
    }

    // Display form to create a new reservation

    // Display form to create a new reservation
    @GetMapping("{flightId}/create")
    public String createReservationForm(@PathVariable Long flightId, Model model) {
        Optional<Flight> flight = flightService.findById(flightId);

        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());
            model.addAttribute("reservation", new Reservation());
            return "reservations/create"; // Correct template for form
        } else {
            return "redirect:/flights"; // Redirect if flight not found
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
            return "reservations/create";
        }

        reservation.setFlight(flight.get()); // Set flight before saving
        reservationService.add(reservation); // Save the reservation, ID will be generated here
        return "redirect:/reservations";
    }


    // Edit a specific reservation by ID
    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "reservations/edit";
    }

    // Handle POST request to edit a specific reservation
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Valid Reservation reservation, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reservation", reservation);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservation", bindingResult);
            return "redirect:/reservations/edit/" + id;
        }
        reservationService.modify(reservation);
        return "redirect:/reservations";
    }

    // Delete a specific reservation by ID
    @GetMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return "redirect:/reservations";
    }

    // View details of a specific reservation
    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("reservation", reservationService.findById(id));
        return "reservations/details";
    }
}
