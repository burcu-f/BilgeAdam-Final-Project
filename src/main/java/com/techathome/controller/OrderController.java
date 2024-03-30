package com.techathome.controller;



import com.techathome.entities.TOrder;
import com.techathome.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/admin/order-management")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping("")
    public ModelAndView orderManagementPage() {
        ModelAndView modelAndView = new ModelAndView("order-management");
        modelAndView.addObject("pageTitle", "Order Management");
        List<TOrder> orders = orderService.getAllOrders();
        modelAndView.addObject("orders", orders); // Add orders to the model
        return modelAndView;
    }

 // Method to retrieve all orders
    @GetMapping("/orders")
    public ResponseEntity<List<TOrder>> getAllOrders() {
        List<TOrder> orders = orderService.getAllOrders();
        if (!orders.isEmpty()) {
        	return ResponseEntity.ok(orders);
        } else {
        return ResponseEntity.noContent().build();
        }
    }

    
 // Method to retrieve a specific order by its ID
    @GetMapping("/{orderId}")
    public ResponseEntity<TOrder> getOrderById(@PathVariable("orderId") Long orderId) {
        TOrder order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok().body(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Method to retrieve orders by some other criteria (e.g., accountId)
   
	/*
	 * @GetMapping("/search-order") public ResponseEntity<Optional<TOrder>>
	 * searchOrders(@RequestParam("account") Long accountId) { Optional<TOrder>
	 * order = orderService.getOrderByAccountId(accountId); if (order.isPresent()) {
	 * return ResponseEntity.ok().body(order); } else { return
	 * ResponseEntity.notFound().build(); }
	 * 
	 * }
	 */

 // Method to create a new order
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TOrder> createOrder(@RequestBody TOrder order) {
        TOrder savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok().body(savedOrder);
    }
    
 // Method to update an existing order
    @PutMapping("/{orderId}")
    public ResponseEntity<TOrder> updateOrder(@PathVariable("orderId") Long orderId,
    											@RequestBody TOrder updatedOrder) {
    	TOrder order = orderService.updateOrder(orderId, updatedOrder);
    	return ResponseEntity.ok().body(order);
    }
    
    
 // Method to delete an existing order
    
    @DeleteMapping("/{orderId}")
    @Transactional
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
    	orderService.deleteOrder(orderId);
    	return ResponseEntity.noContent().build();   	
    }
 // Method to retrieve orders by account ID
    @GetMapping("/orders/{accountId}")
    public ResponseEntity<List<TOrder>> getOrdersByAccountId(@PathVariable Long accountId) {
        List<TOrder> orders = orderService.getOrdersByAccountId(accountId);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.noContent().build();
        }
    }  
    
}
