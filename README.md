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

##### AbstractEntity
- Provides auditing fields and active status.
- Fields: `createdAt`, `updatedAt`, `isActive`.

##### Admin
- Extends `AbstractEntity`.
- Fields: `id`, `firstname`, `lastname`, `user`.
- One-to-One relationship with `User`.

##### Classroom
- Extends `AbstractEntity`.
- Fields: `id`, `name`, `description`, `classroomUrl`, `imageUrl`, `isActive`, `creator`, `teachers`, `studentsOfClassroom`, `meetingDates`, `extraTeachers`.
- Many-to-One relationship with `Teacher` (creator).
- Many-to-Many relationship with `Teacher` (teachers).
- Many-to-Many relationship with `Student` (studentsOfClassroom).
- One-to-Many relationship with `MeetingDate` (meetingDates).
- Many-to-Many relationship with `Teacher` (extraTeachers).

##### MeetingDate
- Extends `AbstractEntity`.
- Fields: `id`, `date`, `time`, `endTime`, `endDate`, `isActive`, `classroom`.
- Many-to-One relationship with `Classroom`.

##### Student
- Extends `AbstractEntity`.
- Fields: `id`, `firstname`, `lastname`, `classrooms`, `user`, `teachers`.
- Many-to-Many relationship with `Classroom` (classrooms).
- One-to-One relationship with `User`.
- Many-to-Many relationship with `Teacher` (teachers).

##### Teacher
- Extends `AbstractEntity`.
- Fields: `id`, `firstname`, `lastname`, `user`, `classrooms`, `extraClassrooms`, `students`.
- One-to-One relationship with `User`.
- Many-to-Many relationship with `Classroom` (classrooms).
- Many-to-Many relationship with `Classroom` (extraClassrooms).
- Many-to-Many relationship with `Student` (students).

##### User
- Extends `AbstractEntity`.
- Implements `UserDetails`.
- Fields: `id`, `role`, `status`, `username`, `email`, `password`, `confirmPassword`, `teacher`, `student`, `admin`, `birthDate`, `country`, `city`.
- One-to-One relationship with `Teacher`.
- One-to-One relationship with `Student`.
- One-to-One relationship with `Admin`.

#### RESTful APIs
The application exposes various RESTful endpoints to manage school entities. Key endpoints include:

##### TeachersRestController
- Manages teacher-related operations.

###### Endpoints:
- **`PUT /teachers/add-teacher`**: Adds a teacher to a classroom.
  - Request Body: `AddTeacherToClassroomDTO`
  - Response: Success or error message.

- **`GET /teachers/current-teacher-id/{id}`**: Retrieves teacher details by ID.
  - Path Variable: `id`
  - Response: `GetTeachersIdDTO` or 404 if not found.

- **`GET /teachers/search-usernames`**: Searches for teacher usernames containing the given string.
  - Request Param: `username`
  - Response: List of matching usernames.

- **`GET /teachers/check-classroom-name`**: Checks if a classroom name exists.
  - Request Param: `name`
  - Response: Boolean indicating if the name exists.

- **`DELETE /teachers/delete-classroom/{id}`**: Deletes a classroom.
  - Path Variable: `id`
  - Response: Success or error message.

- **`GET /teachers/classroom/{id}`**: Retrieves classroom details for updating.
  - Path Variable: `id`
  - Response: `ClassroomUpdateDTO` or 404 if not found.

- **`GET /teachers/classroom-read-only/{id}`**: Retrieves read-only classroom details.
  - Path Variable: `id`
  - Response: `ClassroomReadOnlyDTO` or 404 if not found.

- **`PUT /teachers/update-classroom/{id}`**: Updates classroom details.
  - Path Variable: `id`
  - Request Body: `ClassroomUpdateDTO`
  - Response: Updated `Classroom` entity or error message.

- **`POST /teachers/create-meeting-date/{classroomId}`**: Creates a new meeting date for a classroom.
  - Path Variable: `classroomId`
  - Request Body: `CreateMeetingDateDTO`
  - Response: Created `MeetingDate` entity or error message.

- **`PUT /teachers/update-meeting-date/{classroomId}/{meetingDateId}`**: Updates a meeting date.
  - Path Variables: `classroomId`, `meetingDateId`
  - Request Body: `UpdateMeetingDateDTO`
  - Response: Success or error message.

