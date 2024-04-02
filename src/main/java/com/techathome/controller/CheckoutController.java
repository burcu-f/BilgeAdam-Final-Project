package com.techathome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.config.IMapper;
import com.techathome.entities.Account;
import com.techathome.entities.Cart;
import com.techathome.entities.CartForm;
import com.techathome.services.AccountService;
import com.techathome.services.CartService;

@Controller
public class CheckoutController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CartService cartService;
    
    @Autowired
    private IMapper mapper;

    @GetMapping("/checkout")
    public ModelAndView checkoutPage() {
        Account currentUserAccount = getCurrentUserAccount();
        if (currentUserAccount != null) {
            // Logic to populate the checkout page with current user's account information
            ModelAndView modelAndView = new ModelAndView("checkout");
            modelAndView.addObject("currentUserAccount", currentUserAccount);

            // Fetch cart details and add to the model
            Cart cart = cartService.getCartByAccount(currentUserAccount);
            CartForm cartForm = mapper.fromCartEntity(cart); // Assuming you have a mapper to convert Cart to CartForm
            modelAndView.addObject("cartForm", cartForm);

            return modelAndView;
        } else {
            // Handle scenario where user is not authenticated
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", "User not authenticated");
            return modelAndView;
        }
    }

    private Account getCurrentUserAccount() {
        // Retrieve authentication details to get the current user's account
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return accountService.getAccountByEmail(email).orElse(null);
        }
        return null;
    }
}
