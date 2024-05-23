package gr.aueb.cf.schoolappspringbootmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentsController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "students/dashboard";
    }


}
