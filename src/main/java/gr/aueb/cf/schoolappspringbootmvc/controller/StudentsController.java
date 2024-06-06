package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomStudentsClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetCurrentStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.IStudentService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for handling student-related requests.
 * Implements {@link IStudentService} and {@link IClassroomService}
 * to manage student and classroom data.
 */

@Controller
@RequestMapping("/students")
@Slf4j
@RequiredArgsConstructor
public class StudentsController {

    private final IStudentService studentService;
    private final IClassroomService classroomService;

    /**
     * Handles requests to display the student dashboard.
     * Retrieves the current authenticated student's information and
     * adds it to the model to be displayed on the dashboard.
     *
     * @param model the model to which the student's information is added
     * @return the name of the view for the student dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        StudentGetCurrentStudentDTO dto = new StudentGetCurrentStudentDTO();
        try {
            log.info("Attempting to get current student");
            StudentGetCurrentStudentDTO currentStudent = studentService.getCurrentAuthenticatedStudent(dto);
            log.info("Current student found: {}", currentStudent.getUsername());
            model.addAttribute("username", currentStudent.getUsername());
            model.addAttribute("id", currentStudent.getId());
            log.info("Attempting to get classrooms for student with username: {}", currentStudent.getId());
        } catch (StudentNotFoundException e) {
            log.error("Student not found");
            model.addAttribute("error", "Student not found");
        }
        return "students/dashboard";
    }

    /**
     * Handles requests to display the list of classrooms for the current student.
     * Retrieves the current authenticated student's information and their associated
     * classrooms, then adds this information to the model to be displayed.
     *
     * @param model the model to which the student's classrooms are added
     * @return the name of the view for the student's classrooms
     */
    @GetMapping("/classrooms")
    public String classrooms(Model model) {
        try {
            log.info("Attempting to get current student");
            StudentGetCurrentStudentDTO currentStudent = studentService.getCurrentAuthenticatedStudent(new StudentGetCurrentStudentDTO());
            log.info("Current student found: {}", currentStudent.getUsername());
            List<ClassroomStudentsClassroomDTO> classrooms = classroomService.findClassroomsByStudentId(currentStudent.getId());
            log.info("Classrooms found: {}", classrooms.size());
            model.addAttribute("classrooms", classrooms);
            log.info("Adding username to model: {}", currentStudent.getUsername());
            model.addAttribute("username", currentStudent.getUsername());
        } catch (StudentNotFoundException e) {
            log.error("Student not found", e);
            model.addAttribute("error", "Student not found");
        }
        return "students/classrooms";
    }

}
