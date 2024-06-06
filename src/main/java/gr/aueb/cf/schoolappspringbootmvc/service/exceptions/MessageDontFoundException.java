package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a message is not found.
 */

public class MessageDontFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new MessageDontFoundException with the specified detail message.
     *
     * @param message the detail message
     */

    public MessageDontFoundException(String message) {
        super(message);
    }
}
