package com.techathome.controller;

import com.techathome.entities.Cart;
import com.techathome.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createCart(@RequestBody Cart cart) {
        cartService.saveCart(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Long> request, Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(URI.create("/login")).build(); // Redirect to login page
            }
            
            Long productId = request.get("productId");
            String userEmail = authentication.getName();
            System.out.println("Adding product to cart - Product ID: " + productId + ", User Email: " + userEmail);
            cartService.addToCart(productId, 1, userEmail); // Provide a default quantity and user email
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/")).build();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getCartCount() {
        int count = cartService.getCartItemCount(); // Implement this method in your service
        Map<String, Integer> response = Map.of("count", count);
        return ResponseEntity.ok().body(response);
    }
}
