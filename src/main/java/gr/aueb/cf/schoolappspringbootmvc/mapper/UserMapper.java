package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.GetTeachersIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserCreateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The Mapper class provides methods to convert DTOs to domain models.
 * This class is designed to convert registration DTOs to their respective
 * User, Teacher, Student, and Admin models.
 * This class cannot be instantiated.
 */
@Component
public class UserMapper {

    // Private constructor to prevent instantiation
    private UserMapper() {
    }

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

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());

        if (user.getRole().equals(Role.TEACHER)) {
            if (user.getTeacher() != null) {
                userDTO.setFirstname(user.getTeacher().getFirstname());
                userDTO.setLastname(user.getTeacher().getLastname());
            }
        } else if (user.getRole().equals(Role.STUDENT)) {
            if (user.getStudent() != null) {
                userDTO.setFirstname(user.getStudent().getFirstname());
                userDTO.setLastname(user.getStudent().getLastname());
            }
        } else if (user.getRole().equals(Role.ADMIN)) {
            if (user.getAdmin() != null) {
                userDTO.setFirstname(user.getAdmin().getFirstname());
                userDTO.setLastname(user.getAdmin().getLastname());
            }
        }

        return userDTO;
    }

    public User mapUserDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        if (dto.getRole().equals(Role.TEACHER)) {
            Teacher teacher = new Teacher();
            teacher.setFirstname(dto.getFirstname());
            teacher.setLastname(dto.getLastname());
            user.setTeacher(teacher);
        } else if (dto.getRole().equals(Role.STUDENT)) {
            Student student = new Student();
            student.setFirstname(dto.getFirstname());
            student.setLastname(dto.getLastname());
            user.setStudent(student);
        } else if (dto.getRole().equals(Role.ADMIN)) {
            Admin admin = new Admin();
            admin.setFirstname(dto.getFirstname());
            admin.setLastname(dto.getLastname());
            user.setAdmin(admin);
        }
        return user;
    }

    public User mapUserUpdateDTOToUser(UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO == null) {
            throw new IllegalArgumentException("UserUpdateDTO cannot be null");
        }

        User user = new User();
        user.setId(userUpdateDTO.getId());
        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());
        user.setRole(userUpdateDTO.getRole());
        user.setStatus(userUpdateDTO.getStatus());

        if (userUpdateDTO.getRole() != null) {
            switch (userUpdateDTO.getRole()) {
                case TEACHER:
                    Teacher teacher = new Teacher();
                    teacher.setFirstname(userUpdateDTO.getFirstname());
                    teacher.setLastname(userUpdateDTO.getLastname());
                    user.setTeacher(teacher);
                    break;
                case STUDENT:
                    Student student = new Student();
                    student.setFirstname(userUpdateDTO.getFirstname());
                    student.setLastname(userUpdateDTO.getLastname());
                    user.setStudent(student);
                    break;
                case ADMIN:
                    Admin admin = new Admin();
                    admin.setFirstname(userUpdateDTO.getFirstname());
                    admin.setLastname(userUpdateDTO.getLastname());
                    user.setAdmin(admin);
                    break;
                default:
                    break;
            }
        }
        return user;
    }

    public UserUpdateDTO toUserUpdateDTO(User user) {
        if (user == null) {
            return null;
        }

        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());

        if (user.getRole() != null) {
            switch (user.getRole()) {
                case TEACHER:
                    if (user.getTeacher() != null) {
                        dto.setFirstname(user.getTeacher().getFirstname());
                        dto.setLastname(user.getTeacher().getLastname());
                    }
                    break;
                case STUDENT:
                    if (user.getStudent() != null) {
                        dto.setFirstname(user.getStudent().getFirstname());
                        dto.setLastname(user.getStudent().getLastname());
                    }
                    break;
                case ADMIN:
                    if (user.getAdmin() != null) {
                        dto.setFirstname(user.getAdmin().getFirstname());
                        dto.setLastname(user.getAdmin().getLastname());
                    }
                    break;
                default:
                    // handle other roles if any
                    break;
            }
        }

        return dto;
    }

    public User updateUserFromDto(UserUpdateDTO dto, User user) {
        if (dto == null || user == null) {
            throw new IllegalArgumentException("DTO and User cannot be null");
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setIsActive(dto.getActive());
        if (dto.getActive() == null) {
            user.setIsActive(true);
        }

        if (dto.getRole() != null) {
            switch (dto.getRole()) {
                case TEACHER:
                    if (user.getTeacher() == null) {
                        user.setTeacher(new Teacher());
                    }
                    user.getTeacher().setFirstname(dto.getFirstname());
                    user.getTeacher().setLastname(dto.getLastname());
                    break;
                case STUDENT:
                    if (user.getStudent() == null) {
                        user.setStudent(new Student());
                    }
                    user.getStudent().setFirstname(dto.getFirstname());
                    user.getStudent().setLastname(dto.getLastname());
                    break;
                case ADMIN:
                    if (user.getAdmin() == null) {
                        user.setAdmin(new Admin());
                    }
                    user.getAdmin().setFirstname(dto.getFirstname());
                    user.getAdmin().setLastname(dto.getLastname());
                    break;
                default:
                    // handle other roles if any
                    break;
            }
        }

        return user;
    }


    public static UserCreateDTO toUserCreateDTO(User user) {
        if (user == null) {
            return null;
        }

        UserCreateDTO dto = new UserCreateDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setBirthDate(user.getBirthDate());
        dto.setCountry(user.getCountry());
        dto.setCity(user.getCity());

        if (user.getRole() != null) {
            switch (user.getRole()) {
                case TEACHER:
                    if (user.getTeacher() != null) {
                        dto.setFirstname(user.getTeacher().getFirstname());
                        dto.setLastname(user.getTeacher().getLastname());
                    }
                    break;
                case STUDENT:
                    if (user.getStudent() != null) {
                        dto.setFirstname(user.getStudent().getFirstname());
                        dto.setLastname(user.getStudent().getLastname());
                    }
                    break;
                case ADMIN:
                    if (user.getAdmin() != null) {
                        dto.setFirstname(user.getAdmin().getFirstname());
                        dto.setLastname(user.getAdmin().getLastname());
                    }
                    break;
                default:
                    break;
            }
        }

        return dto;
    }

    public static User toUser(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBirthDate(dto.getBirthDate());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.APPROVED);
        user.setIsActive(true);
        return user;
    }

    public static Teacher toTeacher(User user, UserCreateDTO dto) {
        Teacher teacher = new Teacher(dto.getFirstname(), dto.getLastname());
        teacher.setUser(user);
        return teacher;
    }

    public static Student toStudent(User user, UserCreateDTO dto) {
        Student student = new Student(dto.getFirstname(), dto.getLastname());
        student.setUser(user);
        return student;
    }

    public static Admin toAdmin(User user, UserCreateDTO dto) {
        Admin admin = new Admin(dto.getFirstname(), dto.getLastname());
        admin.setUser(user);
        return admin;
    }
}


