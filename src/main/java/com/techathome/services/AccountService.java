package com.techathome.services;

import com.techathome.entities.Account;
import com.techathome.entities.Address;
import com.techathome.enums.AccountType;
import com.techathome.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAllByOrderByAccountIdAsc();
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findByAccountId(accountId).orElse(null);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account saveAccount(Account account) {
    	String encode = new BCryptPasswordEncoder().encode(account.getPassword());
    	account.setPassword(encode);
        return accountRepository.save(account);
    }
    public List<Account> getAccountsByType(AccountType accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteByAccountId(accountId);
    }

    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existingAccount = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Update the existing account with new values
        existingAccount.setName(updatedAccount.getName());
        existingAccount.setSurname(updatedAccount.getSurname());
        existingAccount.setEmail(updatedAccount.getEmail());
        existingAccount.setAccountType(updatedAccount.getAccountType());

        return accountRepository.save(existingAccount);
    }
    
    public Address getUserAddress(Long accountId) {
        // Assuming you have a method in your repository to fetch the user's address by accountId
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getAddress();
    }

    public void saveUserAddress(Long accountId, Address address) {
        // Assuming you have a method in your repository to save/update the user's address
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setAddress(address);
        accountRepository.save(account);
    }


}
