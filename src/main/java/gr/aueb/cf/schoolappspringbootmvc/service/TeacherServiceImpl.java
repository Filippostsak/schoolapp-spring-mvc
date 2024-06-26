package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.GetTeachersIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.UserMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ITeacherService} interface.
 * Provides methods for registering and retrieving teachers.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements ITeacherService {


    /**
     * The repository for managing teachers.
     */
    private final TeacherRepository teacherRepository;

    /**
     * The repository for managing users.
     */
    private final UserRepository userRepository;

    /**
     * The password encoder for encoding user passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The service for managing students.
     */
    private final StudentServiceImpl studentServiceImpl;

    /**
     * Registers a new teacher.
     *
     * @param dto the data transfer object containing teacher registration information.
     * @return the registered teacher.
     * @throws TeacherAlreadyExistsException if a teacher with the specified username already exists.
     */

    @Override
    public Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException {
        log.info("Attempting to register a new teacher with username: {}", dto.getUsername());
        Teacher teacher;
        User user;
        try {
            teacher = UserMapper.extractTeacherFromRegisterTeacherDTO(dto);
            user = UserMapper.extractUserFromRegisterTeacherDTO(dto);
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                log.error("Teacher registration failed: Username {} already exists", user.getUsername());
                throw new TeacherAlreadyExistsException(user.getUsername());
            }
            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            teacher.addUser(user);
            teacherRepository.save(teacher);
            log.info("Teacher registered successfully with username: {}", user.getUsername());
        } catch (TeacherAlreadyExistsException e) {
            log.error("Teacher registration failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred during teacher registration", e);
            throw new RuntimeException("Unexpected error occurred during teacher registration", e);
        }
        return teacher;
    }

    /**
     * Retrieves all teachers.
     *
     * @return a list of all teachers.
     * @throws Exception if an error occurs while retrieving the teachers.
     */

    @Override
    public List<Teacher> findAllTeachers() throws Exception {
        log.info("Retrieving all teachers");
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            log.info("Successfully retrieved all teachers");
            return teachers;
        } catch (Exception e) {
            log.error("Error retrieving all teachers", e);
            throw new Exception("Error retrieving all teachers", e);
        }
    }

    /**
     * Searches for students by their last name.
     *
     * @param lastName the last name to search for.
     * @return a list of students with matching last names.
     */

    @Override
    public List<Student> searchStudentsByLastName(String lastName) {
        log.info("Searching for students with last name: {}", lastName);
        try {
            List<Student> students = studentServiceImpl.findByLastnameContainingOrderByLastname(lastName);
            log.info("Successfully found {} students with last name: {}", students.size(), lastName);
            return students;
        } catch (Exception e) {
            log.error("Error searching for students with last name: {}", lastName, e);
            throw new RuntimeException("Error searching for students", e);
        }
    }

    /**
     * Finds a teacher by their first name.
     *
     * @param firstname the first name to search for.
     * @return the teacher with the matching first name.
     */

    @Override
    public Teacher findTeacherByFirstname(String firstname) {
        log.info("Searching for teacher with first name: {}", firstname);
        try {
            Teacher teacher = teacherRepository.findByFirstname(firstname);
            if (teacher == null) {
                log.warn("No teacher found with first name: {}", firstname);
            } else {
                log.info("Teacher found with first name: {}", firstname);
            }
            return teacher;
        } catch (Exception e) {
            log.error("Error searching for teacher with first name: {}", firstname, e);
            throw new RuntimeException("Error searching for teacher", e);
        }
    }

    /**
     * Retrieves the currently authenticated teacher.
     *
     * @return an Optional containing the current teacher if found, otherwise empty.
     */

    @Override
    public Optional<Teacher> getCurrentAuthenticatedTeacher() {
        String username = getCurrentUsername();
        log.info("Retrieving currently authenticated teacher with username: {}", username);
        try {
            Optional<Teacher> teacherOptional = teacherRepository.findByUserUsername(username);
            if (teacherOptional.isPresent()) {
                log.info("Currently authenticated teacher found with username: {}", username);
            } else {
                log.warn("No authenticated teacher found with username: {}", username);
            }
            return teacherOptional;
        } catch (Exception e) {
            log.error("Error retrieving currently authenticated teacher with username: {}", username, e);
            throw new RuntimeException("Error retrieving currently authenticated teacher", e);
        }
    }

    /**
     * Retrieves a teacher by their username.
     *
     * @param username the username to search for.
     * @return an Optional containing the teacher with the matching username if found, otherwise empty.
     */

    @Override
    public Optional<Teacher> findByUsername(String username) {
        log.info("Searching for teacher with username: {}", username);
        try {
            Optional<Teacher> teacherOptional = teacherRepository.findByUserUsername(username);
            if (teacherOptional.isPresent()) {
                log.info("Teacher found with username: {}", username);
            } else {
                log.warn("No teacher found with username: {}", username);
            }
            return teacherOptional;
        } catch (Exception e) {
            log.error("Error searching for teacher with username: {}", username, e);
            throw new RuntimeException("Error searching for teacher", e);
        }
    }

    /**
     * Retrieves a teacher by their username containing the specified string.
     * @param username the string to search for in the username.
     * @return an Optional containing the teacher with the matching username if found, otherwise empty.
     * @throws Exception if an error occurs while searching for teachers.
     */

    @Override
    public List<Teacher> findByUsernameContaining(String username) throws Exception {
        log.info("Searching for teachers with username containing: {}", username);
        try {
            List<Teacher> teachers = teacherRepository.findByUserUsernameContaining(username);
            log.info("Successfully found {} teachers with username containing: {}", teachers.size(), username);
            return teachers;
        } catch (Exception e) {
            log.error("Error searching for teachers with username containing: {}", username, e);
            throw new Exception("Error searching for teachers", e);
        }
    }

    /**
     * Retrieves a teacher by their id.
     * @param id the id to search for.
     * @return an Optional containing the teacher with the matching id if found, otherwise empty.
     */

    @Override
    public Optional<GetTeachersIdDTO> findById(Long id) throws Exception {
        //get current authenticated teacher id
        log.info("Searching for teacher with id: {}", id);
        //return id, firstname, lastname
        try {
            Optional<Teacher> teacherOptional = teacherRepository.findById(id);
            if (teacherOptional.isPresent()) {
                log.info("Teacher found with id: {}", id);
                GetTeachersIdDTO getTeachersIdDTO = UserMapper.extractGetTeachersIdDTOFromTeacher(teacherOptional.get());
                return Optional.of(getTeachersIdDTO);
            } else {
                log.warn("No teacher found with id: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error searching for teacher with id: {}", id, e);
            throw new Exception("Error searching for teacher", e);
        }
    }

    /**
     * Retrieves the username of the currently authenticated user.
     * @return the username of the currently authenticated user.
     */

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * Retrieves the teacher with the specified teacher ID.
     *
     * @param teacherId the ID of the teacher to retrieve.
     * @return the teacher with the specified teacher ID.
     * @throws RuntimeException if the teacher is not found.
     */

    @Override
    public Teacher getUserIdByTeacherId(Long teacherId) {
        try{
            return teacherRepository.findByUserUsername(teacherRepository.findUsernameByTeacherId(teacherId)).orElseThrow(() -> new RuntimeException("Teacher not found"));
        } catch (Exception e) {
            log.error("Error retrieving teacher by teacher id: {}", teacherId, e);
            throw new RuntimeException("Error retrieving teacher by teacher id", e);
        }
    }
}
