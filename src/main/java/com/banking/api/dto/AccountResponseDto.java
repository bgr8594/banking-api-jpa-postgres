package com.banking.api.dto;

import java.math.BigDecimal;

public record AccountResponseDto(
    String accountNumber,
    String accountType,
    BigDecimal balance,
    CustomerDto customer
) {}

