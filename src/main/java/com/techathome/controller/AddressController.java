package com.techathome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.config.IMapper;
import com.techathome.config.UserInfoUserDetails;
import com.techathome.entities.Address;
import com.techathome.entities.AddressForm;
import com.techathome.services.AddressService;



@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private IMapper mapper;
    
    @GetMapping("")
    public ModelAndView showAddressPage() {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("address");
        return modelAndView;
    }

    @GetMapping("/getByAccountId")
    public ResponseEntity<AddressForm> getUserAddress(Authentication authentication) {
        try {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            Address address = addressService.getAddressByAccountId(userDetails.getAccount().getAccountId());
            AddressForm form = mapper.fromAddressEntity(address);
            return ResponseEntity.ok().body(form);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/saveAddress")
    public ResponseEntity<AddressForm> saveUserAddress(@RequestBody Address address, Authentication authentication) {
        try {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            address.setAccount(userDetails.getAccount());
            addressService.saveAddress(address);
            AddressForm form = mapper.fromAddressEntity(address);
            return ResponseEntity.ok().body(form);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
