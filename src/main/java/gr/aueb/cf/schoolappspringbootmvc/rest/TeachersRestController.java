package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RemoveStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.IMeetingDateService;
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
    private final IMeetingDateService meetingDateService;
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

    @GetMapping("/classroom/{id}")
    public ResponseEntity<ClassroomUpdateDTO> getClassroom(@PathVariable Long id) throws Exception {
        Optional<Classroom> classroomOpt = classroomService.findById(id);
        if (classroomOpt.isPresent()) {
            Classroom classroom = classroomOpt.get();
            ClassroomUpdateDTO dto = new ClassroomUpdateDTO();
            dto.setId(classroom.getId());
            dto.setName(classroom.getName());
            dto.setDescription(classroom.getDescription());
            dto.setClassroomUrl(classroom.getClassroomUrl());
            dto.setImageUrl(classroom.getImageUrl());
            dto.setActive(classroom.isActive());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update-classroom/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id, @RequestBody ClassroomUpdateDTO classroomUpdateDTO) {
        try {
            Classroom updatedClassroom = classroomService.updateClassroomDetails(id, classroomUpdateDTO);
            return ResponseEntity.ok(updatedClassroom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remove-student")
    public ResponseEntity<Map<String, String>> removeStudentFromClassroom(@RequestBody RemoveStudentDTO removeStudentDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            classroomService.removeStudentFromClassroom(removeStudentDTO.getClassroomId(), removeStudentDTO.getStudentId());
            response.put("status", "success");
            response.put("message", "Student removed successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/create-meeting-date/{classroomId}")
    public ResponseEntity<MeetingDate> createMeetingDate(@PathVariable Long classroomId,
                                                         @RequestBody CreateMeetingDateDTO createMeetingDateDTO) {
        try {
            MeetingDate createdMeetingDate = meetingDateService.createMeetingDate(classroomId, createMeetingDateDTO);
            return ResponseEntity.ok(createdMeetingDate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update-meeting-date/{classroomId}/{meetingDateId}")
    public ResponseEntity<MeetingDate> updateMeetingDate(@PathVariable Long classroomId,
                                                         @PathVariable Long meetingDateId,
                                                         @RequestBody MeetingUpdateDTO meetingUpdateDTO) {
        try {
            MeetingDate updatedMeetingDate = meetingDateService.updateMeetingDate(classroomId, meetingDateId, meetingUpdateDTO);
            return ResponseEntity.ok(updatedMeetingDate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/classroom/{classroomId}/meeting-dates")
    public ResponseEntity<List<MeetingDate>> getMeetingDatesByClassroomId(@PathVariable Long classroomId) {
        try {
            List<MeetingDate> meetingDates = meetingDateService.findMeetingDatesByClassroomId(classroomId);
            return ResponseEntity.ok(meetingDates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
