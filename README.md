## School App (Spring Boot MVC)

### Description
This project is a School Management Application developed using the Spring Boot MVC framework. It provides functionalities for managing students, teachers, courses, and other aspects related to school administration. The application includes authentication and authorization features, a responsive user interface using Thymeleaf, and is integrated with a MySQL database.

### Prerequisites
- Java Development Kit (JDK) version 17 or higher
- Maven
- MySQL database

### Installation
1. Clone the repository using the appropriate git command.
2. Navigate to the project directory.
3. Configure the database by creating a MySQL database named `school_app` and updating the `application.properties` file with your database credentials.
4. Build the project using Maven.
5. Run the application using the Maven Spring Boot plugin.

### Technologies Used
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- Spring Security
- TestNG
- Mockito

### Project Structure

#### Schema
The database schema includes tables for managing students, teachers, users, classrooms, and courses. The schema is defined using JPA entities.

#### Model

##### Admin
- Extends `AbstractEntity`.
- One-to-One relationship with `User`.

##### Classroom
- Extends `AbstractEntity`.
- Many-to-One relationship with `Teacher` (creator).
- Many-to-Many relationship with `Teacher` (teachers).
- One-to-Many relationship with `Student` (studentsOfClassroom).
- One-to-Many relationship with `MeetingDate` (meetingDates).
- Many-to-Many relationship with `Teacher` (extraTeachers).

##### MeetingDate
- Extends `AbstractEntity`.
- Many-to-One relationship with `Classroom`.

##### Student
- Extends `AbstractEntity`.
- Many-to-One relationship with `Classroom`.
- One-to-One relationship with `User`.

##### Teacher
- Extends `AbstractEntity`.
- One-to-One relationship with `User`.
- Many-to-Many relationship with `Classroom` (classrooms).
- Many-to-Many relationship with `Classroom` (extraClassrooms).

##### User
- Extends `AbstractEntity`.
- Implements `UserDetails`.
- One-to-One relationship with `Teacher`.
- One-to-One relationship with `Student`.
- One-to-One relationship with `Admin`.

#### Controllers
Controllers handle HTTP requests and responses. Key controllers include:

##### UnauthorisedErrorController
- Handles access denied errors (HTTP 403).

##### TeachersController
- Manages teacher-related operations such as dashboard display, student search, classroom creation, teacher addition, and classroom deletion.

##### StudentsController
- Manages student-related operations such as dashboard display.

##### RegisterController
- Handles user registration for students, teachers, and admins.

##### PricingController
- Displays the pricing page.

##### LoginController
- Manages login operations and redirects users based on their roles.
- Implements `AuthenticationSuccessHandler` and `AuthenticationFailureHandler`.

##### HomeController
- Displays the home page.

##### FeaturesController
- Displays the features page.

##### ErrorController
- Handles application-wide exceptions and displays error messages.

##### CustomErrorController
- Handles custom error pages.

##### AdminsController
- Manages admin-related operations such as dashboard display.

#### Service Exceptions
Custom exceptions are defined for specific error conditions in the service layer. Key exceptions include:

##### AdminAlreadyExistsException
- Thrown when an attempt is made to create an admin that already exists.

##### ClassroomAlreadyExistsException
- Thrown when an attempt is made to create a classroom that already exists.

##### StudentAlreadyExistsException
- Thrown when an attempt is made to create a student that already exists.

##### TeacherAlreadyExistsException
- Thrown when an attempt is made to create a teacher that already exists.

#### Services
Service interfaces and implementations for managing various entities:

##### IAdminService
- Manages administrators.
- Methods: `registerAdmin`, `findAllAdmins`.

##### IClassroomService
- Manages classrooms.
- Methods: `createClassroom`, `getCreatorTeacher`, `getStudentsInClassroom`, `findClassroomsByTeacher`, `findAllClassrooms`, `updateClassroom`, `deleteClassroom`, `classroomNameExists`, `addTeacherToClassroom`, `findClassroomsByTeacher(Pageable pageable)`.

##### IStudentService
- Manages students.
- Methods: `registerStudent`, `findAllStudents`, `findByLastnameContainingOrderByLastname`, `searchStudentsByLastname`.

##### ITeacherService
- Manages teachers.
- Methods: `registerTeacher`, `findAllTeachers`, `searchStudentsByLastName`, `findTeacherByFirstname`, `getCurrentAuthenticatedTeacher`.

