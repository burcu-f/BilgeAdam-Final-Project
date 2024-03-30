package com.techathome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.entities.Account;
import com.techathome.services.AccountService;

@Controller
public class ProfileController {
	@Autowired
    private AccountService accountService; 

    @GetMapping("/profile/{accountId}")
    public ModelAndView userProfilePage(@PathVariable Long accountId) {
        ModelAndView modelAndView = new ModelAndView("profile");

        // Retrieve user information based on accountId
        Account account = accountService.getAccountById(accountId);
        
        // Add user information to the model
        modelAndView.addObject("account", account);

        return modelAndView;
    }

}
