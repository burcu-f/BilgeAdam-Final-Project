package com.techathome.controller;

import com.techathome.entities.CartDetail;
import com.techathome.services.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-details")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;

    @GetMapping("")
    public ResponseEntity<List<CartDetail>> getAllCartDetails() {
        List<CartDetail> cartDetails = cartDetailService.getAllCartDetails();
        return new ResponseEntity<>(cartDetails, HttpStatus.OK);
    }

    @GetMapping("/cart-details/{cartDetailId}")
    public ResponseEntity<CartDetail> getCartDetailById(@PathVariable Integer cartDetailId) {
        CartDetail cartDetail = cartDetailService.getCartDetailById(cartDetailId);
        return new ResponseEntity<>(cartDetail, HttpStatus.OK);
    }

    @PostMapping("/cart-details")
    public ResponseEntity<Void> createCartDetail(@RequestBody CartDetail cartDetail) {
        cartDetailService.saveCartDetail(cartDetail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
