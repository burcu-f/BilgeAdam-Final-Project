package com.techathome.repository;

import com.techathome.entities.Address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository <Address, Integer> {
	
	public Optional<Address> findByAccountAccountId(Long accountId);
}
