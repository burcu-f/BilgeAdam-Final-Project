package com.techathome.dto.auth;

import lombok.Data;

@Data
public class AddressDTO {
    private String country;
    private String city;
    private String addressLine;
    private String postalCode;

}
