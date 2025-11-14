# Library Management System

A Spring Boot–based Library Management System designed to manage books, authors, categories, physical copies, users, and the loan lifecycle.  
This project demonstrates a realistic backend application implementing authentication, search, borrowing workflows, notifications, auditing, and reporting features.

---

## 1. Features

### Core Features
- User registration and login using JWT authentication with refresh token support.
- Role-based access control:
    - Guest: search catalogue
    - Member: reserve, borrow, return books
    - Librarian: manage catalogue, and borrow status. Can also view reports
- CRUD operations for:
    - Authors
    - Books
    - Categories
- Manage physical book copies with statuses:
    - AVAILABLE, RESERVED, BORROWED, LOST, MAINTENANCE
- Borrow workflow: reserve → borrow → return.
- Automatic fine calculation for overdue books.
- Search and filter books by title, author, ISBN, and category with pagination.
- Audit fields on all entities (createdBy, createdAt, updatedBy, updatedAt).
- JSON structured logging using Logstash Logback Encoder.
- Admin reports for:
    - Active Loans
    - Overdue Loans
- Scheduled job for due-soon and overdue notifications.
- Complete OpenAPI (Swagger UI) documentation.

---

## 2. Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL
- Maven
- Docker & Docker Compose
- Springdoc OpenAPI
- Logstash JSON logging

---

## 3. Project Structure (High-Level)

```
src/main/java/com/library/librarymanagement
│
├── controller        # REST controllers
├── service           # Business logic
├── repository        # JPA repositories
├── entity            # JPA entities (Author, Book, BookCopy, Category, Loan, User)
├── dto               # Request/Response DTOs
├── security          # JWT filters, configs
├── common            # Auditing
└── config            # Application configurations

```

---

## 4. Security Model

### Roles
- ROLE_GUEST (unauthenticated)
- ROLE_MEMBER
- ROLE_LIBRARIAN

### Access Overview

| Endpoint Type | Role |
|---------------|------|
| Search books | Guest |
| Reserve / Borrow / Return | Member |
| CRUD for Books / Authors / Categories / Copies | Librarian |
| Reports | Librarian |

JWT + Refresh Token is used for user authentication and session management.

---

## 5. API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 6. Running the Application (Local)

### Prerequisites
- Java 17
- Maven
- PostgreSQL (if not using Docker)

### Run with Maven

```
mvn clean install
mvn spring-boot:run
```

---

## 7. Docker Setup

This project includes both a Dockerfile and docker-compose.yml.

### 7.1 Build Docker Image

```
docker build -t library-management .
```

### 7.2 Run the Application with Docker Compose (Recommended)

```
docker compose up --build
```

### 7.3 Stop Containers

```
docker-compose down
```

### 7.4 View Logs

```
docker logs <container_name>
```

---

## 8. Environment Variables (docker-compose)

```
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/library
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=your-secret-key
```

---

## 9. Scheduled Jobs

A scheduled job runs daily at 3:00 AM to:

- Identify loans that are due the next day
- Identify overdue loans
- Log notification messages
- Update loan statuses to OVERDUE

This is to simulate a real-world notification mechanism without implementing an actual email service.

---

## 10. Reports Implemented

### Active Loans Report
Lists all currently borrowed books.

### Overdue Loans Report
Lists all loans where the due date has passed.

Reports are exposed through the `/api/reports` endpoints.

---

### Access the Postman Collection [here](https://interstellar-water-512757.postman.co/workspace/My-Workspace~ecbdb43f-202e-4f18-8bec-7067bfd89ada/collection/undefined?action=share&creator=28510352).
