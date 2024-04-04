package com.techathome.repository;

import com.techathome.entities.CartDetail;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository <CartDetail, Long> {

	Optional<CartDetail> findByCartCartIdAndProductProductId(Long cartId, Long productId);

	void deleteByCartCartId(Long cartId);
}