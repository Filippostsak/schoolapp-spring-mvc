package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an attempt is made to create an admin that already exists.
 */
public class AdminAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new AdminAlreadyExistsException with the specified detail message.
     *
     * @param username the username of the admin that already exists.
     */
    public AdminAlreadyExistsException(String username) {
        super("Admin with username " + username + " already exists");
    }
}
