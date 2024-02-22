package com.techathome.services;

import com.techathome.entities.CartDetail;
import com.techathome.repository.CartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    public List<CartDetail> getAllCartDetails() {
        return cartDetailRepository.findAll();
    }

    public CartDetail getCartDetailById(Integer cartDetailId) {
        return cartDetailRepository.findById(cartDetailId).orElse(null);
    }

    public void saveCartDetail(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }
}
