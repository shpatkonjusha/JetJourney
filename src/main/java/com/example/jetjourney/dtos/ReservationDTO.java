package com.example.jetjourney.dtos;

import com.example.jetjourney.enums.TicketStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

    @NotNull(message = "Reservation ID cannot be null")
    private Long id;

    @NotEmpty(message = "First name is required")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phone;

    @NotEmpty(message = "Personal ID is required")
    @Size(min = 5, max = 20, message = "Personal ID must be between 5 and 20 characters")
    private String personalId;

    @NotEmpty(message = "Flight number is required")
    private String flightNumber;

    @NotEmpty(message = "Seat number is required")
    @Size(min = 1, max = 10, message = "Seat number must be between 1 and 10 characters")
    private String seatNumber;

    @NotNull(message = "Ticket status cannot be null")
    private TicketStatus status;
}
