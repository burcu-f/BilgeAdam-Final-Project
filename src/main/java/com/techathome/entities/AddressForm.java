package com.techathome.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class AddressForm {
    private Long id;
    
    private String city;
    private String district;
    private String addressLine;

}
