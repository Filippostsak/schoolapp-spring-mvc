package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomFindMeetingsDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RemoveStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.GetTeachersIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.StudentMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.IMeetingDateService;
import gr.aueb.cf.schoolappspringbootmvc.service.IStudentService;
import gr.aueb.cf.schoolappspringbootmvc.service.TeacherServiceImpl;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing teacher-related operations.
 */

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeachersRestController {

    /**
     * Service for managing classrooms.
     */
    private final IClassroomService classroomService;

    /**
     * Service for managing meeting dates.
     */
    private final IMeetingDateService meetingDateService;

    /**
     * Service for managing students.
     */
    private final IStudentService studentService;

    /**
     * Service for managing teachers.
     */
    private final TeacherServiceImpl teacherServiceImpl;

    /**
     * Adds a teacher to a classroom.
     *
     * @param dto the DTO containing the classroom ID and the teacher ID
     * @return a response entity with a message indicating the result of the operation
     */
    @Operation(summary = "Add a teacher to a classroom", description = "Add a teacher to a classroom", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher added successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have permission to add a teacher to this classroom."),
            @ApiResponse(responseCode = "404", description = "Classroom not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/add-teacher")
    public ResponseEntity<Map<String, String>> addTeacherToClassroom(@RequestBody AddTeacherToClassroomDTO dto) {
        try {
            Long classroomId = dto.getClassroomId();
            Teacher currentTeacher = teacherServiceImpl.getCurrentAuthenticatedTeacher()
                    .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

            Classroom classroom = classroomService.findById(classroomId)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            if (classroom.getCreator().equals(currentTeacher)) {
                classroomService.addTeacherToClassroom(dto);
                return ResponseEntity.ok(Map.of("status", "success", "message", "Teacher added successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("status", "error", "message", "You do not have permission to add a teacher to this classroom."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }



/**
     * Retrieves the teacher with the given ID.
     *
     * @param id the ID of the teacher
     * @return a response entity with the teacher's ID, first name, and last name
     */

    @Operation(summary = "Get teacher by ID", description = "Get teacher by ID", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher found"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })


    @GetMapping("current-teacher-id/{id}")
    public ResponseEntity<GetTeachersIdDTO> getTeacherById(@PathVariable Long id) {
        try {
            GetTeachersIdDTO teacher = teacherServiceImpl.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            GetTeachersIdDTO dto = new GetTeachersIdDTO();
            dto.setId(teacher.getId());
            dto.setFirstname(teacher.getFirstname());
            dto.setLastname(teacher.getLastname());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Searches for teachers by username.
     *
     * @param username the username to search for
     * @return a response entity with a list of usernames
     */

    @Operation(summary = "Search teachers by username", description = "Search teachers by username", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teachers found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/search-usernames")
    public ResponseEntity<List<String>> searchTeachersUsernames(@RequestParam String username) {
        try {
            List<Teacher> teachers = teacherServiceImpl.findByUsernameContaining(username);
            List<String> usernames = teachers.stream()
                    .map(teacher -> teacher.getUser().getUsername())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usernames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Checks if a classroom with the given name exists.
     *
     * @param name the name of the classroom
     * @return a response entity with a boolean indicating whether the classroom exists
     */

    @Operation(summary = "Check if classroom name exists", description = "Check if classroom name exists", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom name exists"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @GetMapping("/check-classroom-name")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkClassroomName(@RequestParam String name) {
        try {
            boolean exists = classroomService.classroomNameExists(name);
            return ResponseEntity.ok(Map.of("exists", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Creates a new classroom.
     *
     * @param id the classroom to create
     * @return a response entity with a message indicating the result of the operation
     */

    @Operation(summary = "Create a classroom", description = "Create a classroom", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom created successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @DeleteMapping("/delete-classroom/{id}")
    public ResponseEntity<Map<String, String>> deleteClassroom(@PathVariable Long id) {
        try {
            Teacher teacher = teacherServiceImpl.getCurrentAuthenticatedTeacher()
                    .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

            boolean isOwner = classroomService.findClassroomsByTeacher(teacher.getId())
                    .stream().anyMatch(classroom -> classroom.getId().equals(id));

            if (isOwner) {
                classroomService.deleteClassroom(id);
                return ResponseEntity.ok(Map.of("status", "success", "message", "Classroom deleted successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("status", "error", "message", "You are not authorized to delete this classroom."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    /**
     * Retrieves the classroom with the given ID.
     *
     * @param id the ID of the classroom
     * @return a response entity with the classroom's ID, name, description, classroom URL, image URL, and active status
     */

    @Operation(summary = "Get classroom by ID", description = "Get classroom by ID", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom found"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })

    @GetMapping("/classroom/{id}")
    public ResponseEntity<ClassroomUpdateDTO> getClassroom(@PathVariable Long id) {
        try {
            Classroom classroom = classroomService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            ClassroomUpdateDTO dto = new ClassroomUpdateDTO();
            dto.setId(classroom.getId());
            dto.setName(classroom.getName());
            dto.setDescription(classroom.getDescription());
            dto.setClassroomUrl(classroom.getClassroomUrl());
            dto.setImageUrl(classroom.getImageUrl());
            dto.setActive(classroom.isActive());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Retrieves the classroom with the given ID.
     *
     * @param id the ID of the classroom
     * @return a response entity with the classroom's ID, name, description, classroom URL, image URL, and active status
     */


    @Operation(summary = "Get classroom by ID", description = "Get classroom by ID", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom found"),
            @ApiResponse(responseCode = "404", description = "Classroom not found")
    })

    @GetMapping("/classroom-read-only/{id}")
    public ResponseEntity<ClassroomReadOnlyDTO> getClassroomReadOnly(@PathVariable Long id){
        try {
            ClassroomReadOnlyDTO classroomReadOnlyDTO = classroomService.getByClassroomId(id);

            log.info("ClassroomReadOnlyDTO created: {}", classroomReadOnlyDTO);
            return ResponseEntity.ok(classroomReadOnlyDTO);
        } catch (RuntimeException e) {
            log.error("Error finding classroom with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Unexpected error finding classroom with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the details of a classroom.
     *
     * @param id the ID of the classroom
     * @param classroomUpdateDTO the DTO containing the updated classroom details
     * @return a response entity with the updated classroom
     */

    @Operation(summary = "Update classroom", description = "Update classroom", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classroom updated successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @PutMapping("/update-classroom/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id, @RequestBody ClassroomUpdateDTO classroomUpdateDTO) {
        try {
            Classroom updatedClassroom = classroomService.updateClassroomDetails(id, classroomUpdateDTO);
            return ResponseEntity.ok(updatedClassroom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Creates a new meeting date.
     *
     * @param classroomId the ID of the classroom
     * @param createMeetingDateDTO the DTO containing the meeting date details
     * @return a response entity with the created meeting date
     */

    @Operation(summary = "Create meeting date", description = "Create meeting date", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting date created successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @PostMapping("/create-meeting-date/{classroomId}")
    public ResponseEntity<MeetingDate> createMeetingDate(@PathVariable Long classroomId,
                                                         @RequestBody CreateMeetingDateDTO createMeetingDateDTO) {
        try {
            MeetingDate createdMeetingDate = meetingDateService.createMeetingDate(classroomId, createMeetingDateDTO);
            return ResponseEntity.ok(createdMeetingDate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Updates the date and time of a meeting.
     *
     * @param classroomId the ID of the classroom
     * @param meetingDateId the ID of the meeting date
     * @param dto the DTO containing the updated meeting date details
     * @return a response entity with a message indicating the result of the operation
     */

    @Operation(summary = "Update meeting date", description = "Update meeting date", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting date updated successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Meeting date not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PutMapping("/update-meeting-date/{classroomId}/{meetingDateId}")
    public ResponseEntity<String> updateMeetingDate(
            @PathVariable Long classroomId,
            @PathVariable Long meetingDateId,
            @RequestBody @Valid UpdateMeetingDateDTO dto) {
        try {
            log.info("Updating meeting date with DTO: {}", dto);
            if (dto.getDate() == null || dto.getTime() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Date and time cannot be null\"}");
            }
            if (dto.getDate().isBefore(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Date cannot be in the past\"}");
            }
            MeetingDate updatedMeetingDate = meetingDateService.updateMeetingDate(classroomId, meetingDateId, dto);
            return ResponseEntity.ok("{\"message\":\"Meeting date updated successfully!\"}");
        } catch (RuntimeException e) {
            log.error("Error updating meeting date", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            log.error("Unexpected error updating meeting date", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Unexpected error updating meeting date\"}");
        }
    }

    /**
     * Retrieves all classrooms with meetings for the current teacher.
     *
     * @return a response entity with a list of classrooms and their meetings
     */

    @Operation(summary = "Get classrooms with meetings for current teacher", description = "Get classrooms with meetings for current teacher", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classrooms found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @GetMapping("/classrooms-with-meetings")
    public ResponseEntity<List<ClassroomFindMeetingsDTO>> getClassroomsWithMeetingsForCurrentTeacher() {
        List<ClassroomFindMeetingsDTO> classrooms = classroomService.getAllClassroomsAndMeetingsForCurrentTeacher();
        return ResponseEntity.ok(classrooms);
    }

    /**
     * Deletes a meeting date.
     *
     * @param meetingDateId the ID of the meeting date
     * @return a response entity with a message indicating the result of the operation
     */

    @Operation(summary = "Delete meeting date", description = "Delete meeting date", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meeting deleted successfully!"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @DeleteMapping("/delete-meeting/{meetingDateId}")
    public ResponseEntity<Map<String, String>> deleteMeetingDate(@PathVariable Long meetingDateId) {
        try {
            meetingDateService.deleteMeetingDate(meetingDateId);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Meeting deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    /**
     * Retrieves all students.
     *
     * @param lastname the last name of the student
     * @param limit the maximum number of students to return
     * @return a response entity with a list of students
     */

    @Operation(summary = "Search students", description = "Search students", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students found"),
            @ApiResponse(responseCode = "404", description = "No students found with the provided last name"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/search-students-dynamic")
    public ResponseEntity<?> searchStudents(@RequestParam(value = "lastname", required = false) String lastname,
                                            @RequestParam(value = "limit", required = false) Integer limit) {
        List<SearchStudentDTO> students;
        try {
            if (lastname == null || lastname.isEmpty()) {
                students = studentService.findAllStudents().stream()
                        .map(StudentMapper::toSearchStudentDTO)
                        .collect(Collectors.toList());
            } else {
                students = studentService.searchStudentsByLastname(lastname).stream()
                        .map(StudentMapper::toSearchStudentDTO)
                        .collect(Collectors.toList());
            }

            if (limit != null && limit > 0 && limit < students.size()) {
                students = students.subList(0, limit);
            }

            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No students found with the provided last name.");
            }

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving students.");
        }
    }

    /**
     * Retrieves all students.
     *
     * @param teacherId the last name of the student
     * @return a response entity with a list of students
     */

    @Operation(summary = "Find classrooms by teacher ID", description = "Find classrooms by teacher ID", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classrooms found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @GetMapping("/find-classrooms-by-teacher-id/{teacherId}")
    public ResponseEntity<List<Classroom>> findClassroomsByTeacherId(@PathVariable Long teacherId) {
        try {
            List<Classroom> classrooms = classroomService.findClassroomsByTeacher(teacherId);
            return ResponseEntity.ok(classrooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Retrieves all students.
     *
     * @param studentId the last name of the student
     * @return a response entity with a list of students
     */

    @Operation(summary = "Find classrooms by student ID", description = "Find classrooms by student ID", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classrooms found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @GetMapping("/find-classrooms-by-student-id/{studentId}")
    public ResponseEntity<SearchStudentToClassroomDTO> getStudentClassrooms(@PathVariable Long studentId) {
        try {
            SearchStudentToClassroomDTO dto = studentService.getStudentClassrooms(studentId);
            return ResponseEntity.ok(dto);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ClassroomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Adds a student to a classroom.
     *
     * @param classroomId the ID of the classroom
     * @param studentId the ID of the student
     * @return a response entity with a message indicating the result of the operation
     */

    @Operation(summary = "Add student to classroom", description = "Add student to classroom", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student added to classroom successfully!"),
            @ApiResponse(responseCode = "409", description = "Student is already registered in this classroom."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PostMapping("/add-student-to-classroom/{classroomId}/student/{studentId}")
    public ResponseEntity<?>addStudentToClassroom(@PathVariable Long classroomId, @PathVariable Long studentId) {
        try {
            // Check if student is already in the classroom
            if (classroomService.isStudentInClassroom(classroomId, studentId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Student is already registered in this classroom.");
            }

            classroomService.addStudentToClassroom(classroomId, studentId);
            return ResponseEntity.ok("Student added to classroom successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding student to classroom.");
        }
    }

    /**
     * Removes a student from a classroom.
     *
     * @param classroomId the ID of the classroom
     * @param studentId the ID of the student
     * @return a response entity with a message indicating the result of the operation
     */

    @Operation(summary = "Remove student from classroom", description = "Remove student from classroom", tags = {"teachers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student removed from classroom successfully!"),
            @ApiResponse(responseCode = "404", description = "Student is not registered in this classroom."),
            @ApiResponse(responseCode = "403", description = "You do not have permission to remove a student from this classroom."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/remove-student-from-classroom/{classroomId}/student/{studentId}")
    public ResponseEntity<String> removeStudentFromClassroom(@PathVariable Long classroomId, @PathVariable Long studentId) {
        try {
            if(!classroomService.isStudentInClassroom(classroomId, studentId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student is not registered in this classroom.");
            }

            if (!Objects.equals(classroomService.getCreatorTeacher(classroomId).getId(), teacherServiceImpl.getCurrentAuthenticatedTeacher().get().getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to remove a student from this classroom.");
            }
            classroomService.removeStudentFromClassroom(new RemoveStudentDTO(classroomId, studentId));
            return ResponseEntity.ok("Student removed from classroom successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing student from classroom.");
        }
    }

}
