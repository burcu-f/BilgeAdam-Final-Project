package com.techathome.services;

import com.techathome.entities.Account;
import com.techathome.entities.Cart;
import com.techathome.entities.CartDetail;
import com.techathome.entities.Product;
import com.techathome.repository.AccountRepository;
import com.techathome.repository.CartDetailRepository;
import com.techathome.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
    
    public void addToCart(Long productId, int quantity, String userEmail) {
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
                Cart cart = getUserCart(userEmail);
                if (cart != null) {
                    // Add the product to the cart
                    addToCart(cart, product, quantity);
                } else {
                    throw new RuntimeException("User cart not found"); // Handle this scenario based on your application logic
                }
            } else {
                throw new IllegalArgumentException("Requested quantity exceeds available stock");
            }
        } else {
            throw new IllegalArgumentException("Invalid product or quantity");
        }
    }

    private Cart getUserCart(String userEmail) {
        // Fetch the account from the database
        Account account = accountRepository.findByEmail(userEmail).orElse(null);
        
        // Check if the account exists and if they have a cart
        if (account != null) {
            return account.getCart(); // method to retrieve the user's cart
        } else {
            throw new RuntimeException("Account not found"); // Handle this scenario based on your application logic
        }
    }

    private void addToCart(Cart cart, Product product, int quantity) {
        // Create a new CartDetail instance
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setQuantity(quantity);
        cartDetail.setItemPrice(product.getPrice()); // Assuming the item price is the same as the product price
        cartDetail.setTotalPrice(product.getPrice() * quantity); // Total price is the item price multiplied by quantity

        // Save the CartDetail directly
        cartDetailRepository.save(cartDetail);

     // Update the cart's total price and other properties
        updateCart(cart);
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
            existingCart.setTotalPrice(totalPrice);

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

        Cart userCart = userAccount.getCart();

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
