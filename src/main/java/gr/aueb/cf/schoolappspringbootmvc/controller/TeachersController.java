package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.IClassroomService;
import gr.aueb.cf.schoolappspringbootmvc.service.IStudentService;
import gr.aueb.cf.schoolappspringbootmvc.service.TeacherServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeachersController {

    private final IStudentService studentService;
    private final IClassroomService classroomService;
    private final TeacherServiceImpl teacherServiceImpl;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();
        if (currentTeacherOpt.isPresent()) {
            Teacher teacher = currentTeacherOpt.get();
            model.addAttribute("firstname", teacher.getFirstname());
            List<Classroom> classrooms = classroomService.findClassroomsByTeacher(teacher.getId());
            model.addAttribute("classrooms", classrooms);
        }
        model.addAttribute("searchStudentDTO", new SearchStudentDTO());
        return "teachers/dashboard";
    }

    @GetMapping("/search-students")
    public String searchStudents(@RequestParam(value = "lastname", required = false) String lastname,
                                 @RequestParam(value = "limit", required = false) Integer limit,
                                 Model model) {
        Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();
        if (currentTeacherOpt.isPresent()) {
            Teacher teacher = currentTeacherOpt.get();
            model.addAttribute("firstname", teacher.getFirstname());
        }

        List<Student> students;
        if (lastname == null || lastname.isEmpty()) {
            try {
                students = studentService.findAllStudents();
            } catch (Exception e) {
                model.addAttribute("error", "An error occurred while retrieving students.");
                return "teachers/search-students";
            }
        } else {
            students = studentService.searchStudentsByLastname(lastname);
        }

        if (limit != null && limit > 0 && limit < students.size()) {
            students = students.subList(0, limit);
        }

        if (students.isEmpty()) {
            model.addAttribute("error", "No students found with the provided last name.");
        } else {
            model.addAttribute("students", students);
        }

        model.addAttribute("searchPerformed", true);
        return "teachers/search-students";
    }

    @GetMapping("/create-classroom")
    public String createClassroom(Model model) {
        Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();
        if (currentTeacherOpt.isPresent()) {
            Teacher teacher = currentTeacherOpt.get();
            model.addAttribute("firstname", teacher.getFirstname());
            model.addAttribute("createClassroomDTO", new CreateClassroomDTO());
        }
        return "teachers/create-classroom";
    }

    @PostMapping("/create-classroom")
    public String createClassroom(@ModelAttribute("createClassroomDTO") @Valid CreateClassroomDTO createClassroomDTO,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("createClassroomDTO", createClassroomDTO);
            return "teachers/create-classroom";
        }

        if (classroomService.classroomNameExists(createClassroomDTO.getName())) {
            bindingResult.rejectValue("name", "error.createClassroomDTO", "Classroom name already exists");
            model.addAttribute("createClassroomDTO", createClassroomDTO);
            return "teachers/create-classroom";
        }

        try {
            classroomService.createClassroom(createClassroomDTO);
            redirectAttributes.addFlashAttribute("success", "Classroom created successfully!");
            return "redirect:/teachers/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while creating the classroom. Please try again.");
            model.addAttribute("createClassroomDTO", createClassroomDTO);
            return "teachers/create-classroom";
        }
    }

    @GetMapping("/your-classrooms")
    public String yourClassrooms(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();

        if (currentTeacherOpt.isPresent()) {
            Teacher teacher = currentTeacherOpt.get();
            Page<Classroom> classroomPage = classroomService.findClassroomsByTeacher(teacher.getId(), pageable);

            model.addAttribute("classrooms", classroomPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", classroomPage.getTotalPages());
            model.addAttribute("firstname", teacher.getFirstname());
        } else {
            model.addAttribute("error", "No authenticated teacher found.");
        }

        return "teachers/your-classrooms";
    }



}
