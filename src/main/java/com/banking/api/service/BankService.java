package com.banking.api.service;

import com.banking.api.dto.AccountResponseDto;
import com.banking.api.entity.Account;
import com.banking.api.exception.AccountNotFoundException;
import com.banking.api.exception.InsufficientBalanceException;
import com.banking.api.mapper.AccountMapper;
import com.banking.api.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BankService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional(readOnly = true)
    public List<AccountResponseDto> findAll() {
        return accountRepository.findAllWithCustomer()
                .stream().map(accountMapper::toDto)
                .toList();
    }

    @Transactional
    public AccountResponseDto deposit(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto debe ser mayor a 0");
        }
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return  accountMapper.toDto(account);
    }

    @Transactional
    public AccountResponseDto withdraw(String id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto debe ser mayor a 0");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + id));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Fondos insuficientes. Saldo: " + account.getBalance());
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountResponseDto create(Account account) {
        if (account.getBalance() == null || account.getBalance().compareTo(new BigDecimal("100")) < 0) {
            throw new InsufficientBalanceException("Saldo inicial mínimo $100 MXN");
        }
        // Aquí se generaría CLABE, por ahora guardamos directo
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }
}