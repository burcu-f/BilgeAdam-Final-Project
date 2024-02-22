package com.techathome.services;

import com.techathome.entities.Cart;
import com.techathome.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
