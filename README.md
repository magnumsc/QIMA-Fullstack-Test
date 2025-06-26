# Spring Boot Fullstack Challenge

This project was developed as part of a technical challenge focused in back-end development using Spring-Boot framework. The main objective was to create a simple web application for product management with authentication for product administration in a relational database and basic CRUD functionalities.

## ✨ Functionalities

- Product listing with basic product information
- Authenticated options for creation, editing and removal of products
- Cookie based authentication for administrators
- Category structure with hierarchy support
- Relational database integrations using JPA and Hibernate

## 🔧 Used Technologies

- **Gradle**
- **Java 21**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL** (Other RDBMS can be used as well, as long as they are compatible with JPA/Hibernate)
- **Thymeleaf** (used to design and render the HTML screens)
- **Lombok** (used to reduce boilerplate)
- **Bootstrap** (optional, used for page styling)
- **Docker** (for PostgreSQL database initialization in testing environments)

## 🗃️ Project Structure
```
src/
├── main/
│ ├── java/com/example/challenge/
│ │ ├── config/
│ │ ├── controller/
│ │ ├── model/
│ │ ├── repository/
│ │ └── service/
│ ├── resources/
│ ├── templates/
│ └── application.properties
```

## 🔐 Access credentials for demonstrations

- **User:** `admin`
- **Password:** `admin`  
*(Credentials defined for demonstration purposes, all the accounts are stored in PostgreSQL DB, see src/main/resources/db/migration/V2__create_users.sql for more information)*

## 🚀 How to run

1. Clone the repository:
   git clone https://github.com/magnumsc/Springboot-Fullstack-challenge.git
   cd Springboot-Fullstack-challenge
2. Execute the application:
  a. Using an IDE: execute class **TBD**
  b. Using a terminal: ./gradlew bootrun
3. Access http://localhost:8080/products

## 📄 About the challenge

The challenge consisted in:
    
1. Create a relational schema for products and categories
2. develop an applcaition with product management functionalities
3. Implement a form of authentication
4. Optional - Add filtering and ordering functionalities for the products

This project demonstrate patractical knowledge in Java web development and Spring Boot framework, following standard REST Guidelines, as well as architecture practices and security implementations
