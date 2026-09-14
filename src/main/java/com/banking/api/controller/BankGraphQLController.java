package com.banking.api.controller;

import com.banking.api.service.BankService;
import com.banking.api.entity.Account;
import com.banking.api.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class BankGraphQLController {

    private final BankService bankService;

    // --- MAPPINGS DE CONSULTAS (QUERIES) ---

    @QueryMapping
    public List<Customer> findAllCustomers() {
        return bankService.getAllCustomers();
    }

    @QueryMapping
    public Customer findCustomerById(@Argument Long id) {
        return bankService.getCustomerById(id);
    }

    @QueryMapping
    public Account findAccountByNumber(@Argument String accountNumber) {
        return bankService.getAccountByNumber(accountNumber);
    }

    // --- MAPPINGS DE MUTACIONES (MUTATIONS) ---

    @MutationMapping
    public Customer createCustomer(@Argument Map<String, String> input) {
        return bankService.createCustomer(
                input.get("fullName"),
                input.get("email"),
                input.get("nationalId")
        );
    }

    @MutationMapping
    public Account createAccount(@Argument Map<String, Object> input) {
        String accountNumber = (String) input.get("accountNumber");
        Account.AccountType type = Account.AccountType.valueOf((String) input.get("accountType"));
        Long customerId = Long.parseLong(input.get("customerId").toString());

        return bankService.createAccount(accountNumber, type, customerId);
    }

    @MutationMapping
    public Account deposit(@Argument String accountNumber, @Argument Double amount) {
        return bankService.deposit(accountNumber, BigDecimal.valueOf(amount));
    }

    @BatchMapping
    public Map<Customer, List<Account>> accounts(List<Customer> customers) {
        // 1. Se obtiene una lista de clientes completa en la consulta inicial.
        // 2. Se buscan TODAS las cuentas de ESOS clientes en una única consulta SQL.

        // 3. Se agrupan las cuentas por cliente en un Map.
        return bankService.getAccountsForCustomers(customers);
    }
}