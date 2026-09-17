package com.banking.api.exception;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(String accountNumber) {
        super("No se encontró la cuenta con el número: " + accountNumber);
    }
}