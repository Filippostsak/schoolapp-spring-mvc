/**
 * This is a custom exception class named TeacherNotFoundException.
 * It extends the RuntimeException class, which means it's an unchecked exception.
 * It is used to indicate that a teacher with a specified username could not be found.
 */
package gr.aueb.cf.schoolappspringbootmvc.mapper.exception;

import java.io.Serial;

public class TeacherNotFoundException extends RuntimeException{

    /**
     * This is a unique identifier for the serialized class.
     * It is used during the deserialization process to ensure that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * This is the constructor of the TeacherNotFoundException class.
     * It calls the superclass constructor (RuntimeException) and passes a custom error message to it.
     * The error message is "Teacher not found: " followed by the username of the teacher.
     *
     * @param username The username of the teacher that could not be found.
     */
    public TeacherNotFoundException(String username) {
        super("Teacher not found: " + username);
    }
}