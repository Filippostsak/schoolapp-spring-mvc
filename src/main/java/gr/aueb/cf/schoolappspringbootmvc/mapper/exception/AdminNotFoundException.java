package gr.aueb.cf.schoolappspringbootmvc.mapper.exception;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String message) {
        super(message);
    }
}
