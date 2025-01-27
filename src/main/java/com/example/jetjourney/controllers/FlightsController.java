package com.example.jetjourney.controllers;

import com.example.jetjourney.models.Flight;
import com.example.jetjourney.models.Reservation;
import com.example.jetjourney.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/flights")
public class FlightsController {
    private final FlightService flightService;

    public FlightsController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("")
    public String flights(Model model) {
        model.addAttribute("flights", flightService.findAll());
        return "flights/list";
    }

    @GetMapping("/create")
    public String add(Model model) {
        model.addAttribute("flight", new Flight());
        return "flights/create";
    }
    @GetMapping("reservation/create")
    public String createReservationForm(Model model) {
        // Assuming you want to offer a list of available flights
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("flights", flightService.findAll());
        return "reservations/create";
    }


    @PostMapping("/create")
    public String add(@Valid Flight flight, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        // Log the ID to verify it's not null
        System.out.println("Flight ID: " + flight.getId());

        flightService.add(flight);
        return "redirect:/flights";
    }


    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("flight", flightService.findById(id));
        return "flights/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Valid Flight flight, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flight", flight);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flight", bindingResult);
            return "redirect:/flights/edit/" + id;
        }
        flightService.modify(flight);
        return "redirect:/flights";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        flightService.deleteById(id);
        return "redirect:/flights";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model) {
        Optional<Flight> flight = flightService.findById(id);

        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());  // Pass flight object to the template
            return "flights/details";
        } else {
            return "redirect:/flights";  // Redirect if flight not found
        }
    }

}
