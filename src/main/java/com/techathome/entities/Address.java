package com.techathome.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    private Long addressId;

    
    private String city;
    private String district;
    private String addressLine;
    

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Account> accounts;

}
