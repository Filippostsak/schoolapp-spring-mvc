package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.service.IAdminService;
import gr.aueb.cf.schoolappspringbootmvc.service.IStudentService;
import gr.aueb.cf.schoolappspringbootmvc.service.ITeacherService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.AdminAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

/**
 * Controller responsible for handling user registration requests.
 */
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final IStudentService studentService;
    private final ITeacherService teacherService;
    private final IAdminService adminService;

    /**
     * Displays the registration page.
     *
     * @return the name of the registration view
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    /**
     * Handles role selection and redirects to the appropriate registration form.
     *
     * @param role the selected role
     * @return the redirection URL to the corresponding registration form
     */
    @GetMapping("/register/role")
    public String handleRoleSelection(@RequestParam("role") String role) {
        logger.info("Selected role: {}", role);
        switch (role.toLowerCase()) {
            case "student":
                return "redirect:/register/student";
            case "teacher":
                return "redirect:/register/teacher";
            case "admin":
                return "redirect:/register/admin";
            default:
                return "redirect:/register";
        }
    }

    /**
     * Displays the teacher registration form.
     *
     * @param model the model to hold form data
     * @return the name of the teacher registration view
     */
    @GetMapping("/register/teacher")
    public String showTeacherRegistrationForm(Model model) {
        model.addAttribute("teacher", new RegisterTeacherDTO());
        return "registerTeacher";
    }

    /**
     * Handles the submission of the teacher registration form.
     *
     * @param teacherDTO the teacher registration data
     * @param bindingResult the result of the validation
     * @param model the model to hold success or error messages
     * @return the name of the view to be rendered
     */
    @PostMapping("/register/teacher")
    public String registerTeacher(@Valid @ModelAttribute("teacher") RegisterTeacherDTO teacherDTO, BindingResult bindingResult, Model model) {
        logger.info("Received registration data: {}", teacherDTO);
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return "registerTeacher";
        }
        try {
            teacherService.registerTeacher(teacherDTO);
            model.addAttribute("success", "Account successfully created. Redirecting to login page...");
            return "registerTeacher";
        } catch (TeacherAlreadyExistsException e) {
            logger.error("Teacher already exists: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "registerTeacher";
        }
    }

    /**
     * Displays the student registration form.
     *
     * @param model the model to hold form data
     * @return the name of the student registration view
     */
    @GetMapping("/register/student")
    public String showStudentRegistrationForm(Model model) {
        model.addAttribute("student", new RegisterStudentDTO());
        return "registerStudent";
    }

    /**
     * Handles the submission of the student registration form.
     *
     * @param studentDTO the student registration data
     * @param bindingResult the result of the validation
     * @param model the model to hold success or error messages
     * @return the name of the view to be rendered
     */
    @PostMapping("/register/student")
    public String registerStudent(@Valid @ModelAttribute("student") RegisterStudentDTO studentDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registerStudent";
        }
        try {
            studentService.registerStudent(studentDTO);
            model.addAttribute("success", "Account successfully created. Redirecting to login page...");
            return "registerStudent";
        } catch (StudentAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "registerStudent";
        }
    }

    /**
     * Displays the admin registration form.
     *
     * @param model the model to hold form data
     * @return the name of the admin registration view
     */
    @GetMapping("/register/admin")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("admin", new RegisterAdminDTO());
        return "registerAdmin";
    }

    /**
     * Handles the submission of the admin registration form.
     *
     * @param adminDTO the admin registration data
     * @param bindingResult the result of the validation
     * @param model the model to hold success or error messages
     * @return the name of the view to be rendered
     */
    @PostMapping("/register/admin")
    public String registerAdmin(@Valid @ModelAttribute("admin") RegisterAdminDTO adminDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registerAdmin";
        }
        try {
            adminService.registerAdmin(adminDTO);
            model.addAttribute("success", "Account successfully created. Redirecting to login page...");
            return "registerAdmin";
        } catch (AdminAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "registerAdmin";
        }
    }
}
