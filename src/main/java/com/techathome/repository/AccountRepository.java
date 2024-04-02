package com.techathome.repository;

import com.techathome.entities.Account;
import com.techathome.entities.Address;
import com.techathome.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account, Integer> {
    Optional<Account> findByEmail(String email);

    List<Account> findByAccountType(AccountType accountType);

	Optional<Account> findByAccountId(Long accountId);

	void deleteByAccountId(Long accountId);
	
	List<Account> findAllByOrderByAccountIdAsc();
	
	// Method to fetch the user's address by accountId
    @Query("SELECT a.address FROM Account a WHERE a.accountId = :accountId")
    Address findAddressByAccountId(@Param("accountId") Long accountId);

    // Method to save/update the user's address
    @Modifying
    @Query("UPDATE Account a SET a.address = :address WHERE a.accountId = :accountId")
    void saveAddressByAccountId(@Param("accountId") Long accountId, @Param("address") Address address);
    
}
