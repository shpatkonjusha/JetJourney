package com.example.jetjourney.services.impls;

import com.example.jetjourney.dtos.UserDTO;
import com.example.jetjourney.dtos.UserRegistrationDto;
import com.example.jetjourney.exceptions.EmailExistException;
import com.example.jetjourney.exceptions.UserNameExistException;
import com.example.jetjourney.mappers.UserMapper;
import com.example.jetjourney.models.User;
import com.example.jetjourney.repositories.UserRepository;
import com.example.jetjourney.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);  // Compare raw passwords directly, without encoding
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDTO);
    }

    @Override
    public UserDTO register(UserRegistrationDto userRegistrationDto, String rawPassword) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(userRegistrationDto.getUsername())) {
            throw new UserNameExistException("Username already exists!");
        }
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new EmailExistException("Email already exists!");
        }

        // Save the plain password directly, without encryption (not recommended)
        User user = userMapper.toEntity(userRegistrationDto, rawPassword);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public Optional<User> findEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
