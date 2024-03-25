package com.techathome.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.entities.Account;
import com.techathome.enums.AccountType;
import com.techathome.services.AccountService;

@Controller
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
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountId") Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            return ResponseEntity.ok().body(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Method to retrieve accounts by their type (e.g., CUSTOMER or ADMIN)
    @GetMapping("/type/{accountType}")
    public ResponseEntity<List<Account>> getAccountsByType(@PathVariable("accountType") AccountType accountType) {
        List<Account> accounts = accountService.getAccountsByType(accountType);
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
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok().body(savedAccount);
    }


    // Method to update an existing account
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable("accountId") Long accountId,
                                                 @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(accountId, updatedAccount);
        return ResponseEntity.ok().body(account);
    }

    // Method to delete an existing account
    @DeleteMapping("/{accountId}")
    @Transactional
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}