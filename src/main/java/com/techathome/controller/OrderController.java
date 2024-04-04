package com.techathome.controller;



import com.techathome.config.IMapper;
import com.techathome.config.UserInfoUserDetails;
import com.techathome.entities.AccountForm;
import com.techathome.entities.Order;
import com.techathome.entities.OrderForm;
import com.techathome.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private IMapper mapper;
    
    @GetMapping("/admin/order-management")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView orderManagementPage() {
        ModelAndView modelAndView = new ModelAndView("order-management");
        modelAndView.addObject("pageTitle", "Order Management");
        List<Order> orders = orderService.getAllOrders();
        modelAndView.addObject("orders", orders); // Add orders to the model
        return modelAndView;
    }

 // Method to retrieve all orders
    @GetMapping("/admin/order-management/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderForm>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderForm> formList = orders.stream().map(c -> mapper.fromOrderEntity(c)).toList();
        if (!orders.isEmpty()) {
        	return ResponseEntity.ok(formList);
        } else {
        return ResponseEntity.noContent().build();
        }
    }

    
 // Method to retrieve a specific order by its ID
    @GetMapping("/admin/order-management/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderByIdAdmin(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok().body(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 // Method to create a new order
    @PostMapping(value = "/admin/order-management/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok().body(savedOrder);
    }
    
 // Method to update an existing order
    @PutMapping("/admin/order-management/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long orderId,
    											@RequestBody Order updatedOrder) {
    	Order order = orderService.updateOrder(orderId, updatedOrder);
    	return ResponseEntity.ok().body(order);
    }
    
    
 // Method to delete an existing order
    
    @DeleteMapping("/admin/order-management/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
    	orderService.deleteOrder(orderId);
    	return ResponseEntity.noContent().build();   	
    }
 // Method to retrieve orders by account ID
    @GetMapping("/admin/order-management/orders/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrdersByAccountId(@PathVariable Long accountId) {
        List<Order> orders = orderService.getOrdersByAccountId(accountId);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    
    @GetMapping("/order/createOrder")
    public ResponseEntity<OrderForm> createOrder(Authentication authentication) {
    	UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Order order = orderService.createOrder(user.getAccount());
        OrderForm form = mapper.fromOrderEntity(order);
    	return ResponseEntity.ok(form);
    }

    @GetMapping("/order/order-completed/{orderId}")
    public ModelAndView createOrder(@PathVariable Long orderId) {
    	ModelAndView modelAndView = new ModelAndView("redirect:/");
    	modelAndView.addObject("completedOrderId", orderId);
    	return modelAndView;
    }
    
    @GetMapping("/order/getOrderById/{orderId}")
    public ResponseEntity<OrderForm> getOrderById(Authentication authentication, @PathVariable("orderId") Long orderId) {
    	UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Order order = orderService.getOrderByAccountIdAndId(user.getAccount().getAccountId(), orderId);
        OrderForm form = mapper.fromOrderEntity(order);
        return ResponseEntity.ok().body(form);
    }
    
}
