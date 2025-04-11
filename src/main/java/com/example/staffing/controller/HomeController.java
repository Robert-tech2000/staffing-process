package com.example.staffing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Public Staffing Application Home Page");
    }

    @GetMapping("/home-admin")
    public String homeAdmin() {
        return "Staffing Application Admin Home Page";
    }
}
