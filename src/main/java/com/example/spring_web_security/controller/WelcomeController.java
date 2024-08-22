package com.example.spring_web_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping
    public String welcome() {
        return "Welcome to My Spring Boot Web API";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String users() {
        return "Authorized user";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public String managers() {
        return "Authorized admin";
    }
}