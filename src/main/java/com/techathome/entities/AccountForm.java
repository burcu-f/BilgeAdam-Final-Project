package com.techathome.entities;

import com.techathome.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class AccountForm {
    private Long accountId;

    private String name;
    private String surname;
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountType accountType; // Enum for UserType: CUSTOMER, ADMIN
    
    private AddressForm address;

    @Transient
    private String fullName;

    public String getFullName() {
        return this.name + " " + this.surname;
    }

}