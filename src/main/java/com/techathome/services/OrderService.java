package com.techathome.services;


import com.techathome.entities.Account;
import com.techathome.entities.Address;
import com.techathome.entities.Cart;
import com.techathome.entities.Order;
import com.techathome.entities.OrderDetail;
import com.techathome.repository.OrderDetailRepository;
import com.techathome.repository.OrderRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private AddressService addressService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    
    public List<Order> getOrdersByAccountId(Long accountId) {
        return orderRepository.findByAccountAccountId(accountId);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    
    public void deleteOrder(Long orderId) {
    	orderRepository.deleteById(orderId);
    }
    
    public Order updateOrder(Long orderId, Order updatedOrder) {
    	Order existingOrder = orderRepository.findById(orderId)
    			.orElseThrow(() -> new IllegalArgumentException("Order not found"));
    	
    	//Update existing order with new values
    	existingOrder.setAccount(updatedOrder.getAccount());
    	//existingOrder.setOrderDate(updatedOrder.getOrderDate());
    	existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
    	existingOrder.setAddress(updatedOrder.getAddress());
    	
    	return orderRepository.save(existingOrder);
    	
    	
    }

    @Transactional
	public Order createOrder(Account account) {
		// mevcut cart'ı çek
		Cart cart = cartService.getCartByAccount(account);
		
		// cart'tan order oluştur
		Order order = new Order();
		order.setAccount(account);
		Address address = addressService.getAddressByAccountId(account.getAccountId());
		order.setAddress(address);
		order.setOrderDate(new Date());
		List<OrderDetail> list = cart.getCartDetails().stream().map(cartDetail -> {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(cartDetail.getProduct());
			orderDetail.setQuantity(cartDetail.getQuantity());
			orderDetail.setItemPrice(cartDetail.getProduct().getPrice());
			orderDetail.setOrder(order);
			return orderDetail;
		}).toList();
		order.setOrderDetails(list);
		
		// kartı kaydet
		saveOrder(order);
		
		// cart'ı boşalt
		cartService.emptyCart(cart.getCartId());
		
		return order;
	}

	public Order getOrderByAccountIdAndId(Long accountId, Long orderId) {
		Order order = orderRepository.findByAccountAccountIdAndId(accountId, orderId).orElse(null);
		if (order != null) {
			List<OrderDetail> details = orderDetailRepository.findByOrderId(orderId);
			order.setOrderDetails(details);
		}
		return order;
	}
}
