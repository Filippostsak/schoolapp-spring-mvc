package gr.aueb.cf.schoolappspringbootmvc.controller;

import gr.aueb.cf.schoolappspringbootmvc.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;

@Component
@RequiredArgsConstructor
@Controller
public class LoginController implements AuthenticationSuccessHandler {

    private final IUserService userService;

    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        if (principal == null) {
            return "login";
        }

        var user = userService.getUserByUsername(principal.getName());
        String role = user.getRole().name();

        switch (role) {
            case "TEACHER":
                return "redirect:/teachers/dashboard";
            case "STUDENT":
                return "redirect:/students/dashboard";
            case "ADMIN":
                return "redirect:/admins/dashboard";
            default:
                return "redirect:/login";
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        var user = userService.getUserByUsername(authentication.getName());
        String role = user.getRole().name();

        switch (role) {
            case "TEACHER":
                response.sendRedirect("/teachers/dashboard");
                break;
            case "STUDENT":
                response.sendRedirect("/students/dashboard");
                break;
            case "ADMIN":
                response.sendRedirect("/admins/dashboard");
                break;
            default:
                response.sendRedirect("/error-403"); // Redirect to error-403 page for unauthorized access
                break;
        }
    }
}