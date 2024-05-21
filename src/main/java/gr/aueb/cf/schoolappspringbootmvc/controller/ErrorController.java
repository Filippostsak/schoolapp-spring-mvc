package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(RuntimeException.class)
    public String handleBadRequest(Model model, RuntimeException e) {
        model.addAttribute("error", e.getMessage()); // Using e.getMessage() to display the error message
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
