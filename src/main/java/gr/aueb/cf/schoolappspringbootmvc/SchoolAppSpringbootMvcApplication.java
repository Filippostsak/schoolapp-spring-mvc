package gr.aueb.cf.schoolappspringbootmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The entry point of the Spring Boot application.
 * This class contains the main method which is the starting point of the Spring Boot application.
 */
@SpringBootApplication
@EnableJpaAuditing
public class SchoolAppSpringbootMvcApplication {

    /**
     * The main method which runs the Spring Boot application.
     *
     * @param args command-line arguments (none are needed for this application).
     */
    public static void main(String[] args) {
        SpringApplication.run(SchoolAppSpringbootMvcApplication.class, args);
    }

}
