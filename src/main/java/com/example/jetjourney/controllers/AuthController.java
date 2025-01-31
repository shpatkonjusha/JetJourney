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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
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
            return "redirect:/login";
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
                            HttpServletRequest request,
                            HttpServletResponse response,
                            Model model, HttpSession httpSession) {
        try {
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();

            User user = userService.findEntityByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            // Validate the password directly (no encoding)
            if (!userService.validatePassword(password, user.getPassword())) {
                throw new WrongPasswordException("Incorrect password.");
            }
            Cookie cookie = new Cookie("userRole", user.getId().toString());

            if (loginRequestDto.isRememberMe()) {
                cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
            } else {
                cookie.setMaxAge(60*60); // 1 hour
            }

            // Set user role in the cookie
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            // Redirect based on user role
            if (session.getAttribute("role").equals("ADMIN"))  {
                session.setAttribute("isAuthenticatedAdmin", true);
                return "redirect:/";  // Admin redirection

            } else if (session.getAttribute("role").equals("CLIENT")) {
                session.setAttribute("isAuthenticatedClient", true);
                return "redirect:/client";  // Client redirection
            } else {
                boolean isAuthenticated = false;

                model.addAttribute("error", "Unknown role.");
                return "auths/login";  // Return to login if role is unknown
            }

        } catch (UserNotFoundException | WrongPasswordException e) {
            model.addAttribute("error", e.getMessage());
            return "/auths/login";  // Return to login with error message
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

/*

@Controller
public class HomeController {
    private boolean isAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userRole".equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }


}
*/

