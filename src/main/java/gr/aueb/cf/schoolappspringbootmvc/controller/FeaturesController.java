package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling feature-related requests.
 */
@Controller
public class FeaturesController {

    /**
     * Handles GET requests to the /features endpoint.
     *
     * @return the name of the view to be rendered, in this case "features"
     */
    @GetMapping("/features")
    public String features() {
        return "features";
    }
}
