package com.example.jetjourney.controllers;

import com.example.jetjourney.dtos.LoginRequestDto;
import com.example.jetjourney.dtos.UserRegistrationDto;
import com.example.jetjourney.exceptions.EmailExistException;
import com.example.jetjourney.exceptions.UserNameExistException;
import com.example.jetjourney.exceptions.UserNotFoundException;
import com.example.jetjourney.exceptions.WrongPasswordException;
import com.example.jetjourney.models.User;
import com.example.jetjourney.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auths")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "auths/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegistrationDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auths/register";
        }
        try {
            userService.register(userDto, userDto.getPassword());
            return "redirect:/auths/login";
        } catch (UserNameExistException | EmailExistException e) {
            model.addAttribute("error", e.getMessage());
            return "auths/register";
        }
    }

    // Get method to display login form with the empty DTO object
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "auths/login";  // Make sure this path matches your actual template location
    }

    // Post method to handle login
    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequestDto loginRequestDto,
                            HttpServletResponse response,
                            Model model) {
        try {
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();

            User user = userService.findEntityByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            // Validate the password directly (no encoding)
            if (!userService.validatePassword(password, user.getPassword())) {
                throw new WrongPasswordException("Incorrect password.");
            }

            // Set user role in the cookie
            Cookie roleCookie = new Cookie("userRole", user.getRole());
            roleCookie.setPath("/");
            roleCookie.setHttpOnly(true);
            roleCookie.setSecure(true);
            roleCookie.setMaxAge(3600);  // 1 hour expiration
            response.addCookie(roleCookie);

            // Redirect based on user role
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/";  // Admin redirection
            } else if ("CLIENT".equalsIgnoreCase(user.getRole())) {
                return "redirect:/client/dashboard";  // Client redirection
            } else {
                model.addAttribute("error", "Unknown role.");
                return "auths/login";  // Return to login if role is unknown
            }

        } catch (UserNotFoundException | WrongPasswordException e) {
            model.addAttribute("error", e.getMessage());
            return "auths/login";  // Return to login with error message
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("userRole", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/auths/login";
    }
}
