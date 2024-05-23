package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

public class ClassroomAlreadyExistsException extends Exception{

    private static final long serialVersionUID = 1L;

    public ClassroomAlreadyExistsException(String name) {
        super("Classroom with name " + name + " already exists");
    }
}
