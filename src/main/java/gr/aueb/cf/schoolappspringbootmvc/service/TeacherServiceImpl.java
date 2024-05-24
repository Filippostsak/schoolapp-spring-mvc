package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
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

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
            teacher = Mapper.extractTeacherFromRegisterTeacherDTO(dto);
            user = Mapper.extractUserFromRegisterTeacherDTO(dto);
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

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
