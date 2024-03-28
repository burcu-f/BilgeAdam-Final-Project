package com.techathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techathome.entities.Account;
import com.techathome.entities.Cart;
import com.techathome.entities.CartDetail;
import com.techathome.entities.Product;
import com.techathome.repository.AccountRepository;
import com.techathome.repository.CartDetailRepository;
import com.techathome.repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CartDetailRepository cartDetailRepository;
    

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
    
    public Cart addToCart(Long productId, int quantity, Account account) {
        // Fetch the product
        Product product = productService.getProductById(productId);
        
        // Check if the product exists and if the requested quantity is valid
        if (product != null && quantity > 0) {
            // Check if the requested quantity exceeds the available stock
            if (product.getStock() >= quantity) {
                // Decrement the product's stock by the requested quantity
                product.decrementStock(quantity);
                
                // Update the product's stock in the database
                productService.saveProduct(product);
                
                // Proceed with adding the product to the cart
                Cart cart = getUserCart(account.getAccountId()).orElse(null);
                if (cart == null) {
                	cart = new Cart();
                	cart.setAccount(account);
                	cart = saveCart(cart);  
                }
             // Add the product to the cart
                addToCart(cart, product, quantity);
                return cartRepository.findById(cart.getCartId()).orElseThrow();
            } else {
                throw new IllegalArgumentException("Requested quantity exceeds available stock");
            }
        } else {
            throw new IllegalArgumentException("Invalid product or quantity");
        }
    }

    private Optional<Cart> getUserCart(Long accountId) {
    	return cartRepository.findByAccountAccountId(accountId);
    }

    private Cart addToCart(Cart cart, Product product, int quantity) {
    	CartDetail cartDetail = 
    			cartDetailRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId()).orElse(null);
    	
    	if (cartDetail == null) {
    		// Create a new CartDetail instance
    		cartDetail = new CartDetail();
    		cartDetail.setCart(cart);
    		cartDetail.setProduct(product);
    		cartDetail.setQuantity(quantity);
    		cartDetail.setItemPrice(product.getPrice()); // Assuming the item price is the same as the product price
    		cartDetail.setTotalPrice(product.getPrice() * quantity); // Total price is the item price multiplied by quantity
    	} else {
    		cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
    	}
    	cartDetailRepository.save(cartDetail);
    	
    	return cart;
    }

    private void updateCart(Cart cart) {
        // Retrieve the cart from the database (if needed)
        Cart existingCart = cartRepository.findById(cart.getCartId()).orElse(null);
        if (existingCart != null) {
            // Perform any necessary updates to the cart, such as recalculating the total price
            // Here, we calculate the total price by summing the total prices of all cart items
            double totalPrice = existingCart.getCartDetails().stream()
                                    .mapToDouble(CartDetail::getTotalPrice)
                                    .sum();
            
            // Update the cart's total price
//            existingCart.setTotalPrice(totalPrice);

            // Save the updated cart back to the database
            cartRepository.save(existingCart);
        }
    }
    
    public int getCartItemCount() {
        // Retrieve the currently authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Find the user's cart by their email
        Account userAccount = accountRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart userCart = null;

        // Calculate the total number of items in the cart
        int itemCount = 0;
        if (userCart != null) {
            for (CartDetail cartDetail : userCart.getCartDetails()) {
                itemCount += cartDetail.getQuantity();
            }
        }

        return itemCount;
    }
}
