package com.example.jetjourney.mappers;

import com.example.jetjourney.dtos.UserDTO;
import com.example.jetjourney.dtos.UserRegistrationDto;
import com.example.jetjourney.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getEmail(),
                user.getPhone()
        );
    }

    public User toEntity(UserRegistrationDto dto, String encodedPassword) {
        return new User(
                null, // id will be generated automatically
                dto.getUsername(),
                encodedPassword, // Store the encrypted password
                dto.getName(),
                dto.getSurname(),
                "CLIENT", // Default role (can be changed later if needed)
                dto.getEmail(),
                dto.getPhone()
        );
    }
}
