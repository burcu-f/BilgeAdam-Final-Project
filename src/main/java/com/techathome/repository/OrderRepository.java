package com.techathome.repository;

import com.techathome.entities.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {
	List<Order> findByAccountAccountId(Long accountId);

	Optional<Order> findByAccountAccountIdAndId(Long accountId, Long id);

	
	
}
