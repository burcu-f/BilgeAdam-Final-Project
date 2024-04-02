package com.techathome.services;

import com.techathome.dto.LoginDTO;
import com.techathome.dto.SignInRequest;
import com.techathome.dto.SignUpRequest;
import com.techathome.entities.Account;
import com.techathome.enums.AccountType;
import com.techathome.repository.AccountRepository;
import com.techathome.repository.AddressRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.techathome.entities.Address;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AccountRepository accountRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        Objects.requireNonNull(accountRepository, "AccountRepository must not be null");
        Objects.requireNonNull(addressRepository, "AddressRepository must not be null");
        Objects.requireNonNull(passwordEncoder, "PasswordEncoder must not be null");
        Objects.requireNonNull(jwtService, "JwtService must not be null");
        Objects.requireNonNull(authenticationManager, "AuthenticationManager must not be null");

        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public LoginDTO signup(SignUpRequest request) {
        var user = Account.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountType(AccountType.CUSTOMER)  // Setting AccountType
                .build();

        var address = Address.builder()
                .addressLine(request.getAddress().getAddressLine())
                .city(request.getAddress().getCity())
                .district(request.getAddress().getDistrict())
                //.postalCode(request.getAddress().getPostalCode())
                .build();

        address = addressRepository.save(address); // Save address and update reference
        user.setAddress(address); // Set the address for the user
        accountRepository.save(user); // Save the user

        var jwt = jwtService.generateToken(user);
        return LoginDTO.builder().fullName(user.getFullName()).token(jwt).build();
    }

    public LoginDTO signIn(SignInRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(request.getEmail());
        Account user = optionalAccount.orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword()));

        // Generate JWT token for the authenticated user
        var jwt = jwtService.generateToken(user);

        return LoginDTO.builder().fullName(user.getFullName()).token(jwt).build();
    }
}
