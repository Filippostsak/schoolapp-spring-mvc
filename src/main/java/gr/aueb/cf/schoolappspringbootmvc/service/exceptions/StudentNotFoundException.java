package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

/**
 * Exception thrown when a student is not found.
 */

public class StudentNotFoundException extends RuntimeException{

    /**
     * Constructs a new StudentNotFoundException with the specified detail message.
     * @param message the detail message
     */

    public StudentNotFoundException(String message) {
        super(message);
    }
}
