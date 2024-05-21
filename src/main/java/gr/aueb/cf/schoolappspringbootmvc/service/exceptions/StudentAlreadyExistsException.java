package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an attempt is made to create a student that already exists.
 */
public class StudentAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new StudentAlreadyExistsException with the specified detail message.
     *
     * @param username the username of the student that already exists.
     */
    public StudentAlreadyExistsException(String username) {
        super("Student with username " + username + " already exists");
    }
}
