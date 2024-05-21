package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an attempt is made to create a teacher that already exists.
 */
public class TeacherAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new TeacherAlreadyExistsException with the specified detail message.
     *
     * @param username the username of the teacher that already exists.
     */
    public TeacherAlreadyExistsException(String username) {
        super("Teacher with username " + username + " already exists");
    }
}