- **`GET /teachers/classrooms-with-meetings`**: Retrieves all classrooms with their meeting dates for the current teacher.
  - Response: List of `ClassroomFindMeetingsDTO`.

- **`DELETE /teachers/delete-meeting/{meetingDateId}`**: Deletes a meeting date.
  - Path Variable: `meetingDateId`
  - Response: Success or error message.

- **`GET /teachers/search-students-dynamic`**: Searches for students by last name and optionally limits the number of results.
  - Request Params: `lastname`, `limit`
  - Response: List of `SearchStudentDTO` or error message.

- **`GET /teachers/find-classrooms-by-teacher-id/{teacherId}`**: Retrieves classrooms by teacher ID.
  - Path Variable: `teacherId`
  - Response: List of `Classroom` entities or error message.

- **`GET /teachers/find-classrooms-by-student-id/{studentId}`**: Retrieves classrooms for a specific student.
  - Path Variable: `studentId`
  - Response: `SearchStudentToClassroomDTO` or error message.

- **`POST /teachers/add-student-to-classroom/{classroomId}/student/{studentId}`**: Adds a student to a classroom.
  - Path Variables: `classroomId`, `studentId`
  - Response: Success or error message.

- **`DELETE /teachers/remove-student-from-classroom/{classroomId}/student/{studentId}`**: Removes a student from a classroom.
  - Path Variables: `classroomId`, `studentId`
  - Response: Success or error message.

#### Controllers
Controllers handle HTTP requests and responses. Key controllers include:

##### AdminsController
- Manages admin-related operations.

###### Methods:
- **`GET /admins/dashboard`**: Displays the admin dashboard.

##### CustomErrorController
- Handles custom error pages.

###### Methods:
- **`GET /error`**: Handles application errors and displays the error page.

##### ErrorController
- Handles application-wide exceptions and displays error messages.

###### Methods:
- **`handleNotFound`**: Handles 404 errors.
- **`handleBadRequest`**: Handles runtime exceptions.
- **`handleException`**: Handles generic exceptions.

##### FeaturesController
- Displays the features page.

###### Methods:
- **`GET /features`**: Displays the features page.

##### HomeController
- Displays the home page.

###### Methods:
- **`GET /`**: Displays the home page.

##### LoginController
- Manages login operations and redirects users based on their roles.
- Implements `AuthenticationSuccessHandler` and `AuthenticationFailureHandler`.

###### Methods:
- **`GET /login`**: Displays the login page.
- **`onAuthenticationSuccess`**: Handles successful login.
- **`onAuthenticationFailure`**: Handles failed login.

##### PricingController
- Displays the pricing page.

###### Methods:
- **`GET /pricing`**: Displays the pricing page.

##### RegisterController
- Handles user registration for students, teachers, and admins.

###### Methods:
- **`GET /register`**: Displays the registration page.
- **`GET /register/role`**: Handles role selection during registration.
- **`GET /register/teacher`**: Displays the teacher registration form.
- **`POST /register/teacher`**: Registers a new teacher.
- **`GET /register/student`**: Displays the student registration form.
- **`POST /register/student`**: Registers a new student.
- **`GET /register/admin`**: Displays the admin registration form.
- **`POST /register/admin`**: Registers a new admin.

##### StudentsController
- Manages student-related operations.

###### Methods:
- **`GET /students/dashboard`**: Displays the student dashboard.

##### TeachersController
- Manages teacher-related operations such as dashboard display, student search, and classroom creation.

###### Methods:
- **`GET /teachers/dashboard`**: Displays the teacher dashboard.
- **`GET /teachers/search-students`**: Searches for students.
- **`GET /teachers/create-classroom`**: Displays the classroom creation form.
- **`POST /teachers/create-classroom`**: Creates a new classroom.
- **`GET /teachers/your-classrooms`**: Displays the teacher's classrooms with pagination.

##### UnauthorisedErrorController
- Handles access denied errors (HTTP 403).

###### Methods:
- **`GET /error-403`**: Displays the access denied page.

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

###### Methods:
- **`registerAdmin(RegisterAdminDTO dto)`**: Registers a new administrator.
- **`findAllAdmins()`**: Retrieves all administrators.

##### IClassroomService
- Manages classrooms.

