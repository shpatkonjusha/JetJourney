package com.example.jetjourney.models;

import com.example.jetjourney.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role; // Use enum for the role (ADMIN or CUSTOMER)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservations> reservations;
}
