package org.example.lab6_1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.example.lab6_1.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Custom query methods can be added here if needed
}
