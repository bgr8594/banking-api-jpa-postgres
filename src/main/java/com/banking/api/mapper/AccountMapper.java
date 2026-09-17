package com.banking.api.mapper;

import com.banking.api.dto.AccountResponseDto;
import com.banking.api.dto.CustomerDto;
import com.banking.api.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponseDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        CustomerDto customerDto = null;
        if (account.getCustomer() != null) {
            customerDto = new CustomerDto(
                    account.getCustomer().getId(),
                    account.getCustomer().getFullName(),
                    account.getCustomer().getEmail(),
                    account.getCustomer().getNationalId()
            );
        }
// Convertir el Enum a String usando .name()
        String accountTypeStr = account.getAccountType() != null
                ? account.getAccountType().name()
                : null;
        return new AccountResponseDto(
                account.getAccountNumber(),
                accountTypeStr, // Account.AccountType
                account.getBalance(),
                customerDto
        );
    }
}