package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
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

/**
 * Controller for handling teacher-related actions such as dashboard viewing,
 * student searching, classroom creation, and classroom management.
 */
@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeachersController {

    private final IClassroomService classroomService;
    private final TeacherServiceImpl teacherServiceImpl;

    /**
     * Displays the teacher's dashboard.
     *
     * @param model the model to be used in the view
     * @return the name of the dashboard view
     */
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

    /**
     * Displays the student search page.
     *
     * @param lastname the last name to search for
     * @param limit    the limit on the number of results
     * @param model    the model to be used in the view
     * @return the name of the search students view
     */
    @GetMapping("/search-students")
    public String searchStudents(@RequestParam(value = "lastname", required = false) String lastname,
                                 @RequestParam(value = "limit", required = false) Integer limit,
                                 Model model) {
        Optional<Teacher> currentTeacherOpt = teacherServiceImpl.getCurrentAuthenticatedTeacher();
        if (currentTeacherOpt.isPresent()) {
            Teacher teacher = currentTeacherOpt.get();
            model.addAttribute("firstname", teacher.getFirstname());
        }

        model.addAttribute("searchPerformed", false);
        return "teachers/search-students";
    }

    /**
     * Displays the classroom creation page.
     *
     * @param model the model to be used in the view
     * @return the name of the create classroom view
     */
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

    /**
     * Handles the submission of the classroom creation form.
     *
     * @param createClassroomDTO  the data transfer object for creating a classroom
     * @param bindingResult       the binding result for validation errors
     * @param model               the model to be used in the view
     * @param redirectAttributes  the redirect attributes for flash messages
     * @return the name of the view to be displayed
     */
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

    /**
     * Displays the teacher's classrooms.
     *
     * @param page  the current page number
     * @param model the model to be used in the view
     * @return the name of the view displaying the teacher's classrooms
     */
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
