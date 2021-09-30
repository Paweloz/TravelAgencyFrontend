package com.travel.agency.service;

import com.travel.agency.client.CustomerClient;
import com.travel.agency.domain.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {
    private final CustomerClient customerClient;

    @Override
    public CustomerDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerClient.getCustomerByName(username);
    }

    public Boolean existByName(String name) {
        return customerClient.checkIfExists(name);
    }

    public Boolean saveNewCustomer(CustomerDto customerDto) {
        return customerClient.saveCustomerInDB(customerDto);
    }
}
