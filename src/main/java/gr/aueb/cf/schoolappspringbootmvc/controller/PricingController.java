package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling requests related to the pricing page.
 */
@Controller
public class PricingController {

    /**
     * Handles GET requests to the "/pricing" endpoint.
     *
     * @return the name of the view to be rendered, in this case "pricing"
     */
    @GetMapping("/pricing")
    public String pricing() {
        return "pricing";
    }
}
