# 🏥 Medical Backend API

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)

A robust REST API implementation using Java Spring Boot for efficient management of a medical clinic.

## 📑 Table of Contents

- [🚀 Getting Started](#-getting-started)
  - [📋 Prerequisites](#-prerequisites)
- [🏗️ Solution Design](#️-solution-design)
  - [💾 Database Schema](#-database-schema)
  - [🏛️ Architecture Design](#️-architecture-design)
- [⚙️ Installation](#️-installation)
  - [☕ Java 17](#-java-17)
  - [🛠️ Maven](#️-maven)
  - [🐬 MySQL](#-mysql)
- [🏃‍♂️ Running the Application](#️-running-the-application)
- [📄 License](#-license)
- [🔗 Links](#-links)

## 🚀 Getting Started

This guide will help you install and run the project locally, enabling you to modify the code, make improvements, and implement your own features.

### 📋 Prerequisites

To run this project, you need to have the following dependencies installed:

- **JAVA**: 17.0.11 2024-04-16 LTS
- **MAVEN**: 3.9.9
- **MySQL Server**: 8.0

## 🏗️ Solution Design

### 💾 Database Schema

The database schema follows a structure where each user has roles (ADMIN, DOCTOR, NURSE, and PATIENT). Patients and doctors can have multiple consultations, with a one-to-many relationship between the user table and the consultation table.

![Database Schema](https://i.imgur.com/iEEQoPp.png)

### 🏛️ Architecture Design

The architecture is designed with the AWS environment in mind but can be implemented using any cloud environment that supports a dedicated server and database. We use a VPC for the EC2 instance to host the application build and an RDS instance using MySQL.

![Architecture Design](https://i.imgur.com/mlIEKUA.png)

## ⚙️ Installation

### ☕ Java 17

To install **Java**, follow the steps in our [Java Installation Guide](docs/JAVA.md).

### 🛠️ Maven

For **Maven** installation, refer to our [Maven Installation Guide](docs/MAVEN.md).

### 🐬 MySQL

To set up **MySQL Server**, follow our [MySQL Installation Guide](docs/MYSQL.md).

Then, create a database in MySQL:

```sql
CREATE DATABASE medico;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON medico.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

> **IMPORTANT**: This configuration is for development purposes only. Ensure to use secure credentials in production.

## 🏃‍♂️ Running the Application

If everything is set up correctly, you can run the project using one of these methods:

1. Using Maven command:
   ```bash
   mvn spring-boot:run
   ```

2. Using an IDE like IntelliJ or VSCode:
  - Open the project in your IDE
  - Locate the main application file
  - Click the `RUN` button

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 🔗 Links

- [API Documentation](http://localhost:8080/swagger-ui/index.html)

---

Made with ❤️ by [José Yan Lipu](https://lipusolutions.com)