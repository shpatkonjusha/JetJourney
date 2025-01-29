package com.example.jetjourney.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters long")
    @NotBlank(message = "Username is not provided, blank or empty")
    private String username;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long")
    @NotBlank(message = "Password is not provided, blank or empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter and one digit")
    private String password;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long")
    @NotBlank(message = "Password is not provided, blank or empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter and one digit")
    private String confirmPassword;



    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    @NotBlank(message = "Name is not provided, blank or empty")
    private String name;

    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters long")
    @NotBlank(message = "Surname is not provided, blank or empty")
    private String surname;



    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters long")
    @NotBlank(message = "Email is not provided, blank or empty")
    @Email(message = "Email is not valid")
    private String email;

    @Size(min = 9, max = 20, message = "Phone must be between 5 and 15 characters long")
    @NotBlank
    private String phone;

    @AssertTrue(message = "You must accept the terms and conditions")
    private boolean acceptTerms;
}
