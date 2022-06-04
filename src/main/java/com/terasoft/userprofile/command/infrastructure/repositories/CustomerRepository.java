package com.terasoft.userprofile.command.infrastructure.repositories;

import com.terasoft.userprofile.command.domain.entities.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends UsersRepository<Customer> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUserName(String userName);
}
