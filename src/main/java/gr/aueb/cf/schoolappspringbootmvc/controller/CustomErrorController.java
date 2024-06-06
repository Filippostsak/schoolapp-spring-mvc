/**
 * CustomErrorController is a controller class that handles errors in the application.
 * It implements the ErrorController interface provided by Spring Boot to customize the error handling process.
 * It is annotated with @Controller to indicate that it's a Spring MVC controller.
 */
package gr.aueb.cf.schoolappspringbootmvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    /**
     * This method is mapped to the "/error" endpoint.
     * It handles the error and adds the error message to the model.
     * The error message is either the HTTP status code or a generic error message.
     * The method returns the view name "error" to be resolved by the Spring's view resolver.
     *
     * @param request The HttpServletRequest object that contains the request the client made of the server.
     * @param model The Model object that holds the model attributes.
     * @return A string that represents the name of the view.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("error", "HTTP Status Code: " + statusCode);
        } else {
            model.addAttribute("error", "Unknown error occurred");
        }
        return "error";
    }
}