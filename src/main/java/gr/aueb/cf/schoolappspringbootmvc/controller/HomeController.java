package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling home-related requests.
 */
@Controller
public class HomeController {

    /**
     * Handles GET requests to the root endpoint ("/").
     *
     * @return the name of the view to be rendered, in this case "index"
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
