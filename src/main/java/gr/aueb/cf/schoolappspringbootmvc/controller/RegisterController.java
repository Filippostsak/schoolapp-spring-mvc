package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterAdminDTO;
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

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final IStudentService studentService;
    private final ITeacherService teacherService;
    private final IAdminService adminService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

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

    @GetMapping("/register/teacher")
    public String showTeacherRegistrationForm(Model model) {
        model.addAttribute("teacher", new RegisterTeacherDTO());
        return "registerTeacher";
    }

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



    @GetMapping("/register/student")
    public String showStudentRegistrationForm(Model model) {
        model.addAttribute("student", new RegisterStudentDTO());
        return "registerStudent";
    }

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


    @GetMapping("/register/admin")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("admin", new RegisterAdminDTO());
        return "registerAdmin";
    }

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
