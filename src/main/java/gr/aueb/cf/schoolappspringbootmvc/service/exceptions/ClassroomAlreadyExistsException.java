package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

/**
 * Exception thrown when an attempt is made to create a classroom that already exists.
 */

public class ClassroomAlreadyExistsException extends Exception{

    /**
     * Serial version UID
     */

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ClassroomAlreadyExistsException with the specified detail message.
     *
     * @param name the name of the classroom that already exists.
     */

    public ClassroomAlreadyExistsException(String name) {
        super("Classroom with name " + name + " already exists");
    }
}
