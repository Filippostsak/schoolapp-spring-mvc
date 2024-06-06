package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a notification is not found.
 */

public class NotificationNotFoundException extends Exception{

    /**
     * Constructs a new NotificationNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */

    @Serial
    private static final long serialVersionUID = 1L;

    public NotificationNotFoundException(String message) {
        super(message);
    }

}
