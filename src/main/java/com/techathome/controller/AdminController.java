package com.techathome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Gets "admin.html" from src/main/resources/templates directory
    }
}
