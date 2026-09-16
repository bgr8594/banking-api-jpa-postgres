package com.banking.api.service;

import com.banking.api.entity.Account;
import com.banking.api.entity.Customer;
import com.banking.api.entity.Transaction;
import com.banking.api.repository.AccountRepository;
import com.banking.api.repository.CustomerRepository;
import com.banking.api.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankService(CustomerRepository customerRepository, 
                       AccountRepository accountRepository, 
                       TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Transactional
    public Customer createCustomer(String fullName, String email, String nationalId) {
        Customer customer = Customer.builder()
                .fullName(fullName)
                .email(email)
                .nationalId(nationalId)
                .build();
        return customerRepository.save(customer);
    }

    @Transactional
    public Account createAccount(String accountNumber, Account.AccountType type, Long customerId) {
        Customer customer = getCustomerById(customerId);
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountType(type)
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));

        Transaction tx = Transaction.builder()
                .amount(amount)
                .type(Transaction.TransactionType.DEPOSIT)
                .timestamp(LocalDateTime.now())
                .account(account)
                .build();

        account.getTransactions().add(tx);
        transactionRepository.save(tx);
        return accountRepository.save(account);
    }

    public Map<Customer, List<Account>> getAccountsForCustomers(List<Customer> customers) {
        List<Account> accounts = accountRepository.findByCustomerIn(customers);
        return accounts.stream()
                .collect(Collectors.groupingBy(Account::getCustomer));
    }
}