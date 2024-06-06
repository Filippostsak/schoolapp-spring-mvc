package gr.aueb.cf.schoolappspringbootmvc.rest;
import gr.aueb.cf.schoolappspringbootmvc.dto.message.DeleteMessageDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.message.GetMessageDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.message.SendMessageDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.message.UpdateMessageDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetCurrentStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetUsernameAndIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.IMessageService;
import gr.aueb.cf.schoolappspringbootmvc.service.IStudentService;
import gr.aueb.cf.schoolappspringbootmvc.service.ITeacherService;
import gr.aueb.cf.schoolappspringbootmvc.service.IUserService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for handling message-related requests.
 * Provides endpoints for retrieving, sending, updating, and deleting messages.
 */

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageRestController {

    private final IMessageService messageService;
    private final IUserService userService;
    private final IStudentService studentService;
    private final ITeacherService teacherService;

    /**
     * Retrieves the list of messages sent by a specific user by their ID.
     *
     * @param senderId the ID of the sender
     * @return a ResponseEntity containing the list of GetMessageDTO
     */

    @Operation(summary = "Get messages by sender ID", description = "Retrieves the list of messages sent by a specific user by their ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<GetMessageDTO>> getMessagesBySenderId(@PathVariable Long senderId) {
        try {
            List<GetMessageDTO> messages = messageService.getMessagesBySenderId(senderId);
            return ResponseEntity.ok(messages);
        } catch (EntityNotFoundException e) {
            log.error("Messages not found for sender id: {}", senderId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the list of messages received by a specific user by their ID.
     *
     * @param receiverId the ID of the receiver
     * @return a ResponseEntity containing the list of GetMessageDTO
     */

    @Operation(summary = "Get messages by receiver ID", description = "Retrieves the list of messages received by a specific user by their ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<GetMessageDTO>> getMessagesByReceiverId(@PathVariable Long receiverId) {
        try {
            List<GetMessageDTO> messages = messageService.getMessagesByReceiverId(receiverId);
            return ResponseEntity.ok(messages);
        } catch (EntityNotFoundException e) {
            log.error("Messages not found for receiver id: {}", receiverId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the list of messages between a specific sender and receiver by their IDs.
     *
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @return a ResponseEntity containing the list of GetMessageDTO
     */

    @Operation(summary = "Get messages by sender and receiver ID", description = "Retrieves the list of messages between a specific sender and receiver by their IDs")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public ResponseEntity<List<GetMessageDTO>> getMessagesBySenderIdAndReceiverId(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            List<GetMessageDTO> messages = messageService.getMessagesBySenderIdAndReceiverId(senderId, receiverId);
            return ResponseEntity.ok(messages);
        } catch (EntityNotFoundException e) {
            log.error("Messages not found for sender id: {} and receiver id: {}", senderId, receiverId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the list of messages received by a specific student by their ID.
     *
     * @param sendMessageDTO the ID of the student
     * @return a ResponseEntity containing the list of GetMessageDTO
     */

    @Operation(summary = "Get messages by student ID or teacher ID", description = "Retrieves the list of messages received by a specific student or teacher by their ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @PostMapping("/send")
    public ResponseEntity<SendMessageDTO> sendMessage(@Validated @RequestBody SendMessageDTO sendMessageDTO) {
        try {
            SendMessageDTO sentMessage = messageService.createMessage(sendMessageDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
        } catch (EntityNotFoundException e) {
            log.error("Error creating message: {}", sendMessageDTO, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Unexpected error creating message: {}", sendMessageDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Updates a message with the specified ID.
     *
     * @param updateMessageDTO the UpdateMessageDTO containing the message ID and updated content
     * @return a ResponseEntity containing the updated GetMessageDTO
     */

    @Operation(summary = "Update message", description = "Updates a message with the specified ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated message",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Message not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully updated message",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Message not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @PutMapping("/update")
    public ResponseEntity<GetMessageDTO> updateMessage(@Validated @RequestBody UpdateMessageDTO updateMessageDTO) {
        try {
            GetMessageDTO updatedMessage = messageService.updateMessage(updateMessageDTO);
            return ResponseEntity.ok(updatedMessage);
        } catch (EntityNotFoundException e) {
            log.error("Error updating message: {}", updateMessageDTO, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Deletes a message with the specified ID.
     *
     * @param deleteMessageDTO the DeleteMessageDTO containing the message ID
     * @return a ResponseEntity with no content
     */

    @Operation(summary = "Delete message", description = "Deletes a message with the specified ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Successfully deleted message",
            content = @Content), @ApiResponse(responseCode = "404", description = "Message not found", content = @Content), @ApiResponse(responseCode = "204", description = "Successfully deleted message",
            content = @Content), @ApiResponse(responseCode = "404", description = "Message not found", content = @Content)})

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMessage(@Validated @RequestBody DeleteMessageDTO deleteMessageDTO) {
        try {
            messageService.deleteMessage(deleteMessageDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            log.error("Error deleting message: {}", deleteMessageDTO, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves the list of messages sent by a specific user by their username.
     *
     * @param username the username of the sender
     * @return a ResponseEntity containing the list of GetMessageDTO
     */

    @Operation(summary = "Get messages by sender username", description = "Retrieves the list of messages sent by a specific user by their username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @GetMapping("/teacher/{username}")
    public ResponseEntity<Long> getTeacherIdByUsername(@PathVariable String username) {
        try {
            Long teacherId = userService.getTeacherIdByUsername(username).getId();
            return ResponseEntity.ok(teacherId);
        } catch (EntityNotFoundException e) {
            log.error("Teacher not found with username: {}", username, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the list of messages received by a specific user by their username.
     *
     * @param email the username of the receiver
     * @return a ResponseEntity containing the list of GetMessageDTO
     */

    @Operation(summary = "Get messages by receiver username", description = "Retrieves the list of messages received by a specific user by their username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved messages",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Messages not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})

    @GetMapping("/student/{email}")
    public ResponseEntity<Long> getStudentIdByEmail(@PathVariable String email) {
        try {
            Long studentId = userService.getStudentIdByEmail(email).getId();
            return ResponseEntity.ok(studentId);
        } catch (EntityNotFoundException e) {
            log.error("Student not found with email: {}", email, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the ID of the current authenticated teacher.
     *
     * @return a ResponseEntity containing the teacher ID
     */

    @Operation(summary = "Get current teacher ID", description = "Retrieves the ID of the current authenticated teacher")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved teacher ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved teacher ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)})

    @GetMapping("/current/teacher")
    public ResponseEntity<Long> getCurrentTeacherId() {
        try {
            Long teacherId = messageService.getCurrentTeacherId();
            return ResponseEntity.ok(teacherId);
        } catch (EntityNotFoundException e) {
            log.error("Teacher not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the ID of the current authenticated student.
     *
     * @return a ResponseEntity containing the student ID
     */

    @Operation(summary = "Get current student ID", description = "Retrieves the ID of the current authenticated student")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved student ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "Student not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved student ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)})

    @GetMapping("/current/student")
    public ResponseEntity<StudentGetCurrentStudentDTO> StudentGetCurrentStudentDTO(StudentGetCurrentStudentDTO dto) {
        try {
            StudentGetCurrentStudentDTO currentStudent = studentService.getCurrentAuthenticatedStudent(new StudentGetCurrentStudentDTO());
            return ResponseEntity.ok(currentStudent);
        } catch (StudentNotFoundException e) {
            log.error("Error retrieving current authenticated student", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the ID of the current authenticated user.
     *
     * @return a ResponseEntity containing the user ID
     */

    @Operation(summary = "Get current user ID", description = "Retrieves the ID of the current authenticated user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved user ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved user ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})

    @GetMapping("/username/{username}")
    public ResponseEntity<Student> getStudentIdByUsername(@PathVariable String username) {
        try {
            Student student = studentService.getStudentIdByUsername(username);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            log.error("Error retrieving student by username: {}", username, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the list of student usernames and IDs for a specific classroom by its ID.
     *
     * @param classroomId the ID of the classroom
     * @return a ResponseEntity containing the list of StudentGetUsernameAndIdDTO
     */

    @Operation(summary = "Get student usernames and IDs by classroom ID", description = "Retrieves the list of student usernames and IDs for a specific classroom by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved student usernames and IDs",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = StudentGetUsernameAndIdDTO.class))), @ApiResponse(responseCode = "404", description = "Student usernames and IDs not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved student usernames and IDs",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = StudentGetUsernameAndIdDTO.class))), @ApiResponse(responseCode = "404", description = "Student usernames and IDs not found", content = @Content)})

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<StudentGetUsernameAndIdDTO>> findStudentUsernamesAndIdsByClassroomId(@PathVariable Long classroomId) {
        try{
            List<StudentGetUsernameAndIdDTO> studentGetUsernameAndIdDTOS = studentService.findStudentUsernamesAndIdsByClassroomId(classroomId);
            return ResponseEntity.ok(studentGetUsernameAndIdDTOS);
        } catch (Exception e) {
            log.error("Error retrieving student usernames and ids by classroom id: {}", classroomId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the ID of the user with the specified username.
     *
     * @param username the username of the user
     * @return a ResponseEntity containing the user ID
     */

    @Operation(summary = "Get user ID by username", description = "Retrieves the ID of the user with the specified username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved user ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved user ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})

    @GetMapping("/user/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        try {
            Long userId = userService.getUserIdByUsername(username).getId();
            return ResponseEntity.ok(userId);
        } catch (EntityNotFoundException e) {
            log.error("User not found with username: {}", username, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the message with the specified ID.
     *
     * @param id the ID of the message
     * @return a ResponseEntity containing the GetMessageDTO
     */

    @Operation(summary = "Get message by ID", description = "Retrieves the message with the specified ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved message",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Message not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved message",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetMessageDTO.class))), @ApiResponse(responseCode = "404", description = "Message not found", content = @Content)})

    @GetMapping("message/{id}")
    public ResponseEntity<GetMessageDTO> getMessageById(@PathVariable Long id) {
        try {
            GetMessageDTO message = messageService.getMessageById(id);
            return ResponseEntity.ok(message);
        } catch (EntityNotFoundException e) {
            log.error("Message not found with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the ID of the teacher with the specified username.
     *
     * @param teacherId the username of the teacher
     * @return a ResponseEntity containing the teacher ID
     */

    @Operation(summary = "Get user ID by teacherId", description = "Retrieves the ID of the user with the specified teacherId")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved teacher ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved teacher ID",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))), @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content)})

    @GetMapping("/{teacherId}/userId")
    public ResponseEntity<Teacher> getUserIdByTeacherId(@PathVariable Long teacherId) {
        try{
            Teacher teacher = teacherService.getUserIdByTeacherId(teacherId);
            return ResponseEntity.ok(teacher);
        } catch (Exception e) {
            log.error("Error fetching user ID for teacher ID: {}", teacherId, e);
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Retrieves the username of the user with the specified ID.
     *
     * @param id the ID of the user
     * @return a ResponseEntity containing the UserGetUsernameDTO
     */

    @Operation(summary = "Get user name by ID", description = "Retrieves the username of the user with the specified ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved user name",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserGetUsernameDTO.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content), @ApiResponse(responseCode = "200", description = "Successfully retrieved user name",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserGetUsernameDTO.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})

    @GetMapping("/get-user-name/{id}")
    public ResponseEntity<Optional<UserGetUsernameDTO>> getUsernameById(@PathVariable Long id) {
        try {
            Optional<UserGetUsernameDTO> userGetUsernameDTO = userService.getUsernameById(id);
            return ResponseEntity.ok(userGetUsernameDTO);
        } catch (EntityNotFoundException e) {
            log.error("User not found with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
