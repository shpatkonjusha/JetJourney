package com.example.jetjourney.services;

import com.example.jetjourney.dtos.UserDTO;
import com.example.jetjourney.dtos.UserRegistrationDto;
import com.example.jetjourney.models.User;

import java.util.Optional;

public interface UserService {
    UserDTO register(UserRegistrationDto userRegistrationDto, String rawPassword);
    Optional<UserDTO> findByUsername(String username);
    boolean validatePassword(String rawPassword, String encodedPassword);
    Optional<User> findEntityByUsername(String username);
}
