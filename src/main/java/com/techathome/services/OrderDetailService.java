package com.techathome.services;

import com.techathome.entities.OrderDetail;
import com.techathome.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }
    
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public OrderDetail getOrderDetailById(Long orderDetailId) {
        return orderDetailRepository.findById(orderDetailId).orElse(null);
    }

    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
    
    public OrderDetail updateOrderDetail(Long orderDetailId, OrderDetail updatedOrderDetail) {
        // Retrieve the existing order detail from the database
    	OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailId).
        		orElseThrow(() -> new IllegalArgumentException("Order detail not found"));
        		

        	// Update the order detail with the provided information
     
    	existingOrderDetail.setOrder(updatedOrderDetail.getOrder());
    	existingOrderDetail.setProduct(updatedOrderDetail.getProduct());
    	existingOrderDetail.setQuantity(updatedOrderDetail.getQuantity());
    	existingOrderDetail.setItemPrice(updatedOrderDetail.getItemPrice());
    	//existingOrderDetail.setTotalPrice(updatedOrderDetail.getTotalPrice());
           

            // Save the updated order detail to the database
            return orderDetailRepository.save(existingOrderDetail);
            
	}
    
    public void deleteOrderDetail(Long orderDetailId) {
    	OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow();
    	orderDetailRepository.delete(orderDetail);
		
    	orderDetailRepository.deleteById(orderDetailId);
    }
}
