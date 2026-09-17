package com.banking.api.service;

import com.banking.api.dto.AccountResponseDto;
import com.banking.api.entity.Account;
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
    public Account deposit(String id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto debe ser mayor a 0");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + id));
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(String id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto debe ser mayor a 0");
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + id));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Fondos insuficientes. Saldo: " + account.getBalance());
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account create(Account account) {
        if (account.getBalance() == null || account.getBalance().compareTo(new BigDecimal("100")) < 0) {
            throw new IllegalArgumentException("Saldo inicial mínimo $100 MXN");
        }
        // Aquí se generaría CLABE, por ahora guardamos directo
        return accountRepository.save(account);
    }
}