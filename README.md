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