package com.banking.api.dto;

public record CustomerDto(
    Long id,
    String fullName,
    String email,
    String nationalId
) {}