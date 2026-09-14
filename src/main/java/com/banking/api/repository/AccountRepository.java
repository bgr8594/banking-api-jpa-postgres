package com.banking.api.repository;

import com.banking.api.entity.Account;
import com.banking.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerIn(List<Customer> customers);
}