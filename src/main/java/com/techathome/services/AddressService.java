package com.techathome.services;

import com.techathome.entities.Address;
import com.techathome.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Integer addressId) {
        return addressRepository.findById(addressId).orElse(null);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

	public Address getAddressByAccountId(Long accountId) {
		return addressRepository.findByAccountAccountId(accountId).orElse(null);
	}
}
