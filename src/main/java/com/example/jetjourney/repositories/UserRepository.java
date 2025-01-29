package com.example.jetjourney.repositories;

import com.example.jetjourney.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(String role); // Kjo mund të përdoret për të kontrolluar rolin "ADMIN"
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
