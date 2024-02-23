package com.techathome.controller;

import com.techathome.entities.Account;
import com.techathome.enums.AccountType;
import com.techathome.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-management")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public ModelAndView userManagementPage() {
        ModelAndView modelAndView = new ModelAndView("user-management");
        modelAndView.addObject("pageTitle", "User Management");
        return modelAndView;
    }

    // Method to retrieve all accounts
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (!accounts.isEmpty()) {
            return ResponseEntity.ok().body(accounts);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Method to retrieve a specific account by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Integer accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            return ResponseEntity.ok().body(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Method to retrieve accounts by their type (e.g., CUSTOMER or ADMIN)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Account>> getAccountsByType(@PathVariable("type") AccountType type) {
        List<Account> accounts = accountService.getAccountsByType(type);
        if (!accounts.isEmpty()) {
            return ResponseEntity.ok().body(accounts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Method to retrieve accounts by some other criteria (e.g., email)
    @GetMapping("/search")
    public ResponseEntity<Optional<Account>> searchAccounts(@RequestParam("email") String email) {
        Optional<Account> account = accountService.getAccountByEmail(email);
        if (account.isPresent()) {
            return ResponseEntity.ok().body(account);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Method to create a new account
    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok().body(savedAccount);
    }

    // Method to update an existing account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Integer accountId,
                                                 @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(accountId, updatedAccount);
        return ResponseEntity.ok().body(account);
    }

    // Method to delete an existing account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Integer accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}