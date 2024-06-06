package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomStudentsClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetCurrentStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.IStudentService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling student-related requests.
 * Provides endpoints for retrieving student information and their classrooms.
 */

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentRestController {

    private final IStudentService studentService;
    private final IClassroomService classroomService;

    /**
     * Retrieves the current authenticated student's information.
     *
     * @return a ResponseEntity containing the StudentGetCurrentStudentDTO
     */
    @Operation(summary = "Get current authenticated student", description = "Retrieves the current authenticated student's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved current authenticated student",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentGetCurrentStudentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/current")
    public ResponseEntity<StudentGetCurrentStudentDTO> getCurrentAuthenticatedStudent() {
        try {
            StudentGetCurrentStudentDTO currentStudent = studentService.getCurrentAuthenticatedStudent(new StudentGetCurrentStudentDTO());
            return ResponseEntity.ok(currentStudent);
        } catch (StudentNotFoundException e) {
            log.error("Error retrieving current authenticated student", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves the list of classrooms for the current authenticated student.
     *
     * @return a ResponseEntity containing the list of ClassroomStudentsClassroomDTO
     */
    @Operation(summary = "Get classrooms for current student", description = "Retrieves the list of classrooms for the current authenticated student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved classrooms for current student",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassroomStudentsClassroomDTO.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/current/classrooms")
    public ResponseEntity<List<ClassroomStudentsClassroomDTO>> getClassroomsForCurrentStudent() {
        try {
            StudentGetCurrentStudentDTO currentStudent = studentService.getCurrentAuthenticatedStudent(new StudentGetCurrentStudentDTO());
            List<ClassroomStudentsClassroomDTO> classrooms = classroomService.findClassroomsByStudentId(currentStudent.getId());
            return ResponseEntity.ok(classrooms);
        } catch (StudentNotFoundException e) {
            log.error("Student not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error retrieving classrooms for current student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves a student's information by their username.
     *
     * @param username the username of the student
     * @return a ResponseEntity containing the Student
     */
    @Operation(summary = "Get student by username", description = "Retrieves a student's information by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student by username",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
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
}
