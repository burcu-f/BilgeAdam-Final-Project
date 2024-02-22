package com.techathome.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public String showRegisterPage() {
        return "register"; // "register.html" is located in src/main/resources/templates directory
    }
}
