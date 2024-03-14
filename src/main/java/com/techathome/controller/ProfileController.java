package com.techathome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
	@GetMapping("/profile")
    public String showProfilePage() {
        // add logic here to retrieve user information from your database
        // and pass it to the profile page
        return "profile";
    }

}
