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
- Unit Testing

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

##### Message
- Fields: `id`, `content`, `timestamp`, `sender`, `receiver`, `notifications`.
- Many-to-One relationship with `User` (sender).
- Many-to-One relationship with `User` (receiver).
- One-to-Many relationship with `Notification` (notifications).

##### Notification
- Fields: `id`, `content`, `timestamp`, `sender`, `receiver`, `message`, `notify`.
- Many-to-One relationship with `User` (sender).
- Many-to-One relationship with `User` (receiver).
- Many-to-One relationship with `Message` (message).

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
- One-to-Many relationship with `Message` (sentMessages).
- One-to-Many relationship with `Notification` (receivedNotifications).
- One-to-Many relationship with `Notification` (sentNotifications).
- One-to-Many relationship with `Message` (receivedMessages).


#### RESTful APIs
The application exposes various RESTful endpoints to manage school entities. Key endpoints include:

### RESTful APIs

#### AdminRestController
- **Endpoint:** `/admin/delete/me`
  - **Method:** `DELETE`
  - **Description:** Deletes the authenticated admin.
  - **Response:** `Void`

- **Endpoint:** `/admin/update/me`
  - **Method:** `PUT`
  - **Description:** Updates the authenticated admin.
  - **Response:** `Void`

- **Endpoint:** `/admin/get-user/{username}`
  - **Method:** `GET`
  - **Description:** Finds a user by username.
  - **Response:** `Optional<UserDTO>`

- **Endpoint:** `/admin/get-by-email/{email}`
  - **Method:** `GET`
  - **Description:** Finds a user by email.
  - **Response:** `Optional<UserDTO>`

- **Endpoint:** `/admin/get/id/{id}`
  - **Method:** `GET`
  - **Description:** Finds a user by ID.
  - **Response:** `UserDTO`

- **Endpoint:** `/admin/get/role/{role}`
  - **Method:** `GET`
  - **Description:** Finds users by role.
  - **Response:** List of `UserDTO`

- **Endpoint:** `/admin/get-all`
  - **Method:** `GET`
  - **Description:** Finds all users.
  - **Response:** `Page<UserDTO>`

- **Endpoint:** `/admin/get/{status}`
  - **Method:** `GET`
  - **Description:** Finds users by status.
  - **Response:** List of `UserDTO`

- **Endpoint:** `/admin/delete/{id}`
  - **Method:** `DELETE`
  - **Description:** Deletes a user by ID.
  - **Response:** `Void`

- **Endpoint:** `/admin/update/{id}`
  - **Method:** `PATCH`
  - **Description:** Updates a user by ID.
  - **Response:** `UserUpdateDTO`

- **Endpoint:** `/admin/create`
  - **Method:** `POST`
  - **Description:** Creates a new user.
  - **Response:** `Map<String, String>`

- **Endpoint:** `/admin/search`
  - **Method:** `GET`
  - **Description:** Searches for users by keyword.
  - **Response:** `Page<UserDTO>`

