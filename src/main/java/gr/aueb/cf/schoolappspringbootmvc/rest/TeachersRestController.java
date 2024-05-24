package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeachersRestController {

    private final IClassroomService classroomService;
    private final TeacherServiceImpl teacherServiceImpl;

    @PutMapping("/add-teacher")
    public ResponseEntity<Map<String, String>> addTeacherToClassroom(@RequestBody AddTeacherToClassroomDTO dto) {
        Map<String, String> response = new HashMap<>();

        try {
            Long classroomId = dto.getClassroomId();
            String teacherUsername = dto.getTeacherUsername();
            Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();
            if (currentTeacherOpt.isPresent()) {
                Teacher currentTeacher = currentTeacherOpt.get();
                Optional<Classroom> classroomOpt = classroomService.findById(classroomId);
                if (classroomOpt.isPresent() && classroomOpt.get().getCreator().equals(currentTeacher)) {
                    classroomService.addTeacherToClassroom(dto);
                    response.put("status", "success");
                    response.put("message", "Teacher added successfully!");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "error");
                    response.put("message", "You do not have permission to add a teacher to this classroom.");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            } else {
                response.put("status", "error");
                response.put("message", "No authenticated teacher found.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/check-classroom-name")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkClassroomName(@RequestParam String name) {
        boolean exists = classroomService.classroomNameExists(name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-classroom/{id}")
    public ResponseEntity<Map<String, String>> deleteClassroom(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();
        if (currentTeacherOpt.isPresent()) {
            Teacher teacher = currentTeacherOpt.get();
            boolean isOwner = classroomService.findClassroomsByTeacher(teacher.getId())
                    .stream().anyMatch(classroom -> classroom.getId().equals(id));
            if (isOwner) {
                try {
                    classroomService.deleteClassroom(id);
                    response.put("status", "success");
                    response.put("message", "Classroom deleted successfully!");
                    return ResponseEntity.ok(response);
                } catch (Exception e) {
                    response.put("status", "error");
                    response.put("message", "An error occurred while deleting the classroom.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } else {
                response.put("status", "error");
                response.put("message", "You are not authorized to delete this classroom.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        } else {
            response.put("status", "error");
            response.put("message", "No authenticated teacher found.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
