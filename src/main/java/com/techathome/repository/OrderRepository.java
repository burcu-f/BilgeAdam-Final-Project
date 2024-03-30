package com.techathome.repository;

import com.techathome.entities.TOrder;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <TOrder, Long> {
	List<TOrder> findByAccountId(Long accountId);

	
	
}
