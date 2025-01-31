package com.example.jetjourney.controllers;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, HttpSession session) {
        boolean isAuthenticated = false;

        // Kontrollo nëse ekziston një cookie 'userRole'
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("userRole".equals(cookie.getName())) {
                    isAuthenticated = true;
                    break;
                }
            }
        }

        // Nëse nuk ka cookie ose sesioni nuk është aktiv, ridrejto te login
        if (!isAuthenticated || session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "index"; // Nëse cookie është aktiv, dërgoje te faqja kryesore
    }
}
