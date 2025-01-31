package com.example.jetjourney.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, HttpSession session) {
        boolean isAuthenticated = false;
        String userRole = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("userRole".equals(cookie.getName())) {
                    userRole = cookie.getValue();
                    isAuthenticated = true;
                    break;
                }
            }
        }

        // Nëse nuk ka cookie ose sesioni nuk është aktiv, ridrejto te login
        if (!isAuthenticated && session.getAttribute("user") == null) {
            return "redirect:/login";
        }if (isAuthenticated && session.getAttribute("role").equals("ADMIN")) {
            return "index";
        }else {
            return "redirect:/client";
        }
        // Nëse cookie është aktiv, dërgoje te faqja kryesore
    }
}



