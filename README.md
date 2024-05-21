## School App (Spring Boot MVC)
# Description
This project is a School Management Application developed using the Spring Boot MVC framework. It provides functionalities for managing students, teachers, courses, and other aspects related to school administration. The application includes authentication and authorization features, a responsive user interface using Thymeleaf, and is integrated with a MySQL database.

# Prerequisites
Java Development Kit (JDK) version 17 or higher
Maven
MySQL database

# Installation
Clone the repository by using the appropriate git command.
Navigate to the project directory.
Configure the database by creating a MySQL database named school_app and updating the application.properties file with your database credentials.
Build the project using Maven.
Run the application using the Maven Spring Boot plugin.

# Technologies Used
Spring Boot
Spring MVC
Spring Data JPA
Thymeleaf
MySQL
Spring Security
TestNG
Mockito

# Project Structure

# Schema
The database schema includes tables for managing students, teachers, users, classrooms, and courses. The schema is defined using JPA entities.

# Service Layer
The service layer contains business logic and interacts with repositories to fetch and persist data. Key services include:

AdminService
TeacherService
StudentService
UserService
# Data Transfer Objects (DTOs)

DTOs are used to transfer data between the client and server. Key DTOs include:

RegisterAdminDTO
RegisterTeacherDTO
RegisterStudentDTO

# Controllers
Controllers handle HTTP requests and responses. Key controllers include:

AdminsController
TeachersController
StudentsController
UsersController

# Repositories
Repositories interact with the database to perform CRUD operations. Key repositories include:

AdminRepository
TeacherRepository
StudentRepository
UserRepository

# Mappers
Mappers convert between DTOs and entities. The Mapper class includes methods like:

extractAdminFromRegisterAdminDTO
extractTeacherFromRegisterTeacherDTO
extractStudentFromRegisterStudentDTO
extractUserFromRegisterAdminDTO

# Enums
Enums are used for predefined constants, such as roles within the application.

# Authentication
Spring Security is used to handle authentication and authorization. Security configurations are defined in the SecurityConfig class.

# Thymeleaf Templates
Thymeleaf is used for server-side rendering of HTML pages. Templates are located in the templates directory.

# Static Resources
JavaScript and CSS files are located in the static directory.

# Logging
Logging is configured using the logback.xml file.

# Testing
The service layer is tested using TestNG and Mockito. Key test classes include:

AdminServiceImplTest
TeacherServiceImplTest
StudentServiceImplTest
UserServiceImplTest
License

# This project is licensed under the MIT License.