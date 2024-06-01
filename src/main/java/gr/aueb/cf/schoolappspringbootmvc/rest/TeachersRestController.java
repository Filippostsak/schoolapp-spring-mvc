package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.ClassroomReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomFindMeetingsDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RemoveStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.GetTeachersIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.ClassroomMapper;
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

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeachersRestController {

    private final IClassroomService classroomService;
    private final IMeetingDateService meetingDateService;
    private final IStudentService studentService;
    private final TeacherServiceImpl teacherServiceImpl;

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

    @PutMapping("/update-classroom/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id, @RequestBody ClassroomUpdateDTO classroomUpdateDTO) {
        try {
            Classroom updatedClassroom = classroomService.updateClassroomDetails(id, classroomUpdateDTO);
            return ResponseEntity.ok(updatedClassroom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

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

    @PutMapping("/update-meeting-date/{classroomId}/{meetingDateId}")
    public ResponseEntity<String> updateMeetingDate(
            @PathVariable Long classroomId,
            @PathVariable Long meetingDateId,
            @RequestBody @Valid UpdateMeetingDateDTO dto) {
        try {
            // Log the incoming DTO
            log.info("Updating meeting date with DTO: {}", dto);

            // Validate the date and time fields in the DTO
            if (dto.getDate() == null || dto.getTime() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Date and time cannot be null\"}");
            }

            // Additional validation for past dates or times if required
            if (dto.getDate().isBefore(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Date cannot be in the past\"}");
            }

            // Call the service to update the meeting date
            MeetingDate updatedMeetingDate = meetingDateService.updateMeetingDate(classroomId, meetingDateId, dto);

            // Return success message
            return ResponseEntity.ok("{\"message\":\"Meeting date updated successfully!\"}");

        } catch (RuntimeException e) {
            log.error("Error updating meeting date", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            log.error("Unexpected error updating meeting date", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Unexpected error updating meeting date\"}");
        }
    }

    @GetMapping("/classrooms-with-meetings")
    public ResponseEntity<List<ClassroomFindMeetingsDTO>> getClassroomsWithMeetingsForCurrentTeacher() {
        List<ClassroomFindMeetingsDTO> classrooms = classroomService.getAllClassroomsAndMeetingsForCurrentTeacher();
        return ResponseEntity.ok(classrooms);
    }


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

    @GetMapping("/find-classrooms-by-teacher-id/{teacherId}")
    public ResponseEntity<List<Classroom>> findClassroomsByTeacherId(@PathVariable Long teacherId) {
        try {
            List<Classroom> classrooms = classroomService.findClassroomsByTeacher(teacherId);
            return ResponseEntity.ok(classrooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

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
