package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Model model, NoHandlerFoundException e) {
        model.addAttribute("error", "Page not found: " + e.getRequestURL());
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleBadRequest(Model model, RuntimeException e) {
        model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("error", "An error occurred: " + e.getMessage());
        return "error";
    }
}
