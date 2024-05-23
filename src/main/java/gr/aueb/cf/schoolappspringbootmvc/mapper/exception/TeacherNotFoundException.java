package gr.aueb.cf.schoolappspringbootmvc.mapper.exception;

public class TeacherNotFoundException extends RuntimeException{

        private static final long serialVersionUID = 1L;

        public TeacherNotFoundException(String username) {
            super("Teacher not found: " + username);
        }
}