##### IUserService
- Manages users.
- Methods: `getUserByUsername`.

##### AdminServiceImpl
- Implements `IAdminService`.

##### ClassroomServiceImpl
- Implements `IClassroomService`.

##### StudentServiceImpl
- Implements `IStudentService`.

##### TeacherServiceImpl
- Implements `ITeacherService`.

##### UserServiceImpl
- Implements `IUserService`.

#### Data Transfer Objects (DTOs)
DTOs are used to transfer data between the client and server. Key DTOs include:

- `RegisterAdminDTO`
- `CreateClassroomDTO`
- `MeetingDateDTO`
- `RegisterStudentDTO`
- `SearchStudentDTO`
- `SearchTeacherDTO`
- `RegisterTeacherDTO`
- `AddTeacherToClassroomDTO`

#### Repositories
Repositories interact with the database to perform CRUD operations. Key repositories include:

##### AdminRepository
- Handles CRUD operations for `Admin` entities.

##### ClassroomRepository
- Handles CRUD operations for `Classroom` entities.
- Provides custom query methods:
  - `findByTeachers_Id(Long teacherId)`: Finds classrooms by teacher ID.
  - `findByStudentsOfClassroom_Id(Long studentId)`: Finds classrooms by student ID.
  - `existsByName(String name)`: Checks if a classroom exists by name.
  - `findByTeachers_Id(Long teacherId, Pageable pageable)`: Finds classrooms by teacher ID with pagination.

##### MeetingDateRepository
- Handles CRUD operations for `MeetingDate` entities.

##### StudentRepository
- Handles CRUD operations for `Student` entities.
- Provides custom query methods:
  - `findByLastnameContainingOrderByLastname(String lastName)`: Finds students by last name containing a specific string, ordered by last name.
  - `findByLastnameContainingIgnoreCase(String lastname)`: Finds students by last name containing a specific string, case insensitive.

##### TeacherRepository
- Handles CRUD operations for `Teacher` entities.
- Provides custom query methods:
  - `findByUserUsername(String username)`: Finds a teacher by their username.
  - `findByClassrooms_Id(Long classroomId)`: Finds teachers by classroom ID.
  - `findByFirstname(String firstname)`: Finds a teacher by their first name.

##### UserRepository
- Handles CRUD operations for `User` entities.
- Provides custom query methods:
  - `findByRole(Role role)`: Finds a user by their role.
  - `findByUsername(String username)`: Finds a user by their username.
  - `countByRole(Role role)`: Counts the number of users with a specific role.
  - `countByRoleAndStatus(Role role, Status status)`: Counts the number of users with a specific role and status.

#### Mappers
Mappers convert between DTOs and entities. The `Mapper` class includes methods like:

- `extractAdminFromRegisterAdminDTO`
- `extractTeacherFromRegisterTeacherDTO`
- `extractStudentFromRegisterStudentDTO`
- `extractUserFromRegisterAdminDTO`
- `extractUserFromRegisterTeacherDTO`
- `extractUserFromRegisterStudentDTO`

The `ClassroomMapper` class includes the method:

- `toClassroom`

#### Enums
Enums are used for predefined constants, such as roles and status within the application.

#### Authentication
The `authentication` package contains classes related to authentication and authorization using Spring Security:

##### SecurityConfig
- Configures security settings, including CORS, CSRF, authentication, and authorization.
- Defines CORS settings and security filter chain.
- Specifies access permissions based on roles and handles login, logout, and session management.

##### CustomUserDetailsService
- Implements `UserDetailsService` and overrides `loadUserByUsername` method to load user details from the database.

##### CustomAuthProvider
- Configures custom authentication provider using `DaoAuthenticationProvider` with custom user details service and BCrypt password encoder.
- Provides an `AuthenticationManager` and `PasswordEncoder` bean.

#### Thymeleaf Templates
Thymeleaf is used for server-side rendering of HTML pages. Templates are located in the templates' directory.

#### Static Resources
JavaScript and CSS files are located in the static directory.

#### Logging
Logging is configured using the `logback.xml` file.

#### Testing
The service layer is tested using TestNG and Mockito. Key test classes include:

- `AdminServiceImplTest`
- `TeacherServiceImplTest`
- `StudentServiceImplTest`
- `UserServiceImplTest`

#### License
This project is licensed under the MIT License.
