package com.techathome.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(generator = "address_id_generator")
    @SequenceGenerator(name = "address_id_generator", sequenceName = "address_id_seq", allocationSize = 1)
    private Long id;
    
    private String city;
    private String district;
    private String addressLine;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