###### Methods:
- **`createClassroom(CreateClassroomDTO classroomDTO)`**: Creates a new classroom.
- **`getCreatorTeacher(Long classroomId)`**: Retrieves the creator teacher of a classroom.
- **`getStudentsInClassroom(Long classroomId)`**: Retrieves the students in a classroom.
- **`findClassroomsByTeacher(Long teacherId)`**: Finds classrooms by teacher ID.
- **`findAllClassrooms()`**: Retrieves all classrooms.
- **`updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO)`**: Updates a classroom.
- **`deleteClassroom(Long classroomId)`**: Deletes a classroom.
- **`classroomNameExists(String name)`**: Checks if a classroom name exists.
- **`addTeacherToClassroom(Long classroomId, String teacherUsername)`**: Adds a teacher to a classroom.
- **`findClassroomsByTeacher(Long teacherId, Pageable pageable)`**: Finds classrooms by teacher ID with pagination.
- **`findById(Long classroomId)`**: Finds a classroom by its ID.
- **`addTeacherToClassroom(AddTeacherToClassroomDTO dto)`**: Adds a teacher to a classroom.
- **`save(Classroom classroom)`**: Saves a classroom.
- **`updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO)`**: Updates classroom details.
- **`updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO meetingUpdateDTO)`**: Updates a meeting date.
- **`getAllClassroomsAndMeetingsForCurrentTeacher()`**: Retrieves all classrooms and their meeting dates for the current teacher.
- **`removeStudentFromClassroom(RemoveStudentDTO dto)`**: Removes a student from a classroom.
- **`isStudentInClassroom(Long classroomId, Long studentId)`**: Checks if a student is in a classroom.
- **`addStudentToClassroom(Long classroomId, Long studentId)`**: Adds a student to a classroom.
- **`getByClassroomId(Long id)`**: Retrieves a classroom by its ID.

##### IMeetingDateService
- Manages meeting dates.

###### Methods:
- **`createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO)`**: Creates a new meeting date.
- **`deleteMeetingDate(Long meetingDateId)`**: Deletes a meeting date.
- **`updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto)`**: Updates a meeting date.

##### IStudentService
- Manages students.

###### Methods:
- **`registerStudent(RegisterStudentDTO dto)`**: Registers a new student.
- **`findAllStudents()`**: Retrieves all students.
- **`findByLastnameContainingOrderByLastname(String lastname)`**: Finds students by last name.
- **`searchStudentsByLastname(String lastname)`**: Searches for students by last name.
- **`getStudentClassrooms(Long studentId)`**: Retrieves classrooms for a specific student.

##### ITeacherService
- Manages teachers.

###### Methods:
- **`registerTeacher(RegisterTeacherDTO dto)`**: Registers a new teacher.
- **`findAllTeachers()`**: Retrieves all teachers.
- **`searchStudentsByLastName(String lastName)`**: Searches for students by last name.
- **`findTeacherByFirstname(String firstname)`**: Finds a teacher by their first name.
- **`getCurrentAuthenticatedTeacher()`**: Retrieves the currently authenticated teacher.
- **`findByUsername(String username)`**: Retrieves a teacher by their username.
- **`findByUsernameContaining(String username)`**: Retrieves teachers by a username containing the specified string.
- **`findById(Long id)`**: Retrieves a teacher by their ID.

##### IUserService
- Manages users.

###### Methods:
- **`getUserByUsername(String username)`**: Retrieves a user by their username.

#### Service Implementations
The application provides various service implementations for managing school entities. Key implementations include:

##### AdminServiceImpl
- Implementation of the `IAdminService` interface.
- Manages admin-related operations.

###### Methods:
- **`registerAdmin(RegisterAdminDTO dto)`**: Registers a new admin.
- **`findAllAdmins()`**: Retrieves all admins.

##### ClassroomServiceImpl
- Implementation of the `IClassroomService` interface.
- Manages classroom-related operations.

