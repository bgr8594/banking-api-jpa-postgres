package com.banking.api.controller;

import com.banking.api.entity.Account;
import com.banking.api.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    private final BankService bankService;

    @GetMapping
    public List<Account> all() { return bankService.findAll(); }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable String id, @RequestParam BigDecimal amount) {
        return bankService.deposit(id, amount);
    }
}