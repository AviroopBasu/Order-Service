package com.aviroop.orderservice.service;
import com.aviroop.orderservice.model.Customer;
import com.aviroop.orderservice.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database using the repository
        Optional<Customer> customers = customerRepository.findById(username);
        Customer customer = null;
        if(customers.isPresent()) {
            customer = customers.get();
        }
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }


        return new User(
                customer.getId(),
                customer.getPassword(),
                new ArrayList<>());
    }
}
