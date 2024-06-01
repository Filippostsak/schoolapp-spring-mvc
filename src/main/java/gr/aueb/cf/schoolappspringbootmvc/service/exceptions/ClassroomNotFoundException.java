package gr.aueb.cf.schoolappspringbootmvc.service.exceptions;

public class ClassroomNotFoundException extends RuntimeException{
    public ClassroomNotFoundException(String message) {
        super(message);
    }
}
