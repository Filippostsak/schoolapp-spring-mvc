package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler for the application.
 * Handles different types of exceptions and directs the user to an error page.
 */
@ControllerAdvice
public class ErrorController {

    /**
     * Handles NoHandlerFoundException (404 errors).
     *
     * @param model the model to add attributes to
     * @param e the exception
     * @return the error view name
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Model model, NoHandlerFoundException e) {

        model.addAttribute("error", "Page not found: " + e.getRequestURL());
        return "error";
    }

    /**
     * Handles RuntimeException.
     *
     * @param model the model to add attributes to
     * @param e the exception
     * @return the error view name
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleBadRequest(Model model, RuntimeException e) {
        model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "error";
    }

    /**
     * Handles all other exceptions.
     *
     * @param model the model to add attributes to
     * @param e the exception
     * @return the error view name
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("error", "An error occurred: " + e.getMessage());
        return "error";
    }
}