###### Methods:
- **`createClassroom(CreateClassroomDTO classroomDTO)`**: Creates a new classroom.
- **`getCreatorTeacher(Long classroomId)`**: Retrieves the creator teacher of a classroom.
- **`getStudentsInClassroom(Long classroomId)`**: Retrieves the students in a classroom.
- **`findClassroomsByTeacher(Long teacherId)`**: Finds classrooms by teacher ID.
- **`findAllClassrooms()`**: Retrieves all classrooms.
- **`updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO)`**: Updates a classroom.
- **`deleteClassroom(Long classroomId)`**: Deletes a classroom.
- **`classroomNameExists(String name)`**: Checks if a classroom name exists.
- **`addTeacherToClassroom(Long classroomId, String teacherUsername)`**: Adds a teacher to a classroom.
- **`findClassroomsByTeacher(Long teacherId, Pageable pageable)`**: Finds classrooms by teacher ID with pagination.
- **`findById(Long classroomId)`**: Finds a classroom by its ID.
- **`addTeacherToClassroom(AddTeacherToClassroomDTO dto)`**: Adds a teacher to a classroom.
- **`save(Classroom classroom)`**: Saves a classroom.
- **`updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO)`**: Updates classroom details.
- **`updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO meetingUpdateDTO)`**: Updates a meeting date.
- **`getAllClassroomsAndMeetingsForCurrentTeacher()`**: Retrieves all classrooms and their meeting dates for the current teacher.
- **`removeStudentFromClassroom(RemoveStudentDTO dto)`**: Removes a student from a classroom.
- **`isStudentInClassroom(Long classroomId, Long studentId)`**: Checks if a student is in a classroom.
- **`addStudentToClassroom(Long classroomId, Long studentId)`**: Adds a student to a classroom.
- **`getByClassroomId(Long id)`**: Retrieves a classroom by its ID.

##### MeetingDateServiceImpl
- Implementation of the `IMeetingDateService` interface.
- Manages meeting date-related operations.

###### Methods:
- **`createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO)`**: Creates a new meeting date.
- **`deleteMeetingDate(Long meetingDateId)`**: Deletes a meeting date.
- **`updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto)`**: Updates a meeting date.

##### StudentServiceImpl
- Implementation of the `IStudentService` interface.
- Manages student-related operations.

###### Methods:
- **`registerStudent(RegisterStudentDTO dto)`**: Registers a new student.
- **`findAllStudents()`**: Retrieves all students.
- **`findByLastnameContainingOrderByLastname(String lastname)`**: Finds students by last name.
- **`searchStudentsByLastname(String lastname)`**: Searches for students by last name.
- **`getStudentClassrooms(Long studentId)`**: Retrieves classrooms for a specific student.

##### TeacherServiceImpl
- Implementation of the `ITeacherService` interface.
- Manages teacher-related operations.

###### Methods:
- **`registerTeacher(RegisterTeacherDTO dto)`**: Registers a new teacher.
- **`findAllTeachers()`**: Retrieves all teachers.
- **`searchStudentsByLastName(String lastName)`**: Searches for students by last name.
- **`findTeacherByFirstname(String firstname)`**: Finds a teacher by their first name.
- **`getCurrentAuthenticatedTeacher()`**: Retrieves the currently authenticated teacher.
- **`findByUsername(String username)`**: Retrieves a teacher by their username.
- **`findByUsernameContaining(String username)`**: Retrieves teachers by a username containing the specified string.
- **`findById(Long id)`**: Retrieves a teacher by their ID.

##### UserServiceImpl
- Implementation of the `IUserService` interface.
- Manages user-related operations.

###### Methods:
- **`getUserByUsername(String username)`**: Retrieves a user by their username.


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
- Manages `Admin` entities.

###### Methods:
- **`JpaRepository<Admin, Long>`**: Extends `JpaRepository` to provide basic CRUD operations.

##### ClassroomRepository
- Manages `Classroom` entities.

###### Methods:
- **`boolean existsByName(String name)`**: Checks if a classroom exists by name.
- **`Page<Classroom> findByTeachers_Id(Long teacherId, Pageable pageable)`**: Finds classrooms by teacher ID with pagination.
- **`Optional<Classroom> findById(Long classroomId)`**: Finds a classroom by its ID.
- **`List<Classroom> findByCreatorOrTeachersContaining(Teacher creator, Teacher teacher)`**: Finds classrooms by creator or containing a specific teacher.
- **`boolean existsByIdAndStudentsOfClassroom_Id(Long classroomId, Long studentId)`**: Checks if a student is in a specific classroom.

##### MeetingDateRepository
- Manages `MeetingDate` entities.

###### Methods:
- **`Optional<MeetingDate> findById(Long id)`**: Finds a meeting date by its ID.
- **`List<MeetingDate> findByClassroom(Classroom classroom)`**: Finds meeting dates for a specific classroom.

##### StudentRepository
- Manages `Student` entities.

