package com.techathome.services;


import com.techathome.entities.TOrder;
import com.techathome.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<TOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public TOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    
    public Optional<TOrder> getOrderByAccountId(Long accountId) {
        return orderRepository.findByAccountAccountId(accountId);
    }

    public TOrder saveOrder(TOrder order) {
        return orderRepository.save(order);
    }
    
    public void deleteOrder(Long orderId) {
    	orderRepository.deleteById(orderId);
    }
    
    public TOrder updateOrder(Long orderId, TOrder updatedOrder) {
    	TOrder existingOrder = orderRepository.findById(orderId)
    			.orElseThrow(() -> new IllegalArgumentException("Order not found"));
    	
    	//Update existing order with new values
    	existingOrder.setAccount(updatedOrder.getAccount());
    	existingOrder.setOrderDate(updatedOrder.getOrderDate());
    	existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
    	existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
    	
    	return orderRepository.save(existingOrder);
    	
    	
    }
}
