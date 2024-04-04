package com.techathome.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.techathome.config.IMapper;
import com.techathome.entities.Account;
import com.techathome.entities.AccountForm;
import com.techathome.enums.AccountType;
import com.techathome.services.AccountService;

@Controller
@RequestMapping("/admin/user-management")
@PreAuthorize("hasRole('ADMIN')")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private IMapper mapper;

    @GetMapping("")
    public ModelAndView userManagementPage() {
        ModelAndView modelAndView = new ModelAndView("user-management");
        modelAndView.addObject("pageTitle", "User Management");
        return modelAndView;
    }

    // Method to retrieve all accounts
    @GetMapping("/list")
    public ResponseEntity<List<AccountForm>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountForm> formList = accounts.stream().map(c -> mapper.fromAccountEntity(c)).toList();
        if (!accounts.isEmpty()) {
            return ResponseEntity.ok().body(formList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Method to retrieve a specific account by its ID
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountForm> getAccountById(@PathVariable("accountId") Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            return ResponseEntity.ok().body(mapper.fromAccountEntity(account));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Method to retrieve accounts by their type (e.g., CUSTOMER or ADMIN)
    @GetMapping("/type/{accountType}")
    public ResponseEntity<List<AccountForm>> getAccountsByType(@PathVariable("accountType") AccountType accountType) {
        List<Account> accounts = accountService.getAccountsByType(accountType);
        List<AccountForm> formList = accounts.stream().map(c -> mapper.fromAccountEntity(c)).toList();
        if (!accounts.isEmpty()) {
            return ResponseEntity.ok().body(formList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Method to retrieve accounts by some other criteria (e.g., email)
    @GetMapping("/search")
    public ResponseEntity<Optional<AccountForm>> searchAccounts(@RequestParam("email") String email) {
        Optional<Account> account = accountService.getAccountByEmail(email);
        if (account.isPresent()) {
        	AccountForm fromAccountEntity = mapper.fromAccountEntity(account.get());
            return ResponseEntity.ok().body(Optional.of(fromAccountEntity));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Method to create a new account
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountForm> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok().body(mapper.fromAccountEntity(savedAccount));
    }


    // Method to update an existing account
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountForm> updateAccount(@PathVariable("accountId") Long accountId,
                                                 @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(accountId, updatedAccount);
        return ResponseEntity.ok().body(mapper.fromAccountEntity(account));
    }

    // Method to delete an existing account
    @DeleteMapping("/{accountId}")
    @Transactional
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
    
//    @GetMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Address> getUserAddress(Authentication authentication) {
//        try {
//            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
//            Address address = userDetails.getAccount().getAddresses().get(0); // Assuming the address is associated with the account
//            return ResponseEntity.ok().body(address);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print stack trace for debugging
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping("/{accountId}/address")
//    public ResponseEntity<Void> saveUserAddress(@PathVariable("accountId") Long accountId,
//                                                 @RequestBody Address address) {
//        accountService.saveUserAddress(accountId, address);
//        return ResponseEntity.ok().build();
//    }
}