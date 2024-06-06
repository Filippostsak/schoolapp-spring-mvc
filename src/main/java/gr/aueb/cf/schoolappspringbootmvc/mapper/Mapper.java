package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.GetTeachersIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;

/**
 * The Mapper class provides methods to convert DTOs to domain models.
 * This class is designed to convert registration DTOs to their respective
 * User, Teacher, Student, and Admin models.
 *
 * This class cannot be instantiated.
 */
public class Mapper {

    // Private constructor to prevent instantiation
    private Mapper() {}

    /**
     * Converts a RegisterTeacherDTO to a Teacher model.
     *
     * @param dto the RegisterTeacherDTO to convert
     * @return the corresponding Teacher model
     */
    public static Teacher extractTeacherFromRegisterTeacherDTO(RegisterTeacherDTO dto) {
        Teacher teacher = new Teacher(dto.getFirstname(), dto.getLastname());
        User user = User.NEW_TEACHER(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBirthDate(),
                dto.getCountry(),
                dto.getCity()
        );
        teacher.setUser(user);
        return teacher;
    }

    /**
     * Converts a RegisterTeacherDTO to a User model with TEACHER role.
     *
     * @param dto the RegisterTeacherDTO to convert
     * @return the corresponding User model with TEACHER role
     */
    public static User extractUserFromRegisterTeacherDTO(RegisterTeacherDTO dto) {
        return User.NEW_TEACHER(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBirthDate(),
                dto.getCountry(),
                dto.getCity()
        );
    }

    /**
     * Converts a RegisterStudentDTO to a Student model.
     *
     * @param dto the RegisterStudentDTO to convert
     * @return the corresponding Student model
     */
    public static Student extractStudentFromRegisterStudentDTO(RegisterStudentDTO dto) {
        Student student = new Student(dto.getFirstname(), dto.getLastname());
        User user = User.NEW_STUDENT(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBirthDate(),
                dto.getCountry(),
                dto.getCity()
        );
        student.setUser(user);
        return student;
    }

    /**
     * Converts a RegisterStudentDTO to a User model with STUDENT role.
     *
     * @param dto the RegisterStudentDTO to convert
     * @return the corresponding User model with STUDENT role
     */
    public static User extractUserFromRegisterStudentDTO(RegisterStudentDTO dto) {
        return User.NEW_STUDENT(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBirthDate(),
                dto.getCountry(),
                dto.getCity()
        );
    }

    /**
     * Converts a RegisterAdminDTO to an Admin model.
     *
     * @param dto the RegisterAdminDTO to convert
     * @return the corresponding Admin model
     */
    public static Admin extractAdminFromRegisterAdminDTO(RegisterAdminDTO dto) {
        Admin admin = new Admin(dto.getFirstname(), dto.getLastname());
        User user = User.NEW_ADMIN(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBirthDate(),
                dto.getCountry(),
                dto.getCity()
        );
        admin.setUser(user);
        return admin;
    }

    /**
     * Converts a RegisterAdminDTO to a User model with ADMIN role.
     *
     * @param dto the RegisterAdminDTO to convert
     * @return the corresponding User model with ADMIN role
     */
    public static User extractUserFromRegisterAdminDTO(RegisterAdminDTO dto) {
        return User.NEW_ADMIN(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBirthDate(),
                dto.getCountry(),
                dto.getCity()
        );
    }

    /**
     * Converts a Teacher model to a GetTeachersIdDTO.
     *
     * @param teacher the Teacher model to convert
     * @return the corresponding GetTeachersIdDTO
     */

    public static GetTeachersIdDTO extractGetTeachersIdDTOFromTeacher(Teacher teacher) {
        return new GetTeachersIdDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname());
    }
}
