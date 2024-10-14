# Medical Backend API

This project is a REST API implementation using Java Spring Boot for managing a medical clinic.

## Table Of Content

- [Getting Started](#getting-started)
    - [Prerequistes](#prerequisites)
- [Solution Design](#solution-design)
    - [Database Schema](#database)
    - [Architecture Design](#architecture-design)
- [Installation](#installation)
    - [Java 17](#java)
    - [Maven](#maven)
    - [MySQL](#mysql-server)
- [License](#license)
- [Links](#links)

## Getting Started

This file aims to guide anyone in installing and running the project locally. You will have the ability to modify the code to make improvements and implement your own features.

### Prerequisites

To run this project, you need to have some dependencies installed.
- JAVA: 17.0.11 2024-04-16 LTS
- MAVEN: 3.9.9
- MySQL Server: 8.0

## Solution Design

### Database
The database schema follows a structure where each user has roles, which can be ADMIN, DOCTOR, NURSE, and PATIENT. Each patient can have multiple consultations, just as each doctor can have multiple consultations. There is a one-to-many relationship between the user table and the consultation table.

![Database](docs\assets\database-schema.png)

### Architecture Design

The architecture was designed with the AWS environment in mind, but it can be implemented using any other cloud environment that allows the use of a dedicated server and a database. We use a VPC for the EC2 instance, where the application build will be hosted, and the RDS instance using MySQL.

![Architecture Design](docs\assets\cloud-arch.png)

## Installation

### Java
To install **Java** you can follow the steps [here](docs\JAVA.md).

### Maven
To install **Maven** you can follow the steps [here](docs\MAVEN.md).

### MySQL Server
To install **MySQL Server** you can follow the steps [here](docs\MYSQL.md).

Create a database in MySQL called `medico` with user `root` and password `root`. 

IMPORTANT: This configuration in only for development purposes.

### Start Application
If everting goes well, you should to abled to run the project.
- Run the maven command:
```bash
    mvn spring-boot:run
```
Or, if you are using some IDE like IntelliJ or VSCode just click `RUN` in your IDE.



