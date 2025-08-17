# Hibernate Product CRUD

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Hibernate](https://img.shields.io/badge/Hibernate-5.6.10-green)](https://hibernate.org/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

A console-based product management application built using Java, JPA/Hibernate, and MariaDB.

## Features

- Create, read, update, and delete products
- Search products by ID or partial name
- List products ordered by price
- Bulk price updates by percentage
- Data validation with custom exceptions
- Database persistence with JPA/Hibernate

## Tech Stack

- **Java 21** - Programming language
- **JPA/Hibernate** - ORM framework
- **Maven** - Dependency management
- **MariaDB** - Production database
- **H2** - In-memory test database
- **JUnit 5 and Mockito** - Unit testing

## Architecture

The project follows a layered architecture pattern:

```text
.
├── main
│   ├── java
│   │   └── br
│   │       └── com
│   │           └── shiroshima
│   │               ├── controller
│   │               ├── exception
│   │               ├── model
│   │               ├── repository
│   │               └── service
│   └── resources
│       └── META-INF
└── test
    ├── java
    │   └── br
    │       └── com
    │           └── shiroshima
    │               ├── controller
    │               ├── repository
    │               └── service
    └── resources
        └── META-INF
```

**Architecture Layers:**

- **Controller** - Handles user input/output and menu navigation
- **Service** - Contains business logic and validation rules
- **Repository (DAO)** - Manages database operations with JPA/Hibernate
- **Model** - Entity classes with JPA annotations
- **Exception** - Custom exception handling for business rules

## Running

### Prerequisites

- Java 21+
- Maven 3.6+
- Docker (for running MariaDB)

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ahvShiro/hibernate-product-crud.git && cd hibernate-product-crud
   ```

2. **Set up the database with Docker:**
   ```bash
   docker compose up -d
   ```

3. **Build the project:**
   ```bash
   mvn clean compile
   ```

4. **Run the application:**
   ```bash
   mvn exec:java -Dexec.mainClass="br.com.shiroshima.Main"
   ```

## Functionalities

1. **Create Product** - Add new products with comprehensive validation
2. **List Products by Price** - Display all products ordered by price (ascending)
3. **Search by ID** - Find specific product by unique identifier
4. **Search by Name (Partial)** - Find products using partial name matching 
5. **Update Product Price by Percentage** - Apply percentage increase/decrease to individual products 
6. **Update All Products Price by Percentage** - Bulk price updates across all products 
7. **Delete Product** - Remove products with confirmation prompt

## Future Steps

- Implementing comprehensive tests for the repository, service, and controller layers
- Adding a graphical user interface
- Supporting product categories and inventory management
- Implementing user authentication and authorization

## License

This project is open source and available under the [MIT License](LICENSE).
