package com.techathome.services;

import com.techathome.entities.Account;
import com.techathome.enums.AccountType;
import com.techathome.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
    public List<Account> getAccountsByType(AccountType accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account updateAccount(Integer accountId, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Update the existing account with new values
        existingAccount.setName(updatedAccount.getName());
        existingAccount.setEmail(updatedAccount.getEmail());
        existingAccount.setAccountType(updatedAccount.getAccountType());

        return accountRepository.save(existingAccount);
    }


}
