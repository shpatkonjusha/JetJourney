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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private boolean isAuthenticated(HttpServletRequest request, HttpSession session) {
        boolean isAuthenticated = false;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("userRole".equals(cookie.getName()) && "CLIENT".equals(session.getAttribute("role"))) {
                    isAuthenticated = true;
                    break;
                }
            }
        }

        return isAuthenticated;
    }

    @GetMapping("")
    public String flights(HttpServletRequest request, HttpSession session, Model model) {
        if (!isAuthenticated(request, session)) {
            return "redirect:/login";
        }

        model.addAttribute("flights", flightService.findAll());
        return "client/list";
    }

    @GetMapping("{flightId}/create")
    public String createReservationForm(@PathVariable Long flightId, HttpServletRequest request, HttpSession session, Model model) {
        if (!isAuthenticated(request, session)) {
            return "redirect:/login";
        }

        Optional<Flight> flight = flightService.findById(flightId);
        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());
            model.addAttribute("reservation", new Reservation());
            return "client/create";
        } else {
            return "redirect:/client";
        }
    }

    @PostMapping("{flightId}/create")
    public String createReservation(@PathVariable Long flightId, @Valid Reservation reservation,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    HttpServletRequest request, HttpSession session, Model model) {
        if (!isAuthenticated(request, session)) {
            return "redirect:/login";
        }

        Optional<Flight> flight = flightService.findById(flightId);
        if (!flight.isPresent()) {
            return "redirect:/flights";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("flight_id", flightId);
            model.addAttribute("flight", flight.get());
            return "client/create";
        }

        reservation.setFlight(flight.get());
        reservationService.add(reservation);
        return "redirect:/client";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, HttpServletRequest request, HttpSession session, Model model) {
        if (!isAuthenticated(request, session)) {
            return "redirect:/login";
        }

        Optional<Flight> flight = flightService.findById(id);
        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());
            return "client/details";
        } else {
            return "redirect:/client";
        }
    }
}
