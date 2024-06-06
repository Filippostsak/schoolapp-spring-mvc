package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling unauthorized access errors.
 */
@Controller
public class UnauthorisedErrorController {

    /**
     * Handles requests to the /error-403 endpoint and returns the error-403 view.
     *
     * @return the name of the error-403 view
     */
    @GetMapping("/error-403")
    public String accessDenied() {
        return "error-403";
    }
}
