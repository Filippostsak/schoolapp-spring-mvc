/**
 * This is the controller class for the Admins.
 * It handles all the HTTP requests related to the admins.
 * It is annotated with @Controller to indicate that it's a Spring MVC controller.
 * @RequestMapping("/admins") is used to map web requests onto specific handler classes and/or handler methods.
 */
package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminGetAuthenticatedAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetCurrentStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.service.IAdminService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
@Slf4j
@RequiredArgsConstructor
public class AdminsController {

    private final IAdminService adminService;

    /**
     * This method is mapped to the GET HTTP request for the "/dashboard" endpoint.
     * It returns the view name "admins/dashboard" to be resolved by the Spring's view resolver.
     * @return A string that represents the name of the view.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        AdminGetAuthenticatedAdminDTO dto = new AdminGetAuthenticatedAdminDTO();
        try {
            log.info("Attempting to get current student");
            AdminGetAuthenticatedAdminDTO currentAdmin = adminService.getAuthenticatedAdmin(dto);
            log.info("Current student found: {}", currentAdmin.getUsername());
            model.addAttribute("username", currentAdmin.getUsername());
            log.info("Attempting to get classrooms for student with username: {}", currentAdmin.getUsername());
        } catch (StudentNotFoundException e) {
            log.error("Student not found");
            model.addAttribute("error", "Student not found");
        }
        return "admins/dashboard";
    }
}