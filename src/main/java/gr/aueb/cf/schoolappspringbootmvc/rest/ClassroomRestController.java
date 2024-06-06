package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomStudentsClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling classroom-related requests.
 * Provides endpoints for retrieving classrooms by student ID and teacher ID.
 */

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor
@Slf4j
public class ClassroomRestController {

    private final IClassroomService classroomService;

    /**
     * Retrieves the list of classrooms for a specific student by their ID.
     *
     * @param studentId the ID of the student
     * @return a ResponseEntity containing the list of ClassroomStudentsClassroomDTO
     */
    @Operation(summary = "Get classrooms by student ID", description = "Retrieves the list of classrooms for a specific student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved classrooms",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassroomStudentsClassroomDTO.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ClassroomStudentsClassroomDTO>> getClassroomsByStudentId(@PathVariable Long studentId) {
        try {
            List<ClassroomStudentsClassroomDTO> classrooms = classroomService.findClassroomsByStudentId(studentId);
            return ResponseEntity.ok(classrooms);
        } catch (StudentNotFoundException e) {
            log.error("Error retrieving classrooms for student ID: {}", studentId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving classrooms for student ID: {}", studentId, e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Retrieves the list of classrooms for a specific teacher by their ID.
     *
     * @param teacherId the ID of the teacher
     * @return a ResponseEntity containing the list of Classroom
     */
    @Operation(summary = "Get classrooms by teacher ID", description = "Retrieves the list of classrooms for a specific teacher by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved classrooms",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Classroom.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/classrooms/{teacherId}")
    public ResponseEntity<List<Classroom>> getAllClassroomsByTeacherId(@PathVariable Long teacherId) {
        try {
            List<Classroom> classrooms = classroomService.getAllClassroomsByTeacherId(teacherId);
            return ResponseEntity.ok(classrooms);
        } catch (StudentNotFoundException e) {
            log.error("Error retrieving classrooms for teacher ID: {}", teacherId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving classrooms for teacher ID: {}", teacherId, e);
            return ResponseEntity.status(500).build();
        }
    }
}
