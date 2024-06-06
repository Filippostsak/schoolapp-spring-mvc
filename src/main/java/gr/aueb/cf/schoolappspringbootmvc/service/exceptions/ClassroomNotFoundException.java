package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

/**
 * Exception thrown when a classroom is not found.
 */

public class ClassroomNotFoundException extends RuntimeException{


    /**
     * Constructs a new ClassroomNotFoundException with the specified detail message.
     * @param message the detail message
     */
    public ClassroomNotFoundException(String message) {
        super(message);
    }
}
