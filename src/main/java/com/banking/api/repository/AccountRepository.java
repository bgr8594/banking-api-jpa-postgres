package com.banking.api.repository;

import com.banking.api.entity.Account;
import com.banking.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerIn(List<Customer> customers);

    @Query("SELECT a FROM Account a JOIN FETCH a.customer")
    List<Account> findAllWithCustomer();
}