###### Methods:
- **`List<Student> findByLastnameContainingOrderByLastname(String lastName)`**: Finds students by last name containing a specific string, ordered by last name.
- **`List<Student> findByLastnameContainingIgnoreCase(String lastname)`**: Finds students by last name containing a specific string, case insensitive.

##### TeacherRepository
- Manages `Teacher` entities.

###### Methods:
- **`Optional<Teacher> findByUserUsername(String username)`**: Finds a teacher by their username.
- **`List<Teacher> findByUserUsernameContaining(String username)`**: Finds teachers by username containing a specific string.
- **`Teacher findByFirstname(String firstname)`**: Finds a teacher by their first name.

##### UserRepository
- Manages `User` entities.

###### Methods:
- **`Optional<User> findByUsername(String username)`**: Finds a user by their username.

#### Mappers
Mappers convert between DTOs and entities. The `Mapper` class includes methods like:

##### ClassroomMapper
- Converts between `Classroom` entities and DTOs.
- Methods:
  - `toClassroom(CreateClassroomDTO dto, Long creatorId)`: Converts `CreateClassroomDTO` to `Classroom` entity.
  - `updateClassroomFromDTO(ClassroomUpdateDTO dto, Classroom classroom)`: Updates `Classroom` entity from `ClassroomUpdateDTO`.
  - `toMeetingDate(CreateMeetingDateDTO dto)`: Converts `CreateMeetingDateDTO` to `MeetingDate` entity.
  - `toClassroomFindMeetingsDTO(Classroom classroom)`: Converts `Classroom` entity to `ClassroomFindMeetingsDTO`.
  - `toReadOnlyDTO(Classroom classroom)`: Converts `Classroom` entity to `ClassroomReadOnlyDTO`.

##### Mapper
- Provides static methods to convert registration DTOs to their respective models.
- Methods:
  - `extractTeacherFromRegisterTeacherDTO(RegisterTeacherDTO dto)`: Converts `RegisterTeacherDTO` to `Teacher` model.
  - `extractUserFromRegisterTeacherDTO(RegisterTeacherDTO dto)`: Converts `RegisterTeacherDTO` to `User` model with `TEACHER` role.
  - `extractStudentFromRegisterStudentDTO(RegisterStudentDTO dto)`: Converts `RegisterStudentDTO` to `Student` model.
  - `extractUserFromRegisterStudentDTO(RegisterStudentDTO dto)`: Converts `RegisterStudentDTO` to `User` model with `STUDENT` role.
  - `extractAdminFromRegisterAdminDTO(RegisterAdminDTO dto)`: Converts `RegisterAdminDTO` to `Admin` model.
  - `extractUserFromRegisterAdminDTO(RegisterAdminDTO dto)`: Converts `RegisterAdminDTO` to `User` model with `ADMIN` role.
  - `extractGetTeachersIdDTOFromTeacher(Teacher teacher)`: Converts `Teacher` to `GetTeachersIdDTO`.

##### MeetingDateMapper
- Converts between `MeetingDate` entities and DTOs.
- Methods:
  - `updateMeetingDateFromDTO(UpdateMeetingDateDTO dto, MeetingDate meetingDate)`: Updates `MeetingDate` entity from `UpdateMeetingDateDTO`.
  - `toMeetingDateFindMeetingsDTO(MeetingDate meetingDate)`: Converts `MeetingDate` entity to `FindMeetingMeetingDateDTO`.

##### StudentMapper
- Converts between `Student` entities and DTOs.
- Methods:
  - `toSearchStudentDTO(Student student)`: Converts `Student` entity to `SearchStudentDTO`.
  - `toSearchStudentToClassroomDTO(Student student)`: Converts `Student` entity to `SearchStudentToClassroomDTO`.
  - `toClassroomDTO(Classroom classroom)`: Helper method to convert `Classroom` entity to `ClassroomDTO`.

#### Enums
Enums are used for predefined constants, such as roles and status within the application.

#### Security Configuration
The application uses Spring Security for authentication and authorization. Key components and configurations include:

##### CustomAuthProvider
- Configures a custom authentication provider.

###### Methods:
- **`authenticationProvider()`**: Configures and returns an `AuthenticationProvider` using `DaoAuthenticationProvider` with custom user details service and password encoder.
- **`authenticationManager(AuthenticationConfiguration config)`**: Returns an `AuthenticationManager` from the specified `AuthenticationConfiguration`.
- **`passwordEncoder()`**: Configures and returns a `PasswordEncoder` using BCrypt hashing algorithm.

