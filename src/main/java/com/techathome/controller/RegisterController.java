package com.techathome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.entities.Account;
import com.techathome.enums.AccountType;
import com.techathome.services.AccountService;

@RestController
@RequestMapping
public class RegisterController {
	
	@Autowired
	private AccountService accountService;
	
    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody Account account) {
    	account.setAccountType(AccountType.CUSTOMER);
    	accountService.saveAccount(account);
    	return ResponseEntity.noContent().build();
    }
}