#### ClassroomRestController
- **Endpoint:** `/classrooms/student/{studentId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of classrooms for a specific student by their ID.
  - **Response:** List of `ClassroomStudentsClassroomDTO`

- **Endpoint:** `/classrooms/classrooms/{teacherId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of classrooms for a specific teacher by their ID.
  - **Response:** List of `Classroom`

#### MessageRestController
- **Endpoint:** `/messages/sender/{senderId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of messages sent by a specific user by their ID.
  - **Response:** List of `GetMessageDTO`

- **Endpoint:** `/messages/receiver/{receiverId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of messages received by a specific user by their ID.
  - **Response:** List of `GetMessageDTO`

- **Endpoint:** `/messages/sender/{senderId}/receiver/{receiverId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of messages between a specific sender and receiver by their IDs.
  - **Response:** List of `GetMessageDTO`

- **Endpoint:** `/messages/send`
  - **Method:** `POST`
  - **Description:** Sends a new message.
  - **Response:** `SendMessageDTO`

- **Endpoint:** `/messages/update`
  - **Method:** `PUT`
  - **Description:** Updates a message with the specified ID.
  - **Response:** `GetMessageDTO`

- **Endpoint:** `/messages/delete`
  - **Method:** `DELETE`
  - **Description:** Deletes a message with the specified ID.
  - **Response:** `Void`

- **Endpoint:** `/messages/teacher/{username}`
  - **Method:** `GET`
  - **Description:** Retrieves the ID of the teacher by their username.
  - **Response:** `Long`

- **Endpoint:** `/messages/student/{email}`
  - **Method:** `GET`
  - **Description:** Retrieves the ID of the student by their email.
  - **Response:** `Long`

- **Endpoint:** `/messages/current/teacher`
  - **Method:** `GET`
  - **Description:** Retrieves the ID of the current authenticated teacher.
  - **Response:** `Long`

- **Endpoint:** `/messages/current/student`
  - **Method:** `GET`
  - **Description:** Retrieves the current authenticated student.
  - **Response:** `StudentGetCurrentStudentDTO`

- **Endpoint:** `/messages/username/{username}`
  - **Method:** `GET`
  - **Description:** Retrieves the student by their username.
  - **Response:** `Student`

- **Endpoint:** `/messages/classroom/{classroomId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of student usernames and IDs for a specific classroom by its ID.
  - **Response:** List of `StudentGetUsernameAndIdDTO`

- **Endpoint:** `/messages/user/{username}`
  - **Method:** `GET`
  - **Description:** Retrieves the ID of the user by their username.
  - **Response:** `Long`

- **Endpoint:** `/messages/message/{id}`
  - **Method:** `GET`
  - **Description:** Retrieves the message by its ID.
  - **Response:** `GetMessageDTO`

#### NotificationRestController
- **Endpoint:** `/notifications/receiver/{receiverId}`
  - **Method:** `GET`
  - **Description:** Retrieves the list of notifications for a specific receiver by their ID.
  - **Response:** List of `GetNotificationDTO`

- **Endpoint:** `/notifications/create`
  - **Method:** `POST`
  - **Description:** Creates a new notification.
  - **Response:** `GetNotificationDTO`

- **Endpoint:** `/notifications/update`
  - **Method:** `PUT`
  - **Description:** Updates an existing notification.
  - **Response:** `GetNotificationDTO`

- **Endpoint:** `/notifications/delete`
  - **Method:** `DELETE`
  - **Description:** Deletes a notification.
  - **Response:** `Void`

- **Endpoint:** `/notifications/read/{notificationId}`
  - **Method:** `PUT`
  - **Description:** Marks a notification as read.
  - **Response:** `Void`

- **Endpoint:** `/notifications/read/all/{receiverId}`
  - **Method:** `PUT`
  - **Description:** Marks all notifications as read for a specific receiver.
  - **Response:** `Void`

#### StudentRestController
- **Endpoint:** `/students/current`
  - **Method:** `GET`
  - **Description:** Retrieves the current authenticated student's information.
  - **Response:** `StudentGetCurrentStudentDTO`

- **Endpoint:** `/students/current/classrooms`
  - **Method:** `GET`
  - **Description:** Retrieves the list of classrooms for the current authenticated student.
  - **Response:** List of `ClassroomStudentsClassroomDTO`

- **Endpoint:** `/students/username/{username}`
  - **Method:** `GET`
  - **Description:** Retrieves a student's information by their username.
  - **Response:** `Student`

#### TeachersRestController
- **Endpoint:** `/teachers/add-teacher`
  - **Method:** `PUT`
  - **Description:** Adds a teacher to a classroom.
  - **Response:** Map with status and message.

- **Endpoint:** `/teachers/current-teacher-id/{id}`
  - **Method:** `GET`
  - **Description:** Retrieves the teacher by their ID.
  - **Response:** `GetTeachersIdDTO`

- **Endpoint:** `/teachers/search-usernames`
  - **Method:** `GET`
  - **Description:** Searches for teachers by username.
  - **Response:** List of usernames.

- **Endpoint:** `/teachers/check-classroom-name`
  - **Method:** `GET`
  - **Description:** Checks if a classroom with the given name exists.
  - **Response:** Map with boolean indicating existence.

- **Endpoint:** `/teachers/delete-classroom/{id}`
  - **Method:** `DELETE`
  - **Description:** Deletes a classroom.
  - **Response:** Map with status and message.

- **Endpoint:** `/teachers/classroom/{id}`
  - **Method:** `GET`
  - **Description:** Retrieves the classroom by its ID.
  - **Response:** `ClassroomUpdateDTO`

- **Endpoint:** `/teachers/classroom-read-only/{id}`
  - **Method:** `GET`
  - **Description:** Retrieves the classroom by its ID for read-only view.
  - **Response:** `ClassroomReadOnlyDTO`

- **Endpoint:** `/teachers/update-classroom/{id}`
  - **Method:** `PUT`
  - **Description:** Updates the details of a classroom.
  - **Response:** `Classroom`

- **Endpoint:** `/teachers/create-meeting-date/{classroomId}`
  - **Method:** `POST`
  - **Description:** Creates a new meeting date.
  - **Response:** `MeetingDate`

- **Endpoint:** `/teachers/update-meeting-date/{classroomId}/{meetingDateId}`
  - **Method:** `PUT`
  - **Description:** Updates the date and time of a meeting.
  - **Response:** Message indicating result.

- **Endpoint:** `/teachers/classrooms-with-meetings`
  - **Method:** `GET`
  - **Description:** Retrieves all classrooms with meetings for the current teacher.
  - **Response:** List of `ClassroomFindMeetingsDTO`

- **Endpoint:** `/teachers/delete-meeting/{meetingDateId}`
  - **Method:** `DELETE`
  - **Description:** Deletes a meeting date.
  - **Response:** Map with status and message.

- **Endpoint:** `/teachers/search-students-dynamic`
  - **Method:** `GET`
  - **Description:** Searches for students dynamically by last name and limit.
  - **Response:** List of `SearchStudentDTO`

- **Endpoint:** `/teachers/find-classrooms-by-teacher-id/{teacherId}`
  - **Method:** `GET`
  - **Description:** Retrieves the classrooms by teacher ID.
  - **Response:** List of `Classroom`

- **Endpoint:** `/teachers/find-classrooms-by-student-id/{studentId}`
  - **Method:** `GET`
  - **Description:** Retrieves the classrooms by student ID.
  - **Response:** `SearchStudentToClassroomDTO`

- **Endpoint:** `/teachers/add-student-to-classroom/{classroomId}/student/{studentId}`
  - **Method:** `POST`
  - **Description:** Adds a student to a classroom.
  - **Response:** Message indicating result.

- **Endpoint:** `/teachers/remove-student-from-classroom/{classroomId}/student/{studentId}`
  - **Method:** `DELETE`
  - **Description:** Removes a student from a classroom.
  - **Response:** Message indicating result.


### Controllers

#### StudentsController
- **Endpoint:** `/students/dashboard`
  - **Method:** `GET`
  - **Description:** Displays the student dashboard with the current authenticated student's information.

- **Endpoint:** `/students/classrooms`
  - **Method:** `GET`
  - **Description:** Displays the list of classrooms for the current authenticated student.

#### UnauthorisedErrorController
- **Endpoint:** `/error-403`
  - **Method:** `GET`
  - **Description:** Handles requests to display the unauthorized access error page.

#### TeachersController
- **Endpoint:** `/teachers/dashboard`
  - **Method:** `GET`
  - **Description:** Displays the teacher's dashboard with their classrooms and student search form.

- **Endpoint:** `/teachers/search-students`
  - **Method:** `GET`
  - **Description:** Displays the student search page.

- **Endpoint:** `/teachers/create-classroom`
  - **Method:** `GET`
  - **Description:** Displays the classroom creation page.

- **Endpoint:** `/teachers/create-classroom`
  - **Method:** `POST`
  - **Description:** Handles the submission of the classroom creation form.

- **Endpoint:** `/teachers/your-classrooms`
  - **Method:** `GET`
  - **Description:** Displays the teacher's classrooms with pagination.

#### RegisterController
- **Endpoint:** `/register`
  - **Method:** `GET`
  - **Description:** Displays the registration page.

- **Endpoint:** `/register/role`
  - **Method:** `GET`
  - **Description:** Handles role selection and redirects to the appropriate registration form.

- **Endpoint:** `/register/teacher`
  - **Method:** `GET`
  - **Description:** Displays the teacher registration form.

- **Endpoint:** `/register/teacher`
  - **Method:** `POST`
  - **Description:** Handles the submission of the teacher registration form.

- **Endpoint:** `/register/student`
  - **Method:** `GET`
  - **Description:** Displays the student registration form.

- **Endpoint:** `/register/student`
  - **Method:** `POST`
  - **Description:** Handles the submission of the student registration form.

- **Endpoint:** `/register/admin`
  - **Method:** `GET`
  - **Description:** Displays the admin registration form.

- **Endpoint:** `/register/admin`
  - **Method:** `POST`
  - **Description:** Handles the submission of the admin registration form.

#### PricingController
- **Endpoint:** `/pricing`
  - **Method:** `GET`
  - **Description:** Handles requests to display the pricing page.

#### LoginController
- **Endpoint:** `/login`
  - **Method:** `GET`
  - **Description:** Handles requests to display the login page and redirects authenticated users based on their roles.

- **On Authentication Success:** Redirects authenticated users based on their roles.

- **On Authentication Failure:** Redirects to the login page with an error parameter.

#### HomeController
- **Endpoint:** `/`
  - **Method:** `GET`
  - **Description:** Handles requests to display the home page.

#### FeaturesController
- **Endpoint:** `/features`
  - **Method:** `GET`
  - **Description:** Handles requests to display the features page.

#### ErrorController
- **Handles:** `NoHandlerFoundException` (404 errors), `RuntimeException`, and all other exceptions.
  - **Description:** Global exception handler directing the user to the appropriate error page.

#### CustomErrorController
- **Endpoint:** `/error`
  - **Method:** `REQUEST`
  - **Description:** Handles errors and adds the error message to the model, returning the "error" view.

#### AdminsController
- **Endpoint:** `/admins/dashboard`
  - **Method:** `GET`
  - **Description:** Displays the admin dashboard with the current authenticated admin's information.

- **Endpoint:** `/admins/manage-users`
- **Method:** `GET`
- **Description:** Displays the user management page with a list of users.


### Service Exceptions

#### AdminAlreadyExistsException
- **Description:** Exception thrown when an attempt is made to create an admin that already exists.
- **Constructor:**
  - `AdminAlreadyExistsException(String username)`
    - **Parameters:** `username` - the username of the admin that already exists.
    - **Message:** "Admin with username [username] already exists".

#### ClassroomAlreadyExistsException
- **Description:** Exception thrown when an attempt is made to create a classroom that already exists.
- **Constructor:**
  - `ClassroomAlreadyExistsException(String name)`
    - **Parameters:** `name` - the name of the classroom that already exists.
    - **Message:** "Classroom with name [name] already exists".

#### ClassroomNotFoundException
- **Description:** Exception thrown when a classroom is not found.
- **Constructor:**
  - `ClassroomNotFoundException(String message)`
    - **Parameters:** `message` - the detail message.
    - **Message:** [message]

#### MessageDontFoundException
- **Description:** Exception thrown when a message is not found.
- **Constructor:**
  - `MessageDontFoundException(String message)`
    - **Parameters:** `message` - the detail message.
    - **Message:** [message]

#### NotificationNotFoundException
- **Description:** Exception thrown when a notification is not found.
- **Constructor:**
  - `NotificationNotFoundException(String message)`
    - **Parameters:** `message` - the detail message.
    - **Message:** [message]

#### StudentAlreadyExistsException
- **Description:** Exception thrown when an attempt is made to create a student that already exists.
- **Constructor:**
  - `StudentAlreadyExistsException(String username)`
    - **Parameters:** `username` - the username of the student that already exists.
    - **Message:** "Student with username [username] already exists".

#### StudentNotFoundException
- **Description:** Exception thrown when a student is not found.
- **Constructor:**
  - `StudentNotFoundException(String message)`
    - **Parameters:** `message` - the detail message.
    - **Message:** [message]

#### TeacherAlreadyExistsException
- **Description:** Exception thrown when an attempt is made to create a teacher that already exists.
- **Constructor:**
  - `TeacherAlreadyExistsException(String username)`
    - **Parameters:** `username` - the username of the teacher that already exists.
    - **Message:** "Teacher with username [username] already exists".


### Service Interfaces

#### IAdminService
- **Description:** Service for managing administrators.
- **Methods:**
  - `Admin registerAdmin(RegisterAdminDTO dto) throws AdminAlreadyExistsException`
    - **Description:** Registers a new administrator.
    - **Parameters:** `dto` - the data transfer object containing the information needed to register an admin.
    - **Returns:** The registered admin.
  - `List<Admin> findAllAdmins() throws Exception`
    - **Description:** Retrieves all administrators.
    - **Returns:** A list of all administrators.
  - `AdminGetAuthenticatedAdminDTO getAuthenticatedAdmin(AdminGetAuthenticatedAdminDTO dto) throws AdminNotFoundException`
    - **Description:** Retrieves the currently authenticated admin.
    - **Parameters:** `dto` - the data transfer object containing the information needed to retrieve the authenticated admin.
    - **Returns:** The authenticated admin.
  - `void deleteCurrentAdmin()`
    - **Description:** Deletes the currently authenticated admin.
  - `void updateAdmin(AdminUpdateDTO dto)`
    - **Description:** Updates the currently authenticated admin based on the provided data transfer object.
    - **Parameters:** `dto` - the data transfer object containing the information needed to update the authenticated admin.

#### IClassroomService
- **Description:** Service for managing classrooms.
- **Methods:**
  - `Classroom createClassroom(CreateClassroomDTO classroomDTO)`
    - **Description:** Creates a new classroom.
    - **Parameters:** `classroomDTO` - the data transfer object containing the information needed to create a classroom.
    - **Returns:** The created classroom.
  - `Teacher getCreatorTeacher(Long classroomId)`
    - **Description:** Retrieves the teacher who created the classroom.
    - **Parameters:** `classroomId` - the id of the classroom.
    - **Returns:** The teacher who created the classroom.
  - `List<Student> getStudentsInClassroom(Long classroomId)`
    - **Description:** Retrieves the students in the classroom.
    - **Parameters:** `classroomId` - the id of the classroom.
    - **Returns:** A list of students in the classroom.
  - `List<Classroom> findClassroomsByTeacher(Long teacherId)`
    - **Description:** Retrieves the classrooms by teacher id.
    - **Parameters:** `teacherId` - the id of the teacher.
    - **Returns:** A list of classrooms by teacher id.
  - `List<Classroom> findAllClassrooms()`
    - **Description:** Retrieves all classrooms.
    - **Returns:** A list of all classrooms.
  - `Classroom updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO)`
    - **Description:** Updates a classroom.
    - **Parameters:** `classroomId` - the id of the classroom to update, `classroomDTO` - the data transfer object containing the updated information of the classroom.
    - **Returns:** The updated classroom.
  - `void deleteClassroom(Long classroomId)`
    - **Description:** Deletes a classroom.
    - **Parameters:** `classroomId` - the id of the classroom to delete.
  - `boolean classroomNameExists(String name)`
    - **Description:** Checks if a classroom with the specified name exists.
    - **Parameters:** `name` - the name of the classroom.
    - **Returns:** `true` if a classroom with the specified name exists, `false` otherwise.
  - `void addTeacherToClassroom(Long classroomId, String teacherUsername)`
    - **Description:** Adds a teacher to a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `teacherUsername` - the username of the teacher to add.
  - `Page<Classroom> findClassroomsByTeacher(Long teacherId, Pageable pageable)`
    - **Description:** Retrieves classrooms by teacher id with pagination.
    - **Parameters:** `teacherId` - the id of the teacher, `pageable` - the pagination information.
    - **Returns:** A paginated list of classrooms by teacher id.
  - `Optional<Classroom> findById(Long classroomId) throws Exception`
    - **Description:** Retrieves a classroom by its id.
    - **Parameters:** `classroomId` - the id of the classroom.
    - **Returns:** The classroom with the specified id.
  - `void addTeacherToClassroom(AddTeacherToClassroomDTO dto) throws Exception`
    - **Description:** Adds a teacher to a classroom.
    - **Parameters:** `dto` - the data transfer object containing the information needed to add a teacher to a classroom.
  - `void save(Classroom classroom) throws ClassroomAlreadyExistsException`
    - **Description:** Saves a classroom.
    - **Parameters:** `classroom` - the classroom to save.
  - `Classroom updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO)`
    - **Description:** Updates the details of a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `classroomUpdateDTO` - the data transfer object containing the updated information of the classroom.
    - **Returns:** The updated classroom.
  - `MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO meetingUpdateDTO)`
    - **Description:** Updates a meeting date.
    - **Parameters:** `classroomId` - the id of the classroom, `meetingDateId` - the id of the meeting date, `meetingUpdateDTO` - the data transfer object containing the updated information of the meeting date.
    - **Returns:** The updated meeting date.
  - `List<ClassroomFindMeetingsDTO> getAllClassroomsAndMeetingsForCurrentTeacher()`
    - **Description:** Retrieves all classrooms and meetings for the current teacher.
    - **Returns:** A list of classrooms and their meetings for the current teacher.
  - `void removeStudentFromClassroom(RemoveStudentDTO dto)`
    - **Description:** Removes a student from a classroom.
    - **Parameters:** `dto` - the data transfer object containing the information needed to remove a student from a classroom.
  - `boolean isStudentInClassroom(Long classroomId, Long studentId)`
    - **Description:** Checks if a student is in a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `studentId` - the id of the student.
    - **Returns:** `true` if the student is in the classroom, `false` otherwise.
  - `void addStudentToClassroom(Long classroomId, Long studentId) throws RuntimeException`
    - **Description:** Adds a student to a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `studentId` - the id of the student.
  - `ClassroomReadOnlyDTO getByClassroomId(Long id) throws Exception`
    - **Description:** Retrieves a classroom by its id in read-only mode.
    - **Parameters:** `id` - the id of the classroom.
    - **Returns:** The classroom with the specified id in read-only mode.
  - `List<ClassroomStudentsClassroomDTO> findClassroomsByStudentId(Long studentId)`
    - **Description:** Retrieves the classrooms by student id.
    - **Parameters:** `studentId` - the id of the student.
    - **Returns:** A list of classrooms by student id.
  - `List<Classroom> getAllClassroomsByTeacherId(Long teacherId)`
    - **Description:** Retrieves all classrooms by teacher id.
    - **Parameters:** `teacherId` - the id of the teacher.
    - **Returns:** A list of classrooms by teacher id.

#### IUserService
- **Description:** Service interface for managing users.
- **Methods:**
  - `User getUserByUsername(String username) throws UsernameNotFoundException`
    - **Description:** Retrieves a user by their username.
    - **Parameters:** `username` - the username of the user to be retrieved.
    - **Returns:** The user with the specified username.
  - `User getTeacherIdByUsername(String username)`
    - **Description:** Retrieves a teacher by their username.
    - **Parameters:** `username` - the username of the teacher to be retrieved.
    - **Returns:** The teacher with the specified username.
  - `User getStudentIdByEmail(String email)`
    - **Description:** Retrieves a student by their email.
    - **Parameters:** `email` - the email of the student to be retrieved.
    - **Returns:** The student with the specified email.
  - `User getUserIdByUsername(String username)`
    - **Description:** Retrieves a user by their username.
    - **Parameters:** `username` - the username of the user to be retrieved.
    - **Returns:** The user with the specified username.
  - `Optional<UserGetUsernameDTO> getUsernameById(Long id)`
    - **Description:** Retrieves a user by their id.
    - **Parameters:** `id` - the id of the user to be retrieved.
    - **Returns:** The user with the specified id.
  - `Optional<User> findByUsername(String username)`
    - **Description:** Retrieves a user by their username.
    - **Parameters:** `username` - the username of the user to be retrieved.
    - **Returns:** An optional user with the specified username.
  - `Optional<UserDTO> findUserByUsername(String username)`
    - **Description:** Retrieves a user by their username.
    - **Parameters:** `username` - the username of the user to be retrieved.
    - **Returns:** An optional user with the specified username.
  - `Optional<UserDTO> findUserByEmail(String email)`
    - **Description:** Retrieves a user by their email.
    - **Parameters:** `email` - the email of the user to be retrieved.
    - **Returns:** An optional user with the specified email.
  - `UserDTO findUserById(Long id)`
    - **Description:** Retrieves a user by their id.
    - **Parameters:** `id` - the id of the user to be retrieved.
    - **Returns:** The user with the specified id.
  - `List<UserDTO> findUserByRole(Role role)`
    - **Description:** Retrieves users by their role.
    - **Parameters:** `role` - the role of the users to be retrieved.
    - **Returns:** A list of users with the specified role.
  - `Page<UserDTO> findAllUsers(Pageable pageable)`
    - **Description:** Retrieves all users.
    - **Parameters:** `pageable` - pagination information.
    - **Returns:** A paginated list of all users.
  - `List<UserDTO> findUserByStatus(Status status)`
    - **Description:** Retrieves users by their status.
    - **Parameters:** `status` - the status of the users to be retrieved.
    - **Returns:** A list of users with the specified status.
  - `void deleteUserById(Long id)`
    - **Description:** Deletes a user by their id.
    - **Parameters:** `id` - the id of the user to be deleted.
  - `Optional<User> updateUser(UserUpdateDTO dto)`
    - **Description:** Updates a user.
    - **Parameters:** `dto` - the data transfer object containing the updated information of the user.
    - **Returns:** An optional updated user.
  - `void createUser(UserCreateDTO userCreateDTO)`
    - **Description:** Creates a user.
    - **Parameters:** `userCreateDTO` - the data transfer object containing the information needed to create a user.
  - `Page<UserDTO> searchUsers(String keyword, Pageable pageable)`
    - **Description:** Searches for users based on a keyword.
    - **Parameters:** `keyword` - the keyword to search for, `pageable` - pagination information.
    - **Returns:** A paginated list of users that match the keyword.

#### IMeetingDateService
- **Description:** Service for managing meeting dates.
- **Methods:**
  - `MeetingDate createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO)`
    - **Description:** Creates a meeting date.
    - **Parameters:** `classroomId` - the id of the classroom, `createMeetingDateDTO` - the data for creating the meeting date.
    - **Returns:** The created meeting date.
  - `void deleteMeetingDate(Long meetingDateId)`
    - **Description:** Deletes a meeting date.
    - **Parameters:** `meetingDateId` - the id of the meeting date.
  - `MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto)`
    - **Description:** Updates a meeting date.
    - **Parameters:** `classroomId` - the id of the classroom, `meetingDateId` - the id of the meeting date, `dto` - the data for updating the meeting date.
    - **Returns:** The updated meeting date.

#### IMessageService
- **Description:** Service interface for managing messages.
- **Methods:**
  - `List<GetMessageDTO> getMessagesBySenderId(Long senderId) throws MessageDontFoundException`
    - **Description:** Retrieves messages by the ID of the sender.
    - **Parameters:** `senderId` - the ID of the sender.
    - **Returns:** A list of `GetMessageDTO`.
  - `List<GetMessageDTO> getMessagesByReceiverId(Long receiverId) throws MessageDontFoundException`
    - **Description:** Retrieves messages by the ID of the receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.
    - **Returns:** A list of `GetMessageDTO`.
  - `List<GetMessageDTO> getMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId) throws MessageDontFoundException`
    - **Description:** Retrieves messages by the IDs of both the sender and the receiver.
    - **Parameters:** `senderId` - the ID of the sender, `receiverId` - the ID of the receiver.
    - **Returns:** A list of `GetMessageDTO`.
  - `SendMessageDTO createMessage(SendMessageDTO sendMessageDTO) throws MessageDontFoundException`
    - **Description:** Creates a new message.
    - **Parameters:** `sendMessageDTO` - the message data.
    - **Returns:** The created `SendMessageDTO`.
  - `GetMessageDTO updateMessage(UpdateMessageDTO updateMessageDTO) throws MessageDontFoundException`
    - **Description:** Updates an existing message.
    - **Parameters:** `updateMessageDTO` - the message data to update.
    - **Returns:** The updated `GetMessageDTO`.
  - `void deleteMessage(DeleteMessageDTO deleteMessageDTO) throws MessageDontFoundException`
    - **Description:** Deletes a message.
    - **Parameters:** `deleteMessageDTO` - the message data to delete.
  - `Long getCurrentTeacherId() throws MessageDontFoundException`
    - **Description:** Retrieves the current teacher's ID.
    - **Returns:** The ID of the current teacher.
  - `GetMessageDTO getMessageById(Long id) throws EntityNotFoundException`
    - **Description:** Retrieves a message by its ID.
    - **Parameters:** `id` - the ID of the message.
    - **Returns:** The retrieved `GetMessageDTO`.
  - `List<GetMessageDTO> getMessagesByStudentId(Long studentId)`
    - **Description:** Retrieves messages by the ID of the student.
    - **Parameters:** `studentId` - the ID of the student.
    - **Returns:** A list of `GetMessageDTO`.

#### INotificationService
- **Description:** Service interface for managing notifications.
- **Methods:**
  - `List<GetNotificationDTO> getNotificationsByReceiverId(Long receiverId) throws NotificationNotFoundException`
    - **Description:** Retrieves notifications by the ID of the receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.
    - **Returns:** A list of `GetNotificationDTO`.
  - `GetNotificationDTO createNotification(SendNotificationDTO sendNotificationDTO) throws NotificationNotFoundException`
    - **Description:** Creates a new notification.
    - **Parameters:** `sendNotificationDTO` - the notification data.
    - **Returns:** The created `GetNotificationDTO`.
  - `GetNotificationDTO updateNotification(UpdateNotification updateNotificationDTO) throws NotificationNotFoundException`
    - **Description:** Updates an existing notification.
    - **Parameters:** `updateNotificationDTO` - the notification data to update.
    - **Returns:** The updated `GetNotificationDTO`.
  - `void deleteNotification(DeleteNotification deleteNotificationDTO) throws NotificationNotFoundException`
    - **Description:** Deletes a notification.
    - **Parameters:** `deleteNotificationDTO` - the notification data to delete.
  - `void markNotificationAsRead(Long notificationId) throws NotificationNotFoundException`
    - **Description:** Marks a notification as read.
    - **Parameters:** `notificationId` - the ID of the notification to mark as read.
  - `void markNotificationAsUnread(Long notificationId) throws NotificationNotFoundException`
    - **Description:** Marks a notification as unread.
    - **Parameters:** `notificationId` - the ID of the notification to mark as unread.
  - `void markAllNotificationsAsRead(Long receiverId) throws NotificationNotFoundException`
    - **Description:** Marks all notifications as read for a specific receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.

#### IStudentService
- **Description:** Service interface for managing students.
- **Methods:**
  - `Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException`
    - **Description:** Registers a new student.
    - **Parameters:** `dto` - the DTO containing the student's registration information.
    - **Returns:** The registered student.
  - `List<Student> findAllStudents() throws Exception`
    - **Description:** Retrieves all students.
    - **Returns:** A list of all students.
  - `List<Student> findByLastnameContainingOrderByLastname(String lastname)`
    - **Description:** Searches for students whose last names contain the specified substring, ordered by their last names.
    - **Parameters:** `lastname` - the substring to search for in last names.
    - **Returns:** A list of students whose last names contain the specified substring.
  - `List<Student> searchStudentsByLastname(String lastname)`
    - **Description:** Searches for students by their last names.
    - **Parameters:** `lastname` - the last name to search for.
    - **Returns:** A list of students with the specified last name.
  - `SearchStudentToClassroomDTO getStudentClassrooms(Long studentId) throws ClassroomNotFoundException`
    - **Description:** Retrieves the classrooms associated with a specific student.
    - **Parameters:** `studentId` - the ID of the student.
    - **Returns:** A DTO containing the student's classroom information.
  - `StudentGetCurrentStudentDTO getCurrentAuthenticatedStudent(StudentGetCurrentStudentDTO dto) throws StudentNotFoundException`
    - **Description:** Retrieves the student with the specified ID.
    - **Parameters:** `dto` - the ID of the student.
    - **Returns:** The student with the specified ID.
  - `Student getStudentIdByUsername(String username) throws StudentNotFoundException`
    - **Description:** Retrieves the student with the specified ID.
    - **Parameters:** `username` - the ID of the student.
    - **Returns:** The student with the specified ID.
  - `List<StudentGetUsernameAndIdDTO> findStudentUsernamesAndIdsByClassroomId(Long classroomId)`
    - **Description:** Retrieves the student with the specified classroom ID.
    - **Parameters:** `classroomId` - the ID of the classroom.
    - **Returns:** The student with the specified ID.

#### ITeacherService
- **Description:** Service interface for managing teachers.
- **Methods:**
  - `Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException`
    - **Description:** Registers a new teacher.
    - **Parameters:** `dto` - the data transfer object containing teacher registration information.
    - **Returns:** The registered teacher.
  - `List<Teacher> findAllTeachers() throws Exception`
    - **Description:** Retrieves all teachers.
    - **Returns:** A list of all teachers.
  - `List<Student> searchStudentsByLastName(String lastName)`
    - **Description:** Searches for students by their last name.
    - **Parameters:** `lastName` - the last name to search for.
    - **Returns:** A list of students with matching last names.
  - `Teacher findTeacherByFirstname(String firstname)`
    - **Description:** Finds a teacher by their first name.
    - **Parameters:** `firstname` - the first name to search for.
    - **Returns:** The teacher with the matching first name.
  - `Optional<Teacher> getCurrentAuthenticatedTeacher()`
    - **Description:** Retrieves the currently authenticated teacher.
    - **Returns:** An Optional containing the current teacher if found, otherwise empty.
  - `Optional<Teacher> findByUsername(String username)`
    - **Description:** Retrieves a teacher by their username.
    - **Parameters:** `username` - the username to search for.
    - **Returns:** An Optional containing the teacher with the matching username if found, otherwise empty.
  - `List<Teacher> findByUsernameContaining(String username) throws Exception`
    - **Description:** Retrieves a teacher by their username containing the specified string.
    - **Parameters:** `username` - the string to search for in the username.
    - **Returns:** A list of teachers with usernames containing the specified string.
  - `Optional<GetTeachersIdDTO> findById(Long id) throws Exception`
    - **Description:** Retrieves a teacher by their id.
    - **Parameters:** `id` - the id to search for.
    - **Returns:** An Optional containing the teacher with the matching id if found, otherwise empty.
  - `Teacher getUserIdByTeacherId(Long teacherId)`
    - **Description:** Retrieves a teacher by their id.
    - **Parameters:** `teacherId` - the id to search for.
    - **Returns:** The teacher with the matching id.


### Service Implementations

#### AdminServiceImpl
- **Description:** Implementation of the `IAdminService` interface. Provides methods for registering and retrieving admins.
- **Methods:**
  - `Admin registerAdmin(RegisterAdminDTO dto) throws AdminAlreadyExistsException`
    - **Description:** Registers a new admin.
    - **Parameters:** `dto` - the data transfer object containing admin registration information.
    - **Returns:** The registered admin.
    - **Implementation:**
      - Extracts `Admin` and `User` entities from the `RegisterAdminDTO`.
      - Checks if a user with the same username already exists.
      - Encodes the user's password.
      - Associates the `User` with the `Admin`.
      - Saves the `Admin` entity to the repository.
  - `List<Admin> findAllAdmins() throws Exception`
    - **Description:** Retrieves all admins.
    - **Returns:** A list of all admins.
    - **Implementation:**
      - Fetches all `Admin` entities from the repository.
      - Logs and handles any errors that occur during retrieval.
  - `AdminGetAuthenticatedAdminDTO getAuthenticatedAdmin(AdminGetAuthenticatedAdminDTO dto) throws AdminNotFoundException`
    - **Description:** Retrieves the currently authenticated admin.
    - **Parameters:** `dto` - the data transfer object containing the information needed to retrieve the authenticated admin.
    - **Returns:** The authenticated admin.
    - **Implementation:**
      - Retrieves the username of the authenticated user from the security context.
      - Finds the corresponding `User` entity by username.
      - Retrieves the associated `Admin` entity by username.
      - Maps the `Admin` entity to `AdminGetAuthenticatedAdminDTO` and returns it.
  - `void deleteCurrentAdmin()`
    - **Description:** Deletes the currently authenticated admin.
    - **Implementation:**
      - Retrieves the username of the authenticated user from the security context.
      - Finds the corresponding `User` entity by username.
      - Deletes the associated `Admin` entity and the `User` entity.
      - Logs and handles any errors that occur during deletion.
  - `void updateAdmin(AdminUpdateDTO dto)`
    - **Description:** Updates the currently authenticated admin based on the provided data transfer object.
    - **Parameters:** `dto` - the data transfer object containing the information needed to update the authenticated admin.
    - **Implementation:**
      - Retrieves the username of the authenticated user from the security context.
      - Finds the corresponding `User` and `Admin` entities by username.
      - Maps the `AdminUpdateDTO` to the `Admin` entity.
      - Saves the updated `Admin` and `User` entities to the repository.
      - Logs and handles any errors that occur during the update.

#### ClassroomServiceImpl
- **Description:** Implementation of the `IClassroomService` interface. Provides methods for managing classrooms.
- **Methods:**
  - `Classroom createClassroom(CreateClassroomDTO classroomDTO)`
    - **Description:** Creates a new classroom.
    - **Parameters:** `classroomDTO` - the data transfer object containing the information needed to create a classroom.
    - **Returns:** The created classroom.
  - `Teacher getCreatorTeacher(Long classroomId)`
    - **Description:** Retrieves the teacher who created the classroom.
    - **Parameters:** `classroomId` - the id of the classroom.
    - **Returns:** The teacher who created the classroom.
  - `List<Student> getStudentsInClassroom(Long classroomId)`
    - **Description:** Retrieves the students in the classroom.
    - **Parameters:** `classroomId` - the id of the classroom.
    - **Returns:** A list of students in the classroom.
  - `List<Classroom> findClassroomsByTeacher(Long teacherId)`
    - **Description:** Retrieves the classrooms by teacher id.
    - **Parameters:** `teacherId` - the id of the teacher.
    - **Returns:** A list of classrooms by teacher id.
  - `List<Classroom> findAllClassrooms()`
    - **Description:** Retrieves all classrooms.
    - **Returns:** A list of all classrooms.
  - `Classroom updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO)`
    - **Description:** Updates a classroom.
    - **Parameters:** `classroomId` - the id of the classroom to update, `classroomDTO` - the data transfer object containing the updated information of the classroom.
    - **Returns:** The updated classroom.
  - `void deleteClassroom(Long classroomId)`
    - **Description:** Deletes a classroom.
    - **Parameters:** `classroomId` - the id of the classroom to delete.
  - `boolean classroomNameExists(String name)`
    - **Description:** Checks if a classroom with the specified name exists.
    - **Parameters:** `name` - the name of the classroom.
    - **Returns:** `true` if a classroom with the specified name exists, `false` otherwise.
  - `void addTeacherToClassroom(Long classroomId, String teacherUsername)`
    - **Description:** Adds a teacher to a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `teacherUsername` - the username of the teacher to add.
  - `Page<Classroom> findClassroomsByTeacher(Long teacherId, Pageable pageable)`
    - **Description:** Retrieves classrooms by teacher id with pagination.
    - **Parameters:** `teacherId` - the id of the teacher, `pageable` - the pagination information.
    - **Returns:** A paginated list of classrooms by teacher id.
  - `Optional<Classroom> findById(Long classroomId) throws Exception`
    - **Description:** Retrieves a classroom by its id.
    - **Parameters:** `classroomId` - the id of the classroom.
    - **Returns:** The classroom with the specified id.
  - `void addTeacherToClassroom(AddTeacherToClassroomDTO dto) throws Exception`
    - **Description:** Adds a teacher to a classroom.
    - **Parameters:** `dto` - the data transfer object containing the information needed to add a teacher to a classroom.
  - `void save(Classroom classroom) throws ClassroomAlreadyExistsException`
    - **Description:** Saves a classroom.
    - **Parameters:** `classroom` - the classroom to save.
  - `Classroom updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO)`
    - **Description:** Updates the details of a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `classroomUpdateDTO` - the data transfer object containing the updated information of the classroom.
    - **Returns:** The updated classroom.
  - `MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO meetingUpdateDTO)`
    - **Description:** Updates a meeting date.
    - **Parameters:** `classroomId` - the id of the classroom, `meetingDateId` - the id of the meeting date, `meetingUpdateDTO` - the data transfer object containing the updated information of the meeting date.
    - **Returns:** The updated meeting date.
  - `List<ClassroomFindMeetingsDTO> getAllClassroomsAndMeetingsForCurrentTeacher()`
    - **Description:** Retrieves all classrooms and meetings for the current teacher.
    - **Returns:** A list of classrooms and their meetings for the current teacher.
  - `void removeStudentFromClassroom(RemoveStudentDTO dto)`
    - **Description:** Removes a student from a classroom.
    - **Parameters:** `dto` - the data transfer object containing the information needed to remove a student from a classroom.
  - `boolean isStudentInClassroom(Long classroomId, Long studentId)`
    - **Description:** Checks if a student is in a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `studentId` - the id of the student.
    - **Returns:** `true` if the student is in the classroom, `false` otherwise.
  - `void addStudentToClassroom(Long classroomId, Long studentId) throws RuntimeException`
    - **Description:** Adds a student to a classroom.
    - **Parameters:** `classroomId` - the id of the classroom, `studentId` - the id of the student.
  - `ClassroomReadOnlyDTO getByClassroomId(Long id) throws Exception`
    - **Description:** Retrieves a classroom by its id in read-only mode.
    - **Parameters:** `id` - the id of the classroom.
    - **Returns:** The classroom with the specified id in read-only mode.
  - `List<ClassroomStudentsClassroomDTO> findClassroomsByStudentId(Long studentId)`
    - **Description:** Retrieves the classrooms by student id.
    - **Parameters:** `studentId` - the id of the student.
    - **Returns:** A list of classrooms by student id.
  - `List<Classroom> getAllClassroomsByTeacherId(Long teacherId)`
    - **Description:** Retrieves all classrooms by teacher id.
    - **Parameters:** `teacherId` - the id of the teacher.
    - **Returns:** A list of classrooms by teacher id.

#### MeetingDateServiceImpl
- **Description:** Implementation of the `IMeetingDateService` interface. Provides methods for creating, updating, and deleting meeting dates.
- **Methods:**
  - `MeetingDate createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO)`
    - **Description:** Creates a meeting date.
    - **Parameters:** `classroomId` - the id of the classroom, `createMeetingDateDTO` - the data for creating the meeting date.
    - **Returns:** The created meeting date.
  - `void deleteMeetingDate(Long meetingDateId)`
    - **Description:** Deletes a meeting date.
    - **Parameters:** `meetingDateId` - the id of the meeting date.
  - `MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto)`
    - **Description:** Updates a meeting date.
    - **Parameters:** `classroomId` - the id of the classroom, `meetingDateId` - the id of the meeting date, `dto` - the data for updating the meeting date.
    - **Returns:** The updated meeting date.

#### MessageServiceImpl
- **Description:** Implementation of the `IMessageService` interface. Provides methods for managing messages.
- **Methods:**
  - `List<GetMessageDTO> getMessagesBySenderId(Long senderId) throws MessageDontFoundException`
    - **Description:** Retrieves messages by the ID of the sender.
    - **Parameters:** `senderId` - the ID of the sender.
    - **Returns:** A list of GetMessageDTO.
  - `List<GetMessageDTO> getMessagesByReceiverId(Long receiverId) throws MessageDontFoundException`
    - **Description:** Retrieves messages by the ID of the receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.
    - **Returns:** A list of GetMessageDTO.
  - `List<GetMessageDTO> getMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId) throws MessageDontFoundException`
    - **Description:** Retrieves messages by the IDs of both the sender and the receiver.
    - **Parameters:** `senderId` - the ID of the sender, `receiverId` - the ID of the receiver.
    - **Returns:** A list of GetMessageDTO.
  - `SendMessageDTO createMessage(SendMessageDTO sendMessageDTO) throws MessageDontFoundException`
    - **Description:** Creates a new message.
    - **Parameters:** `sendMessageDTO` - the message data.
    - **Returns:** The created SendMessageDTO.
  - `GetMessageDTO updateMessage(UpdateMessageDTO updateMessageDTO) throws MessageDontFoundException`
    - **Description:** Updates an existing message.
    - **Parameters:** `updateMessageDTO` - the message data to update.
    - **Returns:** The updated GetMessageDTO.
  - `void deleteMessage(DeleteMessageDTO deleteMessageDTO) throws MessageDontFoundException`
    - **Description:** Deletes a message.
    - **Parameters:** `deleteMessageDTO` - the message data to delete.
  - `Long getCurrentTeacherId() throws MessageDontFoundException`
    - **Description:** Retrieves the current teacher's ID.
    - **Returns:** The ID of the current teacher.
  - `GetMessageDTO getMessageById(Long id) throws EntityNotFoundException`
    - **Description:** Retrieves a message by its ID.
    - **Parameters:** `id` - the ID of the message.
    - **Returns:** The retrieved GetMessageDTO.
  - `List<GetMessageDTO> getMessagesByStudentId(Long studentId)`
    - **Description:** Retrieves messages by the ID of the student.
    - **Parameters:** `studentId` - the ID of the student.
    - **Returns:** A list of GetMessageDTO.

#### NotificationServiceImpl
- **Description:** Implementation of the `INotificationService` interface. Provides methods for retrieving, creating, updating, and deleting notifications.
- **Methods:**
  - `List<GetNotificationDTO> getNotificationsByReceiverId(Long receiverId) throws NotificationNotFoundException`
    - **Description:** Retrieves notifications by the ID of the receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.
    - **Returns:** A list of GetNotificationDTO.
  - `GetNotificationDTO createNotification(SendNotificationDTO sendNotificationDTO) throws NotificationNotFoundException`
    - **Description:** Creates a new notification.
    - **Parameters:** `sendNotificationDTO` - the notification data.
    - **Returns:** The created GetNotificationDTO.
  - `GetNotificationDTO updateNotification(UpdateNotification updateNotificationDTO) throws NotificationNotFoundException`
    - **Description:** Updates an existing notification.
    - **Parameters:** `updateNotificationDTO` - the notification data to update.
    - **Returns:** The updated GetNotificationDTO.
  - `void deleteNotification(DeleteNotification deleteNotificationDTO) throws NotificationNotFoundException`
    - **Description:** Deletes a notification.
    - **Parameters:** `deleteNotificationDTO` - the notification data to delete.
  - `void markNotificationAsRead(Long notificationId) throws NotificationNotFoundException`
    - **Description:** Marks a notification as read.
    - **Parameters:** `notificationId` - the ID of the notification to mark as read.
  - `void MarkNotificationAsUnread(Long notificationId) throws NotificationNotFoundException`
    - **Description:** Marks a notification as unread.
    - **Parameters:** `notificationId` - the ID of the notification to mark as unread.
  - `void markAllNotificationsAsRead(Long receiverId) throws NotificationNotFoundException`
    - **Description:** Marks all notifications as read for a specific receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.

#### StudentServiceImpl
- **Description:** Implementation of the `IStudentService` interface. Provides methods for registering and retrieving students.
- **Methods:**
  - `Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException`
    - **Description:** Registers a new student.
    - **Parameters:** `dto` - the data transfer object containing student registration information.
    - **Returns:** The registered student.
  - `List<Student> findAllStudents() throws Exception`
    - **Description:** Retrieves all students.
    - **Returns:** A list of all students.
  - `List<Student> findByLastnameContainingOrderByLastname(String lastname)`
    - **Description:** Searches for students whose last names contain the specified substring, ordered by their last names.
    - **Parameters:** `lastname` - the substring to search for in last names.
    - **Returns:** A list of students whose last names contain the specified substring.
  - `List<Student> searchStudentsByLastname(String lastname)`
    - **Description:** Searches for students by their last names.
    - **Parameters:** `lastname` - the last name to search for.
    - **Returns:** A list of students with the specified last name.
  - `SearchStudentToClassroomDTO getStudentClassrooms(Long studentId) throws ClassroomNotFoundException`
    - **Description:** Retrieves the classrooms associated with a specific student.
    - **Parameters:** `studentId` - the ID of the student.
    - **Returns:** A DTO containing the student's classroom information.
  - `StudentGetCurrentStudentDTO getCurrentAuthenticatedStudent(StudentGetCurrentStudentDTO dto) throws StudentNotFoundException`
    - **Description:** Retrieves the currently authenticated student.
    - **Parameters:** `dto` - the data transfer object containing the information needed to retrieve the authenticated student.
    - **Returns:** The authenticated student.
  - `Student getStudentIdByUsername(String username) throws StudentNotFoundException`
    - **Description:** Retrieves the student with the specified username.
    - **Parameters:** `username` - the username of the student.
    - **Returns:** The student with the specified username.
  - `List<StudentGetUsernameAndIdDTO> findStudentUsernamesAndIdsByClassroomId(Long classroomId)`
    - **Description:** Retrieves the student usernames and IDs by classroom ID.
    - **Parameters:** `classroomId` - the ID of the classroom.
    - **Returns:** A list of student usernames and IDs by classroom ID.

#### TeacherServiceImpl
- **Description:** Implementation of the `ITeacherService` interface. Provides methods for registering and retrieving teachers.
- **Methods:**
  - `Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException`
    - **Description:** Registers a new teacher.
    - **Parameters:** `dto` - the data transfer object containing teacher registration information.
    - **Returns:** The registered teacher.
  - `List<Teacher> findAllTeachers() throws Exception`
    - **Description:** Retrieves all teachers.
    - **Returns:** A list of all teachers.
  - `List<Student> searchStudentsByLastName(String lastName)`
    - **Description:** Searches for students by their last name.
    - **Parameters:** `lastName` - the last name to search for.
    - **Returns:** A list of students with matching last names.
  - `Teacher findTeacherByFirstname(String firstname)`
    - **Description:** Finds a teacher by their first name.
    - **Parameters:** `firstname` - the first name to search for.
    - **Returns:** The teacher with the matching first name.
  - `Optional<Teacher> getCurrentAuthenticatedTeacher()`
    - **Description:** Retrieves the currently authenticated teacher.
    - **Returns:** An `Optional` containing the current teacher if found, otherwise empty.
  - `Optional<Teacher> findByUsername(String username)`
    - **Description:** Retrieves a teacher by their username.
    - **Parameters:** `username` - the username to search for.
    - **Returns:** An `Optional` containing the teacher with the matching username if found, otherwise empty.
  - `List<Teacher> findByUsernameContaining(String username) throws Exception`
    - **Description:** Retrieves a teacher by their username containing the specified string.
    - **Parameters:** `username` - the string to search for in the username.
    - **Returns:** An `Optional` containing the teacher with the matching username if found, otherwise empty.
  - `Optional<GetTeachersIdDTO> findById(Long id) throws Exception`
    - **Description:** Retrieves a teacher by their id.
    - **Parameters:** `id` - the id to search for.
    - **Returns:** An `Optional` containing the teacher with the matching id if found, otherwise empty.
  - `Teacher getUserIdByTeacherId(Long teacherId)`
    - **Description:** Retrieves the teacher with the specified teacher ID.
    - **Parameters:** `teacherId` - the ID of the teacher.
    - **Returns:** The teacher with the specified teacher ID.

#### UserServiceImpl
- **Description:** Implementation of the `IUserService` interface. Provides methods for retrieving user details by username.
- **Methods:**
  - `User getUserByUsername(String username) throws UsernameNotFoundException`
    - **Description:** Retrieves a user by their username.
    - **Parameters:** `username` - the username of the user to be retrieved.
    - **Returns:** The user with the specified username.
  - `User getTeacherIdByUsername(String username)`
    - **Description:** Retrieves a teacher by their username.
    - **Parameters:** `username` - the username of the teacher to be retrieved.
    - **Returns:** The teacher with the specified username.
  - `User getStudentIdByEmail(String email)`
    - **Description:** Retrieves a student by their email.
    - **Parameters:** `email` - the email of the student to be retrieved.
    - **Returns:** The student with the specified email.
  - `User getUserIdByUsername(String username)`
    - **Description:** Retrieves a user by their username.
    - **Parameters:** `username` - the username of the user to be retrieved.
    - **Returns:** The user with the specified username.
  - `Optional<UserGetUsernameDTO> getUsernameById(Long id)`
    - **Description:** Retrieves a user by their id.
    - **Parameters:** `id` - the id of the user to be retrieved.
    - **Returns:** The user with the specified id.


#### Data Transfer Objects (DTOs)
DTOs are used to transfer data between the client and server. Key DTOs include:

### Admin DTOs

### Admin DTOs

#### AdminGetAuthenticatedAdminDTO
- **Description:** Data Transfer Object (DTO) for authenticated admin information.
- **Fields:**
  - `@NotNull private Long id`: The unique identifier of the admin.
  - `private String username`: The username of the admin.

#### RegisterAdminDTO
- **Description:** Data Transfer Object (DTO) for registering an admin.
- **Fields:**
  - `@NotNull @Size(min = 3, max = 32) private String username`: The username of the admin. Must be between 3 and 32 characters long.
  - `@NotNull @Email private String email`: The email address of the admin. Must be a valid email format.
  - `@NotNull @Size(min = 5, max = 32) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{5,32}$") private String password`: The password of the admin. Must be between 5 and 32 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.
  - `@NotNull @Size(min = 5, max = 32) private String confirmPassword`: The password confirmation field. Must be between 5 and 32 characters long.
  - `@NotNull @Size(min = 3, max = 32) private String firstname`: The first name of the admin. Must be between 3 and 32 characters long.
  - `@NotNull @Size(min = 3, max = 32) private String lastname`: The last name of the admin. Must be between 3 and 32 characters long.
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthDate`: The birthdate of the admin. Must be in the format "yyyy-MM-dd".
  - `@NotNull private String country`: The country of the admin.
  - `@NotNull private String city`: The city of the admin.

#### AdminUpdateDTO
- **Description:** Data Transfer Object (DTO) for updating admin information.
- **Fields:**
  - `private String firstname`: The first name of the admin.
  - `private String lastname`: The last name of the admin.
  - `@Size(max = 50) private String username`: The username of the admin. Cannot exceed 50 characters.
  - `@Email private String email`: The email of the admin. Must be a valid email format.
  - `private String password`: The password of the admin.
  - `@DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthDate`: The birthdate of the admin. Must be in the format "yyyy-MM-dd".
  - `private String country`: The country of the admin.
  - `private String city`: The city of the admin.

### Classroom DTOs

#### ClassroomFindMeetingsDTO
- **Description:** Data Transfer Object (DTO) for transferring classroom information along with associated meeting dates.
- **Fields:**
  - `private Long id`: The unique identifier of the classroom.
  - `private String name`: The name of the classroom.
  - `private String description`: A description of the classroom.
  - `private String classroomUrl`: The URL of the classroom.
  - `private String imageUrl`: The image URL of the classroom.
  - `private List<FindMeetingMeetingDateDTO> meetingDates`: A list of meeting dates associated with the classroom.

#### ClassroomReadOnlyDTO
- **Description:** Data Transfer Object (DTO) for transferring read-only classroom information.
- **Fields:**
  - `private Long id`: The unique identifier of the classroom.
  - `private String name`: The name of the classroom.
  - `private String description`: A description of the classroom.
  - `private String classroomUrl`: The URL of the classroom.
  - `private String imageUrl`: The image URL of the classroom.
  - `private boolean isActive`: Indicates whether the classroom is active.
  - `private TeacherReadOnlyDTO creator`: The creator of the classroom.
  - `private List<TeacherReadOnlyDTO> teachers`: A list of teachers associated with the classroom.
  - `private List<StudentReadOnlyDTO> studentsOfClassroom`: A list of students enrolled in the classroom.
  - `private List<MeetingDateReadOnlyDTO> meetingDates`: A list of meeting dates associated with the classroom.
  - `private List<TeacherReadOnlyDTO> extraTeachers`: A list of extra teachers associated with the classroom.

#### ClassroomStudentsClassroomDTO
- **Description:** Data Transfer Object (DTO) for transferring classroom information along with associated students.
- **Fields:**
  - `private Long id`: The unique identifier of the classroom.
  - `private String name`: The name of the classroom.
  - `private String description`: A description of the classroom.
  - `private String classroomUrl`: The URL of the classroom.
  - `private String imageUrl`: The image URL of the classroom.
  - `private boolean isActive`: Indicates whether the classroom is active.
  - `private TeacherReadOnlyDTO creator`: The creator of the classroom.
  - `private List<TeacherReadOnlyDTO> teachers`: A list of teachers associated with the classroom.
  - `private List<StudentReadOnlyDTO> studentsOfClassroom`: A list of students enrolled in the classroom.
  - `private List<MeetingDateReadOnlyDTO> meetingDates`: A list of meeting dates associated with the classroom.
  - `private List<TeacherReadOnlyDTO> extraTeachers`: A list of extra teachers associated with the classroom.

#### ClassroomUpdateDTO
- **Description:** Data Transfer Object (DTO) for updating classroom information.
- **Fields:**
  - `private Long id`: The unique identifier of the classroom.
  - `@NotNull @NotBlank @Size(max = 100) private String name`: The name of the classroom. Mandatory field, cannot exceed 100 characters.
  - `@NotBlank @Size(max = 500) private String description`: A description of the classroom. Mandatory field, cannot exceed 500 characters.
  - `@Size(max = 200) private String classroomUrl`: The URL of the classroom. Optional field, cannot exceed 200 characters.
  - `@Size(max = 200) private String imageUrl`: The image URL of the classroom. Optional field, cannot exceed 200 characters.
  - `private boolean isActive`: Indicates whether the classroom is active. Default value is true.

#### CreateClassroomDTO
- **Description:** Data Transfer Object (DTO) for creating a new classroom.
- **Fields:**
  - `@NotBlank @Size(max = 100) private String name`: The name of the classroom. Mandatory field, cannot exceed 100 characters.
  - `@NotBlank @Size(max = 500) private String description`: A description of the classroom. Mandatory field, cannot exceed 500 characters.
  - `@Size(max = 200) private String classroomUrl`: The URL of the classroom. Optional field, cannot exceed 200 characters.
  - `@Size(max = 200) private String imageUrl`: The image URL of the classroom. Optional field, cannot exceed 200 characters.
  - `private boolean isActive`: Indicates whether the classroom is active. Default value is true.

### MeetingDate DTOs

#### CreateMeetingDateDTO
- **Description:** Data Transfer Object (DTO) for creating a new meeting date.
- **Fields:**
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate date`: The date of the meeting. Mandatory field, must follow the format "yyyy-MM-dd".
  - `@NotNull @DateTimeFormat(pattern = "HH:mm") private LocalTime time`: The starting time of the meeting. Mandatory field, must follow the format "HH:mm".
  - `@NotNull @DateTimeFormat(pattern = "HH:mm") private LocalTime endTime`: The ending time of the meeting. Mandatory field, must follow the format "HH:mm".
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate endDate`: The ending date of the meeting. Mandatory field, must follow the format "yyyy-MM-dd".

#### DeleteMeetingDateDTO
- **Description:** Data Transfer Object (DTO) for deleting a meeting date.
- **Fields:**
  - `private Long id`: The unique identifier of the meeting date.

#### FindMeetingMeetingDateDTO
- **Description:** Data Transfer Object (DTO) for finding meeting dates.
- **Fields:**
  - `private Long id`: The unique identifier for the meeting date.
  - `@DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate date`: The date of the meeting. Must follow the format "yyyy-MM-dd".
  - `@DateTimeFormat(pattern = "HH:mm") private LocalTime time`: The starting time of the meeting. Must follow the format "HH:mm".
  - `@DateTimeFormat(pattern = "HH:mm") private LocalTime endTime`: The ending time of the meeting. Must follow the format "HH:mm".
  - `@DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate endDate`: The ending date of the meeting. Must follow the format "yyyy-MM-dd".

#### MeetingDateDTO
- **Description:** Data Transfer Object (DTO) for a meeting date.
- **Fields:**
  - `private Long id`: The unique identifier of the meeting date.
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate date`: The date of the meeting. Mandatory field, must follow the format "yyyy-MM-dd".
  - `@NotNull @DateTimeFormat(pattern = "HH:mm") private LocalTime time`: The starting time of the meeting. Mandatory field, must follow the format "HH:mm".
  - `@NotNull @DateTimeFormat(pattern = "HH:mm") private LocalTime endTime`: The ending time of the meeting. Mandatory field, must follow the format "HH:mm".
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate endDate`: The ending date of the meeting. Mandatory field, must follow the format "yyyy-MM-dd".

#### MeetingDateReadOnlyDTO
- **Description:** Data Transfer Object (DTO) for reading meeting date information.
- **Fields:**
  - `private Long id`: The unique identifier of the meeting date.
  - `private LocalDate date`: The date of the meeting.
  - `private LocalTime time`: The starting time of the meeting.
  - `private LocalDate endDate`: The ending date of the meeting.
  - `private LocalTime endTime`: The ending time of the meeting.

#### UpdateMeetingDateDTO
- **Description:** Data Transfer Object (DTO) for updating a meeting date.
- **Fields:**
  - `private Long id`: The unique identifier of the meeting date.
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate date`: The date of the meeting. Mandatory field, must follow the format "yyyy-MM-dd".
  - `@NotNull @DateTimeFormat(pattern = "HH:mm") private LocalTime time`: The starting time of the meeting. Mandatory field, must follow the format "HH:mm".
  - `@DateTimeFormat(pattern = "HH:mm") private LocalTime endTime`: The ending time of the meeting. Must follow the format "HH:mm".
  - `@DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate endDate`: The ending date of the meeting. Must follow the format "yyyy-MM-dd".

### Message DTOs

#### DeleteMessageDTO
- **Description:** Data Transfer Object for deleting a message.
- **Fields:**
  - `@NotNull private Long id`: The ID of the message to be deleted. Must not be null.

#### GetMessageDTO
- **Description:** Data Transfer Object for retrieving a message.
- **Fields:**
  - `private Long id`: The ID of the message.
  - `private String content`: The content of the message.
  - `private LocalDateTime timestamp`: The timestamp when the message was sent.
  - `private Long senderId`: The ID of the sender.
  - `private String senderUsername`: The username of the sender.
  - `private Long receiverId`: The ID of the receiver.
  - `private String receiverUsername`: The username of the receiver.

#### MessageDTO
- **Description:** Data Transfer Object for messages.
- **Fields:**
  - `private Long id`: The ID of the message.
  - `private String content`: The content of the message.
  - `private LocalDateTime timestamp`: The timestamp when the message was sent.
  - `private Long senderId`: The ID of the sender.
  - `private Long receiverId`: The ID of the receiver.

#### SendMessageDTO
- **Description:** Data Transfer Object for sending a message.
- **Fields:**
  - `@NotNull @Size(min = 1, max = 500) private String content`: The content of the message. Must not be null and must be between 1 and 500 characters.
  - `@NotNull private Long senderId`: The ID of the sender. Must not be null.
  - `@NotNull private Long receiverId`: The ID of the receiver. Must not be null.

#### UpdateMessageDTO
- **Description:** Data Transfer Object for updating a message.
- **Fields:**
  - `@NotNull private Long id`: The ID of the message to be updated. Must not be null.
  - `@NotBlank private String content`: The updated content of the message. Must not be blank.
  - `@NotNull private Long senderId`: The ID of the sender. Must not be null.
  - `@NotNull private Long receiverId`: The ID of the receiver. Must not be null.

### Notification DTOs

#### DeleteNotification
- **Description:** Data Transfer Object for deleting a notification.
- **Fields:**
  - `@NotNull private Long id`: The ID of the notification to be deleted. Must not be null.

#### GetNotificationDTO
- **Description:** Data Transfer Object for retrieving a notification.
- **Fields:**
  - `private Long id`: The ID of the notification.
  - `private String content`: The content of the notification.
  - `private String senderUsername`: The sender of the notification.
  - `private LocalDateTime timestamp`: The timestamp when the notification was sent.
  - `private Long senderId`: The sender of the notification.
  - `private Long receiverId`: The receiver of the notification.
  - `private Long messageId`: The message ID of the notification.
  - `private Notify notify`: The notification status.

#### SendNotificationDTO
- **Description:** Data Transfer Object for sending a notification.
- **Fields:**
  - `private Long id`: The ID of the notification.
  - `@NotNull @Size(min = 1, max = 500) private String content`: The content of the notification. Must not be null and must be between 1 and 500 characters.
  - `@NotNull private Long senderId`: The ID of the sender. Must not be null.
  - `@NotNull private Long receiverId`: The ID of the receiver. Must not be null.
  - `@NotNull private Long messageId`: The ID of the related message. Must not be null.
  - `private Notify notify`: The notification status. Defaults to `Notify.UNREAD`.

#### UpdateNotification
- **Description:** Data Transfer Object for updating a notification.
- **Fields:**
  - `@NotNull private Long id`: The ID of the notification to be updated. Must not be null.
  - `@NotNull @Size(min = 1, max = 500) private String content`: The updated content of the notification. Must not be null and must be between 1 and 500 characters.
  - `private Notify notify`: The status of the notification. Defaults to `Notify.READ`.

### Student DTOs

#### AddStudentToClassroomDTO
- **Description:** Data Transfer Object (DTO) for adding a student to a classroom.
- **Fields:**
  - `private Long studentId`: The unique identifier of the student.
  - `private Long classroomId`: The unique identifier of the classroom.

#### RegisterStudentDTO
- **Description:** Data Transfer Object (DTO) for registering a student.
- **Fields:**
  - `@NotNull @Size(min = 3, max = 32) private String username`: The username of the student. Must be between 3 and 32 characters long.
  - `@NotNull @Email private String email`: The email address of the student. Must be a valid email format.
  - `@NotNull @Size(min = 5, max = 32) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{5,32}$") private String password`: The password of the student. Must be between 5 and 32 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.
  - `@NotNull @Size(min = 5, max = 32) private String confirmPassword`: The password confirmation field. Must be between 5 and 32 characters long.
  - `@NotNull @Size(min = 3, max = 32) private String firstname`: The first name of the student. Must be between 3 and 32 characters long.
  - `@NotNull @Size(min = 3, max = 32) private String lastname`: The last name of the student. Must be between 3 and 32 characters long.
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthDate`: The birth date of the student. Must be in the format "yyyy-MM-dd".
  - `@NotNull private String country`: The country of the student.
  - `@NotNull private String city`: The city of the student.

#### RemoveStudentDTO
- **Description:** Data Transfer Object (DTO) for removing a student from a classroom.
- **Fields:**
  - `@NotNull private Long classroomId`: The unique identifier of the classroom. Mandatory field.
  - `@NotNull private Long studentId`: The unique identifier of the student. Mandatory field.

#### SearchStudentDTO
- **Description:** Data Transfer Object (DTO) for searching students.
- **Fields:**
  - `private Long id`: The unique identifier for the student.
  - `private String firstname`: The first name of the student.
  - `private String lastname`: The last name of the student.
  - `private List<Long> classroomIds`: A list of classroom IDs associated with the student.
  - `private String email`: The email address of the student.
  - `private String country`: The country of the student.

#### SearchStudentToClassroomDTO
- **Description:** Data Transfer Object (DTO) for searching students to classrooms.
- **Fields:**
  - `@NotNull private Long studentId`: The unique identifier for the student. Mandatory field.
  - `private String studentFirstname`: The first name of the student.
  - `private String studentLastname`: The last name of the student.
  - `private List<ClassroomDTO> classrooms`: A list of classrooms associated with the student.

##### ClassroomDTO (nested inside SearchStudentToClassroomDTO)
- **Description:** Data Transfer Object (DTO) for classroom details.
- **Fields:**
  - `private Long id`: The unique identifier for the classroom.
  - `private String name`: The name of the classroom.

#### SearchTeacherDTO
- **Description:** Data Transfer Object (DTO) for searching teachers.
- **Fields:**
  - `private Long id`: The unique identifier for the teacher.
  - `private String firstname`: The first name of the teacher.
  - `private String lastname`: The last name of the teacher.

#### StudentGetCurrentStudentDTO
- **Description:** Data Transfer Object for getting the current student.
- **Fields:**
  - `@NotNull private Long id`: The ID of the student.
  - `private String username`: The username of the student.

#### StudentGetUsernameAndIdDTO
- **Description:** Data Transfer Object for getting the student's username and ID.
- **Fields:**
  - `private Long id`: The ID of the student.
  - `private String username`: The username of the student.

#### StudentReadOnlyDTO
- **Description:** Data Transfer Object (DTO) for reading student information.
- **Fields:**
  - `private Long id`: The unique identifier of the student.
  - `private String firstname`: The first name of the student.
  - `private String lastname`: The last name of the student.
  - `private String email`: The email address of the student.
  - `private String country`: The country of the student.
  - `private String city`: The city of the student.

### Teacher DTOs

#### AddTeacherToClassroomDTO
- **Description:** Data Transfer Object (DTO) for adding a teacher to a classroom.
- **Fields:**
  - `@NotNull private Long classroomId`: The unique identifier of the classroom. Mandatory field.
  - `@NotNull private String teacherUsername`: The username of the teacher. Mandatory field.

#### GetTeachersIdDTO
- **Description:** Data Transfer Object (DTO) for getting the unique identifier of a teacher.
- **Fields:**
  - `@NotNull private Long id`: The unique identifier of the teacher. Mandatory field.
  - `@NotNull private String firstname`: The first name of the teacher. Mandatory field.
  - `@NotNull private String lastname`: The last name of the teacher. Mandatory field.

#### RegisterTeacherDTO
- **Description:** Data Transfer Object (DTO) for registering a teacher.
- **Fields:**
  - `@NotNull @Size(min = 3, max = 32) private String username`: The username of the teacher. Must be between 3 and 32 characters long.
  - `@NotNull @Email private String email`: The email address of the teacher. Must be a valid email format.
  - `@NotNull @Size(min = 5, max = 32) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{5,32}$") private String password`: The password of the teacher. Must be between 5 and 32 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.
  - `@NotNull @Size(min = 5, max = 32) private String confirmPassword`: The password confirmation field. Must be between 5 and 32 characters long.
  - `@NotNull @Size(min = 3, max = 32) private String firstname`: The first name of the teacher. Must be between 3 and 32 characters long.
  - `@NotNull @Size(min = 3, max = 32) private String lastname`: The last name of the teacher. Must be between 3 and 32 characters long.
  - `@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthDate`: The birth date of the teacher. Must be in the format "yyyy-MM-dd".
  - `@NotNull private String country`: The country of the teacher.
  - `@NotNull private String city`: The city of the teacher.

#### TeacherReadOnlyDTO
- **Description:** Data Transfer Object (DTO) for reading teacher information.
- **Fields:**
  - `private Long id`: The unique identifier of the teacher.
  - `private String firstname`: The first name of the teacher.
  - `private String lastname`: The last name of the teacher.
  - `private String email`: The email address of the teacher.
  - `private String username`: The username of the teacher.

### User DTOs

#### UserGetUsernameDTO
- **Description:** Data Transfer Object for getting the user's username.
- **Fields:**
  - `private Long id`: The ID of the user.
  - `private String username`: The username of the user.

#### UserCreateDTO
- **Description:** Data Transfer Object (DTO) for creating a new user.
- **Fields:**
  - `private Long id`: The unique identifier of the user.
  - `private String firstname`: The first name of the user.
  - `private String lastname`: The last name of the user.
  - `@Enumerated(EnumType.STRING) private Role role`: The role of the user.
  - `@Enumerated(EnumType.STRING) private Status status`: The status of the user.
  - `@NotBlank private String username`: The username of the user.
  - `@NotBlank private String email`: The email of the user.
  - `private String password`: The password of the user.
  - `@Transient private String confirmPassword`: The confirmation password of the user.
  - `@DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthDate`: The birthdate of the user.
  - `private String country`: The country of the user.
  - `private String city`: The city of the user.
  - `private Boolean isActive`: Indicates whether the user is active.
  - `private LocalDateTime createdAt`: The date and time the user was created.

#### UserDTO
- **Description:** Data Transfer Object (DTO) for user information.
- **Fields:**
  - `private Long id`: The ID of the user.
  - `private String firstname`: The first name of the user.
  - `private String lastname`: The last name of the user.
  - `@Enumerated(EnumType.STRING) private Role role`: The role of the user.
  - `@Enumerated(EnumType.STRING) private Status status`: The status of the user.
  - `private String username`: The username of the user.
  - `private String email`: The email of the user.

#### UserUpdateDTO
- **Description:** Data Transfer Object (DTO) for updating user information.
- **Fields:**
  - `private Long id`: The ID of the user.
  - `private String firstname`: The first name of the user.
  - `private String lastname`: The last name of the user.
  - `@Enumerated(EnumType.STRING) private Role role`: The role of the user.
  - `@Enumerated(EnumType.STRING) private Status status`: The status of the user.
  - `private Boolean active`: Indicates whether the user is active.
  - `private String username`: The username of the user.
  - `private String email`: The email of the user.

#### Repositories
Repositories interact with the database to perform CRUD operations. Key repositories include:

### Repository Interfaces

#### AdminRepository
- **Description:** Repository interface for managing `Admin` entities. Extends `JpaRepository` to provide CRUD operations.
- **Methods:**
  - `Optional<Admin> findByUserUsername(String username)`
    - **Description:** Finds an admin by the username.
    - **Parameters:** `username` - the username of the admin.
    - **Returns:** The admin with the given username.

#### ClassroomRepository
- **Description:** Repository interface for managing `Classroom` entities. Extends `JpaRepository` to provide CRUD operations.
- **Methods:**
  - `boolean existsByName(String name)`
    - **Description:** Checks if a classroom with the given name exists.
    - **Parameters:** `name` - the name of the classroom.
    - **Returns:** `true` if a classroom with the given name exists, `false` otherwise.
  - `Page<Classroom> findByTeachers_Id(Long teacherId, Pageable pageable)`
    - **Description:** Finds classrooms by the teacher's ID.
    - **Parameters:** `teacherId` - the ID of the teacher, `pageable` - the pagination information.
    - **Returns:** A paginated list of classrooms associated with the given teacher.
  - `Optional<Classroom> findById(Long classroomId)`
    - **Description:** Finds a classroom by its ID.
    - **Parameters:** `classroomId` - the ID of the classroom.
    - **Returns:** The classroom with the given ID.
  - `List<Classroom> findByCreatorOrTeachersContaining(Teacher creator, Teacher teacher)`
    - **Description:** Finds classrooms by the creator or teachers.
    - **Parameters:** `creator` - the creator of the classroom, `teacher` - a teacher associated with the classroom.
    - **Returns:** A list of classrooms with the given creator or teacher.
  - `boolean existsByIdAndStudentsOfClassroom_Id(Long classroomId, Long studentId)`
    - **Description:** Checks if a classroom with the given ID and student exists.
    - **Parameters:** `classroomId` - the ID of the classroom, `studentId` - the ID of the student.
    - **Returns:** `true` if a classroom with the given ID and student exists, `false` otherwise.
  - `@Query("SELECT c FROM Classroom c JOIN c.studentsOfClassroom s WHERE s.id = :studentId") List<Classroom> findClassroomsByStudentsOfClassroom_Id(@Param("studentId") Long studentId)`
    - **Description:** Finds classrooms by the student's ID.
    - **Parameters:** `studentId` - the ID of the student.
    - **Returns:** A list of classrooms associated with the given student.

#### MeetingDateRepository
- **Description:** Repository interface for managing `MeetingDate` entities. Extends `JpaRepository` to provide CRUD operations.
- **Methods:**
  - `Optional<MeetingDate> findById(Long id)`
    - **Description:** Finds a meeting date by its ID.
    - **Parameters:** `id` - the ID of the meeting date.
    - **Returns:** The meeting date with the given ID.
  - `List<MeetingDate> findByClassroom(Classroom classroom)`
    - **Description:** Finds meeting dates by the classroom.
    - **Parameters:** `classroom` - the classroom associated with the meeting dates.
    - **Returns:** A list of meeting dates associated with the given classroom.

#### MessagesRepository
- **Description:** Repository interface for managing `Message` entities. Provides methods for querying messages by sender, receiver, and specific criteria.
- **Methods:**
  - `List<Message> findBySenderId(Long senderId)`
    - **Description:** Finds messages by the ID of the sender.
    - **Parameters:** `senderId` - the ID of the sender.
    - **Returns:** A list of messages sent by the specified sender.
  - `List<Message> findByReceiverId(Long receiverId)`
    - **Description:** Finds messages by the ID of the receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.
    - **Returns:** A list of messages received by the specified receiver.
  - `List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId)`
    - **Description:** Finds messages by the IDs of both the sender and the receiver.
    - **Parameters:** `senderId` - the ID of the sender, `receiverId` - the ID of the receiver.
    - **Returns:** A list of messages between the specified sender and receiver.
  - `@Query("SELECT m FROM Message m WHERE m.receiver.id = :studentId") List<Message> findMessagesByStudentId(@Param("studentId") Long studentId)`
    - **Description:** Finds messages by the ID of the student who is the receiver.
    - **Parameters:** `studentId` - the ID of the student.
    - **Returns:** A list of messages received by the specified student.

#### NotificationRepository
- **Description:** Repository interface for managing `Notification` entities. Provides methods for querying notifications by receiver ID.
- **Methods:**
  - `List<Notification> findByReceiverId(Long receiverId)`
    - **Description:** Finds notifications by the ID of the receiver.
    - **Parameters:** `receiverId` - the ID of the receiver.
    - **Returns:** A list of notifications received by the specified receiver.

#### StudentRepository
- **Description:** Repository interface for managing `Student` entities. Extends `JpaRepository` to provide CRUD operations.
- **Methods:**
  - `List<Student> findByLastnameContainingOrderByLastname(String lastName)`
    - **Description:** Finds students by the last name.
    - **Parameters:** `lastName` - the last name of the student.
    - **Returns:** A list of students with the given last name, ordered by last name.
  - `List<Student> findByLastnameContainingIgnoreCase(String lastname)`
    - **Description:** Finds students by the last name, case insensitive.
    - **Parameters:** `lastname` - the last name of the student.
    - **Returns:** A list of students with the given last name, case insensitive.
  - `Optional<Student> findByUserUsername(String username)`
    - **Description:** Finds a student by the username.
    - **Parameters:** `username` - the username of the student.
    - **Returns:** The student with the given username.
  - `@Query("SELECT new gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetUsernameAndIdDTO(s.id, s.user.username) FROM Student s JOIN s.classrooms c WHERE c.id = :classroomId") List<StudentGetUsernameAndIdDTO> findStudentUsernamesAndIdsByClassroomId(Long classroomId)`
    - **Description:** Finds students by the classroom ID.
    - **Parameters:** `classroomId` - the ID of the classroom.
    - **Returns:** A list of students in the classroom with the given ID.

#### TeacherRepository
- **Description:** Repository interface for managing `Teacher` entities. Extends `JpaRepository` to provide CRUD operations.
- **Methods:**
  - `Optional<Teacher> findByUserUsername(String username)`
    - **Description:** Finds a teacher by the username.
    - **Parameters:** `username` - the username of the teacher.
    - **Returns:** The teacher with the given username.
  - `List<Teacher> findByUserUsernameContaining(String username)`
    - **Description:** Finds teachers by the username, containing a specific string.
    - **Parameters:** `username` - the string to search for in the username.
    - **Returns:** A list of teachers with the given string in their username.
  - `Teacher findByFirstname(String firstname)`
    - **Description:** Finds a teacher by their first name.
    - **Parameters:** `firstname` - the first name of the teacher.
    - **Returns:** The teacher with the given first name.
  - `@Query("SELECT t.user.username FROM Teacher t WHERE t.id = :teacherId") String findUsernameByTeacherId(Long teacherId)`
    - **Description:** Finds the username of a teacher by their ID.
    - **Parameters:** `teacherId` - the ID of the teacher.
    - **Returns:** The username of the teacher with the given ID.
  - `@Query("SELECT t.user.id FROM Teacher t WHERE t.id = :teacherId") Long findUserIdByTeacherId(Long teacherId)`
    - **Description:** Finds the user ID of a teacher by their ID.
    - **Parameters:** `teacherId` - the ID of the teacher.
    - **Returns:** The user ID of the teacher with the given ID.

#### UserRepository
- **Description:** Repository interface for managing `User` entities. Extends `JpaRepository` to provide CRUD operations.
- **Methods:**
  - `Optional<User> findByUsername(String username)`
    - **Description:** Finds a user by the username.
    - **Parameters:** `username` - the username of the user.
    - **Returns:** The user with the given username.
  - `User findTeacherIdByUsername(String username)`
    - **Description:** Finds a teacher by the username.
    - **Parameters:** `username` - the username of the teacher.
    - **Returns:** The teacher with the given username.
  - `User findStudentIdByEmail(String email)`
    - **Description:** Finds a student by the email.
    - **Parameters:** `email` - the email of the student.
    - **Returns:** The student with the given email.
  - `User findUserIdByUsername(String username)`
    - **Description:** Finds a user by the username.
    - **Parameters:** `username` - the username of the user.
    - **Returns:** The user ID with the given username.
  - `@Query("SELECT new gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO(u.id, u.username) FROM User u WHERE u.id = :id") Optional<UserGetUsernameDTO> findUsernameById(Long id)`
    - **Description:** Finds a username by the user ID.
    - **Parameters:** `id` - the ID of the user.
    - **Returns:** The username with the given ID.
  - `User findUserByUsername(String username)`
    - **Description:** Finds a user by the username.
    - **Parameters:** `username` - the username of the user.
    - **Returns:** The user with the given username.
  - `User findUserByEmail(String email)`
    - **Description:** Finds a user by the email.
    - **Parameters:** `email` - the email of the user.
    - **Returns:** The user with the given email.
  - `User findUserById(Long id)`
    - **Description:** Finds a user by the ID.
    - **Parameters:** `id` - the ID of the user.
    - **Returns:** The user with the given ID.
  - `List<User> findUserByRole(Role role)`
    - **Description:** Finds users by the role.
    - **Parameters:** `role` - the role of the users.
    - **Returns:** A list of users with the given role.
  - `List<User> findUserByStatus(Status status)`
    - **Description:** Finds users by the status.
    - **Parameters:** `status` - the status of the users.
    - **Returns:** A list of users with the given status.
  - `@Modifying @Transactional @Query("DELETE FROM User u WHERE u.id = :id") void deleteById(@Param("id") Long id)`
    - **Description:** Deletes a user by the ID.
    - **Parameters:** `id` - the ID of the user to be deleted.
  - `Boolean existsByUsername(String username)`
    - **Description:** Checks if a user exists by the username.
    - **Parameters:** `username` - the username of the user.
    - **Returns:** `true` if a user with the given username exists, `false` otherwise.
  - `@Query("SELECT u FROM User u LEFT JOIN u.teacher t LEFT JOIN u.student s LEFT JOIN u.admin a WHERE (:keyword IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(CAST(u.role AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(CAST(u.status AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.lastname) LIKE LOWER(CONCAT('%', :keyword, '%'))) Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable)`
    - **Description:** Searches for users by a keyword.
    - **Parameters:** `keyword` - the keyword to search for, `pageable` - the pagination information.
    - **Returns:** A paginated list of users that match the keyword.


#### Mappers
Mappers convert between DTOs and entities. The `Mapper` class includes methods like:

### Mapper Classes

#### UserMapper
- **Description:** Mapper for the `User` entity. Contains methods for converting `User` entities to various DTOs and vice versa.
- **Methods:**
  - `UserDTO mapUserToUserDTO(User user)`
    - **Description:** Converts a `User` entity to a `UserDTO`.
    - **Parameters:** `user` - The `User` entity to convert.
    - **Returns:** The `UserDTO`.
  - `User mapUserDTOToUser(UserDTO dto)`
    - **Description:** Converts a `UserDTO` to a `User` entity.
    - **Parameters:** `dto` - The `UserDTO` to convert.
    - **Returns:** The `User` entity.
  - `User mapUserUpdateDTOToUser(UserUpdateDTO userUpdateDTO)`
    - **Description:** Converts a `UserUpdateDTO` to a `User` entity.
    - **Parameters:** `userUpdateDTO` - The `UserUpdateDTO` to convert.
    - **Returns:** The `User` entity.
  - `UserUpdateDTO toUserUpdateDTO(User user)`
    - **Description:** Converts a `User` entity to a `UserUpdateDTO`.
    - **Parameters:** `user` - The `User` entity to convert.
    - **Returns:** The `UserUpdateDTO`.
  - `User updateUserFromDto(UserUpdateDTO dto, User user)`
    - **Description:** Updates a `User` entity from a `UserUpdateDTO`.
    - **Parameters:** `dto` - The `UserUpdateDTO`, `user` - The `User` entity to update.
    - **Returns:** The updated `User` entity.
  - `UserCreateDTO toUserCreateDTO(User user)`
    - **Description:** Converts a `User` entity to a `UserCreateDTO`.
    - **Parameters:** `user` - The `User` entity to convert.
    - **Returns:** The `UserCreateDTO`.
  - `User toUser(UserCreateDTO dto)`
    - **Description:** Converts a `UserCreateDTO` to a `User` entity.
    - **Parameters:** `dto` - The `UserCreateDTO` to convert.
    - **Returns:** The `User` entity.
  - `Teacher toTeacher(User user, UserCreateDTO dto)`
    - **Description:** Converts a `User` entity and a `UserCreateDTO` to a `Teacher` entity.
    - **Parameters:** `user` - The `User` entity, `dto` - The `UserCreateDTO`.
    - **Returns:** The `Teacher` entity.
  - `Student toStudent(User user, UserCreateDTO dto)`
    - **Description:** Converts a `User` entity and a `UserCreateDTO` to a `Student` entity.
    - **Parameters:** `user` - The `User` entity, `dto` - The `UserCreateDTO`.
    - **Returns:** The `Student` entity.
  - `Admin toAdmin(User user, UserCreateDTO dto)`
    - **Description:** Converts a `User` entity and a `UserCreateDTO` to an `Admin` entity.
    - **Parameters:** `user` - The `User` entity, `dto` - The `UserCreateDTO`.
    - **Returns:** The `Admin` entity.

#### AdminMapper
- **Description:** Mapper for the `Admin` entity. Contains methods for converting an `Admin` entity to a DTO.
- **Methods:**
  - `AdminGetAuthenticatedAdminDTO toAdminGetAuthenticatedAdminDTO(Admin admin) throws AdminNotFoundException`
    - **Description:** Converts an `Admin` entity to an `AdminGetAuthenticatedAdminDTO`.
    - **Parameters:** `admin` - The `Admin` entity to convert.
    - **Returns:** The `AdminGetAuthenticatedAdminDTO`.
    - **Throws:** `AdminNotFoundException` if the `Admin` entity is null.
  - `Admin mapAdminDTOToAdmin(AdminUpdateDTO dto, Admin existingAdmin)`
    - **Description:** Maps an `AdminUpdateDTO` to an existing `Admin` entity.
    - **Parameters:** `dto` - The `AdminUpdateDTO` to map, `existingAdmin` - The existing `Admin` entity to update.
    - **Returns:** The updated `Admin` entity.

#### ClassroomMapper
- **Description:** Mapper class for converting between Classroom-related DTOs and entities.
- **Methods:**
  - `Classroom toClassroom(CreateClassroomDTO dto, Long creatorId)`
    - **Description:** Converts a `CreateClassroomDTO` to a `Classroom` entity.
    - **Parameters:** `dto` - the `CreateClassroomDTO`, `creatorId` - the ID of the creator.
    - **Returns:** The `Classroom` entity.
  - `void updateClassroomFromDTO(ClassroomUpdateDTO dto, Classroom classroom)`
    - **Description:** Updates a `Classroom` entity from a `ClassroomUpdateDTO`.
    - **Parameters:** `dto` - the `ClassroomUpdateDTO`, `classroom` - the `Classroom` entity.
  - `MeetingDate toMeetingDate(CreateMeetingDateDTO dto)`
    - **Description:** Converts a `CreateMeetingDateDTO` to a `MeetingDate` entity.
    - **Parameters:** `dto` - the `CreateMeetingDateDTO`.
    - **Returns:** The `MeetingDate` entity.
  - `ClassroomFindMeetingsDTO toClassroomFindMeetingsDTO(Classroom classroom)`
    - **Description:** Converts a `Classroom` entity to a `ClassroomFindMeetingsDTO`.
    - **Parameters:** `classroom` - the `Classroom` entity.
    - **Returns:** The `ClassroomFindMeetingsDTO`.
  - `ClassroomReadOnlyDTO toReadOnlyDTO(Classroom classroom)`
    - **Description:** Converts a `Classroom` entity to a `ClassroomReadOnlyDTO`.
    - **Parameters:** `classroom` - the `Classroom` entity.
    - **Returns:** The `ClassroomReadOnlyDTO`.
  - `ClassroomStudentsClassroomDTO toClassroomStudentsClassroomDTO(Classroom classroom)`
    - **Description:** Converts a `Classroom` entity to a `ClassroomStudentsClassroomDTO`.
    - **Parameters:** `classroom` - the `Classroom` entity.
    - **Returns:** The `ClassroomStudentsClassroomDTO`.
  - `List<ClassroomStudentsClassroomDTO> toClassroomStudentsClassroomDTOList(List<Classroom> classrooms)`
    - **Description:** Converts a list of `Classroom` entities to a list of `ClassroomStudentsClassroomDTO`.
    - **Parameters:** `classrooms` - the list of `Classroom` entities.
    - **Returns:** The list of `ClassroomStudentsClassroomDTO`.

#### Mapper
- **Description:** Provides methods to convert DTOs to domain models.
- **Methods:**
  - `Teacher extractTeacherFromRegisterTeacherDTO(RegisterTeacherDTO dto)`
    - **Description:** Converts a `RegisterTeacherDTO` to a `Teacher` model.
    - **Parameters:** `dto` - the `RegisterTeacherDTO` to convert.
    - **Returns:** The corresponding `Teacher` model.
  - `User extractUserFromRegisterTeacherDTO(RegisterTeacherDTO dto)`
    - **Description:** Converts a `RegisterTeacherDTO` to a `User` model with TEACHER role.
    - **Parameters:** `dto` - the `RegisterTeacherDTO` to convert.
    - **Returns:** The corresponding `User` model with TEACHER role.
  - `Student extractStudentFromRegisterStudentDTO(RegisterStudentDTO dto)`
    - **Description:** Converts a `RegisterStudentDTO` to a `Student` model.
    - **Parameters:** `dto` - the `RegisterStudentDTO` to convert.
    - **Returns:** The corresponding `Student` model.
  - `User extractUserFromRegisterStudentDTO(RegisterStudentDTO dto)`
    - **Description:** Converts a `RegisterStudentDTO` to a `User` model with STUDENT role.
    - **Parameters:** `dto` - the `RegisterStudentDTO` to convert.
    - **Returns:** The corresponding `User` model with STUDENT role.
  - `Admin extractAdminFromRegisterAdminDTO(RegisterAdminDTO dto)`
    - **Description:** Converts a `RegisterAdminDTO` to an `Admin` model.
    - **Parameters:** `dto` - the `RegisterAdminDTO` to convert.
    - **Returns:** The corresponding `Admin` model.
  - `User extractUserFromRegisterAdminDTO(RegisterAdminDTO dto)`
    - **Description:** Converts a `RegisterAdminDTO` to a `User` model with ADMIN role.
    - **Parameters:** `dto` - the `RegisterAdminDTO` to convert.
    - **Returns:** The corresponding `User` model with ADMIN role.
  - `GetTeachersIdDTO extractGetTeachersIdDTOFromTeacher(Teacher teacher)`
    - **Description:** Converts a `Teacher` model to a `GetTeachersIdDTO`.
    - **Parameters:** `teacher` - the `Teacher` model to convert.
    - **Returns:** The corresponding `GetTeachersIdDTO`.

#### MeetingDateMapper
- **Description:** Mapper class for converting between MeetingDate-related DTOs and entities.
- **Methods:**
  - `void updateMeetingDateFromDTO(UpdateMeetingDateDTO dto, MeetingDate meetingDate)`
    - **Description:** Updates a `MeetingDate` entity from a `UpdateMeetingDateDTO`.
    - **Parameters:** `dto` - the `UpdateMeetingDateDTO`, `meetingDate` - the `MeetingDate` entity.
  - `FindMeetingMeetingDateDTO toMeetingDateFindMeetingsDTO(MeetingDate meetingDate)`
    - **Description:** Converts a `MeetingDate` entity to a `FindMeetingMeetingDateDTO`.
    - **Parameters:** `meetingDate` - the `MeetingDate` entity.
    - **Returns:** The `FindMeetingMeetingDateDTO`.

#### MessageMapper
- **Description:** Mapper for the `Message` entity. Contains methods for converting a `Message` entity to a DTO and vice versa.
- **Methods:**
  - `GetMessageDTO toGetMessageDTO(Message message)`
    - **Description:** Converts a `Message` entity to a `GetMessageDTO`.
    - **Parameters:** `message` - the `Message` entity to convert.
    - **Returns:** The converted `GetMessageDTO`.
  - `List<GetMessageDTO> toGetMessageDTOList(List<Message> messages)`
    - **Description:** Converts a list of `Message` entities to a list of `GetMessageDTOs`.
    - **Parameters:** `messages` - the list of `Message` entities to convert.
    - **Returns:** The converted list of `GetMessageDTOs`.
  - `Message toMessage(SendMessageDTO sendMessageDTO)`
    - **Description:** Converts a `SendMessageDTO` to a `Message` entity.
    - **Parameters:** `sendMessageDTO` - the `SendMessageDTO` to convert.
    - **Returns:** The converted `Message` entity.

#### NotificationMapper
- **Description:** Mapper for the `Notification` entity. Contains methods for converting a `Notification` entity to a DTO and vice versa.
- **Methods:**
  - `GetNotificationDTO toGetNotificationDTO(Notification notification)`
    - **Description:** Converts a `Notification` entity to a `GetNotificationDTO`.
    - **Parameters:** `notification` - the `Notification` entity to convert.
    - **Returns:** The converted `GetNotificationDTO`.
  - `List<GetNotificationDTO> notificationsToGetNotificationDTOs(List<Notification> notifications)`
    - **Description:** Converts a list of `Notification` entities to a list of `GetNotificationDTOs`.
    - **Parameters:** `notifications` - the list of `Notification` entities to convert.
    - **Returns:** The converted list of `GetNotificationDTOs`.
  - `Notification sendNotificationDTOToNotification(SendNotificationDTO sendNotificationDTO)`
    - **Description:** Converts a `SendNotificationDTO` to a `Notification` entity.
    - **Parameters:** `sendNotificationDTO` - the `SendNotificationDTO` to convert.
    - **Returns:** The converted `Notification` entity.
  - `GetNotificationDTO notificationToGetNotificationDTO(Notification notification)`
    - **Description:** Converts a `Notification` entity to a `GetNotificationDTO`.
    - **Parameters:** `notification` - the `Notification` entity to convert.
    - **Returns:** The converted `GetNotificationDTO`.
  - `SendNotificationDTO toSendNotificationDTO(Message message, String content)`
    - **Description:** Converts a `Message` entity and content to a `SendNotificationDTO`.
    - **Parameters:** `message` - the `Message` entity related to the notification, `content` - the content of the notification.
    - **Returns:** The converted `SendNotificationDTO`.

#### StudentMapper
- **Description:** Mapper class for converting between Student-related DTOs and entities.
- **Methods:**
  - `SearchStudentDTO toSearchStudentDTO(Student student)`
    - **Description:** Converts a `Student` entity to a `SearchStudentDTO`.
    - **Parameters:** `student` - the `Student` entity.
    - **Returns:** The `SearchStudentDTO`.
  - `SearchStudentToClassroomDTO toSearchStudentToClassroomDTO(Student student)`
    - **Description:** Converts a `Student` entity to a `SearchStudentToClassroomDTO`.
    - **Parameters:** `student` - the `Student` entity.
    - **Returns:** The `SearchStudentToClassroomDTO`.
  - `StudentGetCurrentStudentDTO toGetCurrentStudentDTO(Student student)`
    - **Description:** Converts a `Student` entity to a `StudentGetCurrentStudentDTO`.
    - **Parameters:** `student` - the `Student` entity.
    - **Returns:** The `StudentGetCurrentStudentDTO`.


#### Enums
Enums are used for predefined constants, such as roles and status within the application.

#### Notify
- **Description:** Enum for the notification status.
- **Values:**
  - `READ`
    - **Description:** The notification has been read.
  - `UNREAD`
    - **Description:** The notification has not been read.

#### Role
- **Description:** Enumeration representing the different roles in the system.
- **Values:**
  - `STUDENT`
    - **Description:** Represents a student role.
  - `TEACHER`
    - **Description:** Represents a teacher role.
  - `ADMIN`
    - **Description:** Represents an admin role.

#### Status
- **Description:** Enumeration representing the different statuses in the system.
- **Values:**
  - `PENDING`
    - **Description:** Represents a pending status.
  - `APPROVED`
    - **Description:** Represents an approved status.
  - `REJECTED`
    - **Description:** Represents a rejected status.


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

### Thymeleaf Templates

#### adminsDashboard.html

- `navbar` (fragment): Replaces the navigation bar with the defined navbar component.
- `showSection(event, sectionId)`: Displays the specified section and hides the current active section.
- `showSpinner(event, this)`: Shows a loading spinner and redirects after a delay.
- Sections include:
  - `Manage Users`
  - `Manage Roles`
  - `System Settings`

#### components/emailUs.html

- `email-us` (fragment): A container encouraging users to email for support, includes a call-to-action button.

#### components/footer.html

- `footer` (fragment): Footer of the webpage with navigation links and copyright information.

#### components/navbar.html

- `navbar` (fragment): Navigation bar with links for home, features, pricing, and contact. Conditional login/logout links based on authentication status.

#### components/someWords.html

- `grid` (fragment): Displays benefits of the school management system and the ability to access it from anywhere, with call-to-action buttons.

#### features.html

- `features.css`: Custom styles for the features page.
- Sections include:
  - `User Management`
  - `Course and Classroom Management`
  - `Communication`
  - `Attendance and Performance Tracking`
  - `Reporting and Analytics`
  - `Security Features`
  - `User Interface and Experience`
  - `Integrations`
  - `Notifications and Alerts`
  - `Administrative Tools`
  - `Documentation and Support`

#### index.html

- `navbar` (fragment): Navigation bar.
- `custom-header`: Header with a welcome message and call-to-action buttons.
- Sections include:
  - `Why Use a School Management System?`
  - `Why Choose Us?`
  - `components/someWords` (fragment)
  - `components/pricing` (fragment)
  - `components/emailUs` (fragment)
- `footer` (fragment): Footer of the webpage.

#### login.html

- `navbar` (fragment): Navigation bar.
- `login.css`: Custom styles for the login page.
- Login form with fields for username and password.
- Conditional error and success messages.
- `footer` (fragment): Footer of the webpage.

#### pricing.html

- `navbar` (fragment): Navigation bar.
- Simple placeholder content indicating the pricing page.
- `footer` (fragment): Footer of the webpage.

#### register.html

- `navbar` (fragment): Navigation bar.
- Registration role selection form with options for student, teacher, and admin.
- `footer` (fragment): Footer of the webpage.

#### registerAdmin.html

- `navbar` (fragment): Navigation bar.
- Registration form for admins with fields for username, email, password, confirm password, first name, last name, birth date, country, and city.
- Conditional error and success messages.
- `footer` (fragment): Footer of the webpage.

#### registerStudent.html

- `navbar` (fragment): Navigation bar.
- Registration form for students with fields for username, email, password, confirm password, first name, last name, birth date, country, and city.
- Conditional error and success messages.
- `footer` (fragment): Footer of the webpage.

#### registerTeacher.html

- `navbar` (fragment): Navigation bar.
- Registration form for teachers with fields for username, email, password, confirm password, first name, last name, birth date, country, and city.
- Conditional error and success messages.
- `footer` (fragment): Footer of the webpage.

#### studentsClassroom.html

- `navbar` (fragment): Navigation bar.
- Displays all classrooms a student is enrolled in.
- Displays meeting details for each classroom.
- Displays classmates for each classroom.
- `footer` (fragment): Footer of the webpage.

#### studentsDashboard.html

- `navbar` (fragment): Navigation bar.
- Dashboard for students with options to view classrooms, send messages to teachers, view messages from teachers, and view notifications.
- `footer` (fragment): Footer of the webpage.

#### teachersCreateClassroom.html

- `navbar` (fragment): Navigation bar.
- Form for teachers to create a classroom with fields for classroom name, description, URL, and image URL.
- `footer` (fragment): Footer of the webpage.

#### teachersDashboard.html

- `navbar` (fragment): Navigation bar.
- Dashboard for teachers with options to search students, create classrooms, view classrooms and meetings, send messages to students, view messages from students, and view notifications.
- `footer` (fragment): Footer of the webpage.

#### teachersSearchStudents.html

- `navbar` (fragment): Navigation bar.
- Form for teachers to search for students by last name and limit the number of results.
- Displays search results with options to add or remove students from classrooms.
- `footer` (fragment): Footer of the webpage.


#### Static Resources
JavaScript and CSS files are located in the static directory.

#### JavaScript Files

The application uses several JavaScript files to manage various client-side functionalities, including form validation, animations, and AJAX requests.

#### adminsDashboard.js

- `checkClassroomNameExists(name)`: Checks if a classroom name already exists in the database.
- `validateField(field)`: Validates a single form field based on defined rules and checks if the classroom name is unique.
- `validateForm()`: Validates all form fields and checks their overall validity.
- `form.addEventListener('submit', async function (event) {...})`: Handles form submission, validates the form, and either shows an error message or submits the form after a delay.
- `showCustomErrorMessage(message)`: Displays a custom error message.
- `showCustomSuccessMessage(message)`: Displays a custom success message.

#### features.js

- `showSection(event, sectionId)`: Displays a specified section and hides the current active section with transition effects.
- `activateNewSection(sectionId)`: Activates and shows a new section with transition effects.

#### index.js

- `setInterval(() => {...}, 3000)`: Toggles the visibility of the section title every 3 seconds.

#### login.js

- `form.addEventListener('submit', event => {...})`: Validates the form, shows a spinner, and submits the form after a delay if valid.

#### registerAdmin.js, registerStudent.js, registerTeacher.js

- `document.addEventListener('DOMContentLoaded', function() {...})`: Redirects to the login page after 3 seconds if a success alert is present.

#### replyMessage.js

- `fetch('http://localhost:8080/messages/current/teacher')`: Fetches the current teacher's information.
- `openReplyModal(messageId)`: Opens a reply modal and populates it with the current message details.
- `sendReply()`: Sends a reply message.

#### searchStudents.js

- `searchStudents()`: Searches for students based on the entered last name and limit.
- `showError(message)`: Displays an error message.
- `fetchTeacherClassrooms(students)`: Fetches the classrooms for the current teacher and updates the results with the students.
- `addStudentToClassroom(studentId, classroomId, student)`: Adds a student to a classroom.
- `removeStudentFromClassroom(studentId, classroomId, student)`: Removes a student from a classroom.
- `refreshStudentClassrooms(studentId)`: Refreshes the list of classrooms for a student.

#### studentMessages.js

- `fetch('http://localhost:8080/messages/current/student')`: Fetches the current student's information.
- `fetchMessagesByUserId(userId)`: Fetches messages by user ID.
- `populateMessagesList(messages)`: Populates the list of messages grouped by sender.
- `showReplyForm(message)`: Displays a form to reply to a message.
- `sendReply(receiverId)`: Sends a reply message.

#### studentClassroom.js

- `calculateRemainingTime(startDate, startTime)`: Calculates the remaining time until a meeting starts.
- `document.addEventListener('DOMContentLoaded', function() {...})`: Initializes the countdown timers for meeting dates.

#### studentDashboard.js

- `getCurrentStudentId()`: Retrieves the current student's ID.
- `getTeacherIdByUsername(username)`: Retrieves the teacher's ID by their username.
- `createMessage(message)`: Creates and sends a message.
- `fetchAndDisplayEntitiesForMessages()`: Fetches and displays entities for sending messages.
- `createMessageToSelectedUser()`: Creates a message to the selected user.
- `validateMessageContent(content)`: Validates the message content length.
- `showSection(event, sectionId)`: Displays a specified section and hides the current active section.
- `fetchAndDisplayMessages()`: Fetches and displays messages for the current student.

#### studentNotifications.js

- `fetch('http://localhost:8080/messages/current/student')`: Fetches the current student's information.
- `fetchNotifications(userId)`: Fetches notifications for the current user.
- `updateNotificationCount(notifications)`: Updates the notification count display.
- `displayNotifications(notifications)`: Displays the list of notifications.
- `viewMessage(messageId)`: Views a specific message.
- `sendReply(receiverId)`: Sends a reply message.
- `deleteNotification(notificationId)`: Deletes a notification.
- `markNotificationAsRead(notificationId)`: Marks a notification as read.
- `markNotificationAsUnread(notificationId)`: Marks a notification as unread.

#### teachersConversation.js

- `fetch('http://localhost:8080/messages/current/teacher')`: Fetches the current teacher's information.
- `fetchMessages(teacherId)`: Fetches messages for the teacher.
- `displayMessageList(messages)`: Displays the list of messages grouped by sender.
- `openConversationModal(studentId, studentUsername)`: Opens a modal to view the conversation with a student.
- `handleReplyFormSubmit(event)`: Handles the submission of the reply form.

#### teachersDashboard.js

- `showSection(event, sectionId)`: Displays a specified section and hides the current active section.
- `showSpinner(event, button)`: Shows a spinner and redirects after a delay.
- `fetchClassroomsAndMeetings()`: Fetches classrooms and meetings for the teacher.
- `showUpdateModal(meetingId, classroomId, date, time, endDate, endTime, active)`: Displays the update meeting modal.
- `updateMeetingDetails()`: Updates meeting details.
- `deleteMeeting(meetingId)`: Deletes a meeting.

#### teachersMessages.js

- `fetch('http://localhost:8080/messages/current/teacher')`: Fetches the current teacher's information.
- `fetchClassroomsByTeacherId(teacherId)`: Fetches classrooms for the teacher.
- `populateUsernamesSelect(usernames)`: Populates a dropdown with usernames.
- `createMessageToSelectedUser()`: Creates a message to the selected user.

#### admin-create-user.js
- **Description:** Handles the functionality for creating a new user through a modal form.
- **Event Listeners:**
  - `DOMContentLoaded`: Initializes the event listeners when the DOM content is fully loaded.
  - `create-users-btn`: Shows the create user modal when the button is clicked.
  - `createUserForm submit`: Handles form submission for creating a new user.
- **Methods:**
  - `fetch('/admin/create', {...})`: Sends a POST request to create a new user.
  - `alert(data.message)`: Shows a success message after user creation.
  - `alert('Error creating user: ' + error.message)`: Shows an error message if user creation fails.

#### manage-users.js
- **Description:** Handles the functionality for managing users, including loading, updating, and deleting users.
- **Event Listeners:**
  - `DOMContentLoaded`: Initializes the event listeners when the DOM content is fully loaded.
  - `load-users-btn`: Loads the users when the button is clicked.
  - `previous-page`: Loads the previous page of users.
  - `next-page`: Loads the next page of users.
  - `search-input input`: Loads the users based on the search query.
- **Methods:**
  - `loadUsers(page)`: Fetches and displays users based on the current page and search query.
  - `openUpdateUserModal(userData)`: Opens the update user modal and populates it with user data.
  - `handleUpdate(userId)`: Fetches user data and opens the update user modal.
  - `handleDelete(userId)`: Deletes a user after confirmation.
  - `fetch('/admin/search?${params.toString()}')`: Sends a GET request to search for users.
  - `fetch('/admin/get/id/${userId}')`: Sends a GET request to get user data by ID.
  - `fetch('/admin/update/${userId}', {...})`: Sends a PATCH request to update a user.
  - `fetch('/admin/delete/${userId}', { method: 'DELETE' })`: Sends a DELETE request to delete a user.

#### teachersNotification.js

- `fetch('http://localhost:8080/messages/current/teacher')`: Fetches the current teacher's information.
- `fetchNotifications(userId)`: Fetches notifications for the teacher.
- `updateNotificationCount(notifications)`: Updates the notification count display.
- `displayNotifications(notifications)`: Displays the list of notifications.
- `deleteNotification(notificationId)`: Deletes a notification.
- `markNotificationAsRead(notificationId)`: Marks a notification as read.
- `markNotificationAsUnread(notificationId)`: Marks a notification as unread.

#### teachersReceiveMessages.js

- `fetch('http://localhost:8080/messages/current/teacher')`: Fetches the current teacher's information.
- `fetchMessages(teacherId)`: Fetches messages for the teacher.
- `displayMessages(messages)`: Displays the list of received messages.

#### teachersYourClassrooms.js

- `showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer)`: Shows a spinner and hides buttons.
- `hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer)`: Hides the spinner and shows buttons.
- `openAddTeacherModal(classroomId)`: Opens the add teacher modal.
- `addTeacher()`: Adds a teacher to a classroom.
- `searchClassrooms()`: Searches classrooms based on the input.
- `openDeleteModal(button)`: Opens the delete classroom modal.
- `deleteClassroom(classroomId)`: Deletes a classroom.
- `searchTeacherUsernames()`: Searches for teacher usernames dynamically.
- `openUpdateClassroomModal(classroomId)`: Opens the update classroom modal.
- `updateClassroomDetails()`: Updates classroom details.
- `openSetMeetingModal(classroomId)`: Opens the set meeting modal.
- `setMeeting()`: Sets a meeting.
- `viewClassroomDetails(classroomId)`: Views classroom details.
- `openRemoveStudentModal(classroomId, studentId)`: Opens the remove student modal.
- `removeStudent()`: Removes a student from a classroom.


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
