package com.banking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura 404 - Recursos no encontrados
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, 
                ex.getMessage()
        );
        problem.setTitle("Recurso no encontrado");
        problem.setType(URI.create("https://api.banking.com/errors/not-found"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // Captura 400 - Reglas de negocio/Saldos
    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientBalance(InsufficientBalanceException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, 
                ex.getMessage()
        );
        problem.setTitle("Transacción no permitida");
        problem.setType(URI.create("https://api.banking.com/errors/bad-request"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // Captura 500 - Cualquier error no controlado (Fallback)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUncaughtException(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Ocurrió un error interno en el servidor"
        );
        problem.setTitle("Error Interno del Servidor");
        problem.setType(URI.create("https://api.banking.com/errors/internal-error"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}