package com.techathome.controller;

import com.techathome.entities.Address;
import com.techathome.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    
    @GetMapping("")
    public ModelAndView showAddressPage() {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("address");
        return modelAndView;
    }

	/*
	 * @GetMapping("") public ResponseEntity<List<Address>> getAllAddresses() {
	 * List<Address> addresses = addressService.getAllAddresses(); return new
	 * ResponseEntity<>(addresses, HttpStatus.OK); }
	 */

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer addressId) {
        Address address = addressService.getAddressById(addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<Void> createAddress(@RequestBody Address address) {
        addressService.saveAddress(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
