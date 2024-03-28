package com.techathome.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techathome.config.IMapper;
import com.techathome.config.UserInfoUserDetails;
import com.techathome.dto.AddToCartPayload;
import com.techathome.entities.Account;
import com.techathome.entities.Cart;
import com.techathome.entities.CartForm;
import com.techathome.services.CartService;

@Controller
@RequestMapping("/carts")
public class CartController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private CartService cartService;

    @Autowired
    private IMapper mapper;
    
    @GetMapping("")
    public ResponseEntity<List<CartForm>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        List<CartForm> formList = carts.stream().map(c -> mapper.fromCartEntity(c)).toList();
        return new ResponseEntity<>(formList, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartForm> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        CartForm cartForm = mapper.fromCartEntity(cart);
        return new ResponseEntity<>(cartForm, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartForm> createCart(@RequestBody Cart cart) {
        cartService.saveCart(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/add")
    public ResponseEntity<CartForm> addToCart(@RequestBody AddToCartPayload payload, Authentication authentication) {
    	logger.info("Adding product to cart. Product ID: {}", payload.getProductId());
        try {
            UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
            Account account = user.getAccount();
            Cart cart = cartService.addToCart(payload.getProductId(), payload.getQuantity(), account);
            CartForm cartForm = mapper.fromCartEntity(cart);
            return ResponseEntity.status(HttpStatus.OK).body(cartForm);
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
