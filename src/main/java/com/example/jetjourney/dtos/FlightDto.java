package com.example.jetjourney.dtos;

import com.example.jetjourney.enums.FlightStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class FlightDto {
    @PositiveOrZero(message = "Id must be positive or zero")
    private Long id;

    @NotNull(message = "Receipt number is required")
    @NotBlank(message = "Receipt number is required")
    private String flightNumber;

    @Size(max = 100, message = "Origin must be maximum 100 characters")
    @NotNull(message = "Origin is required")
    @NotBlank(message = "Origin is required")
    private String origin;

    @Size(max = 100, message = "Destination must be maximum 100 characters")
    @NotNull(message = "Destination is required")
    @NotBlank(message = "Destination is required")
    private String destination;

    @Size(max = 100, message = "Departure time must be maximum 100 characters")
    @NotNull(message = "Departure time is required")
    @NotBlank(message = "Departure time is required")
    private String departureTime;

    @Size(max = 100, message = "Arrival time must be maximum 100 characters")
    @NotNull(message = "Arrival time is required")
    @NotBlank(message = "Arrival time is required")
    private String arrivalTime;

    @PositiveOrZero(message = "Price must be positive or zero")
    @NotNull(message = "Price is required")
    private Double price;

    @PositiveOrZero(message = "Available seats must be positive or zero")
    @NotNull(message = "Available seats is required")
    @Min(value = 0, message = "Available seats cannot be negative.")
    private int availableSeats;

    @NotNull(message = "Status is required")
    private FlightStatus status;

}
