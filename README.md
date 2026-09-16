# Banking API - Spring Boot + JPA + Postgres

Production-ready banking API built with Java 17, Spring Boot 3, Spring Data JPA and Postgres 15.

This project is used as a reference implementation for banking modernization and high-integrity transactions.

## Stack
- Java 17, Spring Boot 3.2
- Spring Data JPA / Hibernate
- PostgreSQL 15
- Docker & Docker Compose
- JUnit 5 / Testcontainers

## Key Features
- ACID transactions with @Transactional
- Pessimistic locking for concurrent deposits/withdrawals
- Optimized query for account statements
- Global exception handling
- Docker one-command run

## How to Run
```bash
docker-compose up --build

API: http://localhost:8080
Swagger: http://localhost:8080/swagger-ui.htmlEndpointsPOST /account - create accountGET /account/{number} - get accountPUT /account/deposit/{number}/{amount}PUT /account/withdraw/{number}/{amount}Why this project?Designed to demonstrate skills required for legacy banking systems: transaction integrity, Postgres performance, and Dockerized deployment.