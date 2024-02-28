package com.techathome.repository;

import com.techathome.entities.Account;
import com.techathome.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account, Integer> {
    Optional<Account> findByEmail(String email);

    List<Account> findByAccountType(AccountType accountType);

	Optional<Account> findByAccountId(Long accountId);

	void deleteByAccountId(Long accountId);
}
