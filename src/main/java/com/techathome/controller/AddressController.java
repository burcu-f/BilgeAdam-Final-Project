package com.techathome.controller;

import com.techathome.config.UserInfoUserDetails;
import com.techathome.entities.Account;
import com.techathome.entities.Address;
import com.techathome.services.AccountService;
import com.techathome.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("")
    public ModelAndView showAddressPage() {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("address");
        return modelAndView;
    }

    @GetMapping("/user")
    public ResponseEntity<Address> getUserAddress(Authentication authentication) {
        try {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            Address address = userDetails.getAccount().getAddress(); // Assuming the address is associated with the account
            return ResponseEntity.ok().body(address);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/saveAddress")
    public ResponseEntity<Void> saveUserAddress(@RequestBody Address address, Authentication authentication) {
        try {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            Long accountId = userDetails.getAccount().getAccountId();
            accountService.saveUserAddress(accountId, address);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
