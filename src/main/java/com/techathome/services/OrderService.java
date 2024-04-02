package com.techathome.services;


import com.techathome.entities.TOrder;
import com.techathome.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    public List<TOrder> getOrdersByAccountId(Long accountId) {
        return orderRepository.findByAccountId(accountId);
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
    	//existingOrder.setOrderDate(updatedOrder.getOrderDate());
    	existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
    	existingOrder.setAddress(updatedOrder.getAddress());
    	
    	return orderRepository.save(existingOrder);
    	
    	
    }
}
