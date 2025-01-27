package com.example.jetjourney.mappers;

import com.example.jetjourney.dtos.ReservationDTO;
import com.example.jetjourney.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationMapper {

    public static ReservationDTO toDto(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setFirstName(reservation.getFirstName());
        dto.setLastName(reservation.getLastName());
        dto.setEmail(reservation.getEmail());
        dto.setPhone(reservation.getPhone());
        dto.setPersonalId(reservation.getPersonalId());
        dto.setFlightNumber(reservation.getFlight().getFlightNumber()); // Assuming you want the flight number in DTO
        dto.setSeatNumber(reservation.getSeatNumber());
        dto.setStatus(reservation.getStatus());
        return dto;
    }

    public static Reservation toEntity(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setFirstName(dto.getFirstName());
        reservation.setLastName(dto.getLastName());
        reservation.setEmail(dto.getEmail());
        reservation.setPhone(dto.getPhone());
        reservation.setPersonalId(dto.getPersonalId());
        reservation.setSeatNumber(dto.getSeatNumber());
        reservation.setStatus(dto.getStatus());
        // Assuming flight is linked by flightNumber
        // You would fetch the flight from the database based on flightNumber and set it here
        // flightRepository.findByFlightNumber(dto.getFlightNumber())
        return reservation;
    }
}