##### CustomUserDetailsService
- Implements `UserDetailsService` to load user details by username.

###### Methods:
- **`loadUserByUsername(String username)`**: Loads the user by their username.

##### SecurityConfig
- Configures security settings, including CORS, CSRF, authentication, and authorization.

###### Methods:
- **`corsConfigurationSource()`**: Configures the CORS settings for the application.
- **`filterChain(HttpSecurity http)`**: Configures the security filter chain for the application.

###### Security Settings:
- CORS: Configures allowed origins, methods, and headers.
- CSRF: Disabled.
- Authorization: Specifies access permissions based on roles and public endpoints.
- Form Login: Custom login page and success handler.
- HTTP Basic: Default settings.
- Session Management: Session creation policy set to `IF_REQUIRED`.
- Logout: Configures logout success URL, session invalidation, and cookie deletion.
- Remember Me: Configures "remember me" functionality with token validity.
- Exception Handling: Custom access denied handler redirecting to `/error-403`.

#### Thymeleaf Templates
Thymeleaf is used for server-side rendering of HTML pages. Templates are located in the templates' directory.

#### Static Resources
JavaScript and CSS files are located in the static directory.

#### JavaScript Files

The application uses several JavaScript files to manage various client-side functionalities, including form validation, animations, and AJAX requests.

##### `createClassroom.js`
- Handles form validation and submission for creating a new classroom.
- Validates fields such as `name`, `description`, and `classroomUrl` based on specified rules.
- Checks if a classroom name already exists before submission.
- Displays success or error messages accordingly.

##### `features.js`
- Manages the display of different content sections on the features page.
- Implements animations for transitioning between sections.
- Handles active state changes for the list items corresponding to each section.

##### `index.js`
- Contains simple logic to toggle the visibility of the section title at regular intervals to create a blinking effect.

##### `login.js`
- Manages the login form validation and submission.
- Shows a spinner while the login request is processed.
- Displays an error message if the login credentials are invalid.

##### `registerAdmin.js`
- Redirects to the login page after a successful admin registration with a delay of 3 seconds.

##### `registerStudent.js`
- Redirects to the login page after a successful student registration with a delay of 3 seconds.

##### `registerTeacher.js`
- Redirects to the login page after a successful teacher registration with a delay of 3 seconds.


##### `createClassroom.js`
- Handles form validation and submission for creating a new classroom.
- Validates fields such as `name`, `description`, and `classroomUrl` based on specified rules.
- Checks if a classroom name already exists before submission.
- Displays success or error messages accordingly.

##### `searchStudent.js`
- Handles dynamic searching of students based on the input provided by the user.
- Manages the display of search results, including showing and hiding a spinner during data fetching.
- Includes error handling to display appropriate error messages if the request fails.
- Updates the results table with the fetched student data and manages the addition and removal of students to/from classrooms.

##### `teachersDashboard.js`
- Handles the main functionalities for the teacher's dashboard, including fetching and displaying classroom and meeting data.
- Manages the update and deletion of meeting details.
- Includes functions for showing different sections of the dashboard and managing navigation.

##### `teachersYourClassrooms.js`
- Manages the functionalities related to the teacher's own classrooms.
- Includes functions for adding and removing teachers, searching classrooms, and deleting classrooms.
- Provides modal handling for updating classroom details and setting meetings.
- Manages the display and hiding of spinners and buttons during operations.

#### Logging
Logging is configured using the `logback.xml` file.

#### Testing
The RESTful APIs were thoroughly tested using Postman. Postman allowed for the creation and execution of test cases to ensure the correctness and reliability of the API endpoints.

##### Postman Testing Includes:
- **Creating Requests**: Formulated GET, POST, PUT, DELETE requests to interact with the APIs.
- **Validating Responses**: Checked response status codes, headers, and body content to verify the API functionality.
- **Automated Tests**: Wrote automated test scripts within Postman to run tests on API responses.
- **Environment Setup**: Configured environment variables for different scenarios and testing conditions.
- **Collection Runs**: Used Postman Collections to organize and execute grouped requests in sequence, ensuring end-to-end testing.

The use of Postman ensured comprehensive coverage of API functionalities and provided a reliable means to test various scenarios and edge cases.

#### License
This project is licensed under the MIT License.
