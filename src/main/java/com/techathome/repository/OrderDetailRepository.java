package com.techathome.repository;

import com.techathome.entities.OrderDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository <OrderDetail, Long> {
	List<OrderDetail> findByOrderOrderId(Long orderId);
}
