# banking-api-jpa-postgres

API REST bancaria desarrollada con Spring Boot, JPA/Hibernate, PostgreSQL y Docker. Incluye seeding idempotente y soporte GraphQL.

## 🚀 Tech Stack
- Java 17+ / Spring Boot 3
- Spring Data JPA / Hibernate
- PostgreSQL 16 (Docker)
- GraphQL + REST
- Maven

## ✨ Features
- CRUD de entidades bancarias
- Migración de H2 a PostgreSQL
- Seeding idempotente al iniciar (no duplica datos)
- Contenerización con Docker Compose
- API REST + GraphQL endpoint

## 📁 Estructura
com.banking.api

├── controller

├── service

├── repository

├── entity

└── BankingApiApplication.java



## 🐳 Cómo correrlo
1. Levantar BD:
```bash
docker-compose up -d

2. Correr la app:
./mvnw spring-boot:run

App en: http://localhost:8080
GraphiQL en: http://localhost:8080/graphiql
REST en: http://localhost:8080/api/...

⚙️ Configuración application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/bankingdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.graphql.path=/graphql
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql

📌 Endpoints
Método Endpoint Descripción
GET/api/accountsListar cuentas
POST/api/accountsCrear cuenta
POST/graphqlQuery GraphQL

Hecho con Spring Boot para portafolio Backend.
