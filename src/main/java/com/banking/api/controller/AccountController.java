package com.banking.api.controller;

import com.banking.api.dto.AccountResponseDto;
import com.banking.api.entity.Account;
import com.banking.api.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
@Tag(name = "Banking Core API", description = "Operaciones bancarias principales - JPA + Postgres")
public class AccountController {
    private final BankService bankService;

    @Operation(summary = "Listar todas las cuentas", description = "Obtiene todas las cuentas con saldo y sucursal")
    @ApiResponse(responseCode = "200", description = "Cuentas obtenidas correctamente")
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> all() {
        return ResponseEntity.ok(bankService.findAll());
    }

    @Operation(summary = "Depositar fondos",
            description = "Deposita monto a cuenta existente con validación de saldo mínimo y auditoría transaccional")
    @ApiResponse(responseCode = "200", description = "Depósito exitoso")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    @ApiResponse(responseCode = "400", description = "Monto inválido")
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(bankService.deposit(id, amount));
    }

    @Operation(summary = "Retirar fondos",
            description = "Retira monto con validación de fondos suficientes y bloqueo pesimista")
    @ApiResponse(responseCode = "200", description = "Retiro exitoso")
    @ApiResponse(responseCode = "400", description = "Fondos insuficientes")
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable String id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(bankService.withdraw(id, amount));
    }

    @Operation(summary = "Crear cuenta bancaria",
            description = "Crea cuenta con validación de saldo inicial mínimo $100 MXN y generación de CLABE")
    @ApiResponse(responseCode = "201", description = "Cuenta creada")
    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return ResponseEntity.status(201).body(bankService.create(account));
    }
}