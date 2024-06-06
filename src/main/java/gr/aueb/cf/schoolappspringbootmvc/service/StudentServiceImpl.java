package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetCurrentStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetUsernameAndIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.StudentMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.StudentRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link IStudentService} interface.
 * Provides methods for registering and retrieving students.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMapper studentMapper;

    /**
     * Registers a new student.
     *
     * @param dto the data transfer object containing student registration information.
     * @return the registered student.
     * @throws StudentAlreadyExistsException if a student with the specified username already exists.
     */
    @Override
    @Transactional
    public Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException {
        log.debug("Attempting to register student with username: {}", dto.getUsername());
        Student student;
        User user;
        try {
            student = Mapper.extractStudentFromRegisterStudentDTO(dto);
            user = Mapper.extractUserFromRegisterStudentDTO(dto);
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                log.warn("Student with username: {} already exists", user.getUsername());
                throw new StudentAlreadyExistsException(user.getUsername());
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            student.addUser(user);
            studentRepository.save(student);
            log.info("Student registered successfully with username: {}", user.getUsername());
        } catch (StudentAlreadyExistsException e) {
            log.error("Error registering student: {}", e.getMessage());
            throw e;
        }
        return student;
    }

    /**
     * Retrieves all students.
     *
     * @return a list of all students.
     * @throws Exception if an error occurs while retrieving the students.
     */
    @Override
    public List<Student> findAllStudents() throws Exception {
        try {
            log.debug("Retrieving all students");
            List<Student> students = studentRepository.findAll();
            log.info("Retrieved {} students", students.size());
            return students;
        } catch (Exception e) {
            log.error("Error retrieving students: {}", e.getMessage());
            throw new Exception("Error retrieving students", e);
        }
    }


    /**
     * Searches for students whose last names contain the specified substring, ordered by their last names.
     * @param lastname the substring to search for in last names
     * @return a list of students whose last names contain the specified substring
     */

    @Override
    public List<Student> findByLastnameContainingOrderByLastname(String lastname) {
        log.debug("Searching for students with lastname containing: {}", lastname);
        List<Student> students = studentRepository.findByLastnameContainingOrderByLastname(lastname);
        log.info("Found {} students with lastname containing: {}", students.size(), lastname);
        return students;
    }

    /**
     * Searches for students by their last names.
     *
     * @param lastname the last name to search for.
     * @return a list of students with matching last names.
     */

    @Override
    public List<Student> searchStudentsByLastname(String lastname) {
        log.debug("Searching for students with lastname containing (ignore case): {}", lastname);
        List<Student> students = studentRepository.findByLastnameContainingIgnoreCase(lastname);
        log.info("Found {} students with lastname containing (ignore case): {}", students.size(), lastname);
        return students;
    }

    /**
     * Retrieves the classrooms associated with a specific student.
     *
     * @param studentId the ID of the student.
     * @return a DTO containing the student's classroom information.
     * @throws ClassroomNotFoundException if the classrooms for the student are not found.
     */

    @Override
    public SearchStudentToClassroomDTO getStudentClassrooms(Long studentId) throws ClassroomNotFoundException {
        try {
            log.debug("Retrieving classrooms for student with id: {}", studentId);
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
            SearchStudentToClassroomDTO dto = studentMapper.toSearchStudentToClassroomDTO(student);
            log.info("Retrieved classrooms for student with id: {}", studentId);
            return dto;
        } catch (StudentNotFoundException e) {
            log.error("Student not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving student classrooms: {}", e.getMessage());
            throw new ClassroomNotFoundException("Error retrieving student classrooms");
        }
    }

    /**
     * Retrieves the currently authenticated student.
     *
     * @return a DTO containing the current student's information.
     * @throws StudentNotFoundException if the authenticated student is not found.
     */

    @Override
    public StudentGetCurrentStudentDTO getCurrentAuthenticatedStudent(StudentGetCurrentStudentDTO dto) throws StudentNotFoundException {
        try {
            log.debug("Retrieving current authenticated student");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Student student = studentRepository.findByUserUsername(username)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with username: " + username));
            dto = studentMapper.toGetCurrentStudentDTO(student);
            log.info("Retrieved current authenticated student: {}", dto);
            return dto;
        } catch (StudentNotFoundException e) {
            log.error("Student not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving current authenticated student: {}", e.getMessage());
            throw new StudentNotFoundException("Error retrieving current authenticated student");
        }
    }

    /**
     * Retrieves the student with the specified username.
     *
     * @param username the username of the student to be retrieved.
     * @return the student with the specified username.
     * @throws StudentNotFoundException if the student is not found.
     */

    @Override
    public Student getStudentIdByUsername(String username) throws StudentNotFoundException {
        try{
            log.debug("Retrieving student with username: {}", username);
            Student student = studentRepository.findByUserUsername(username)
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with username: " + username));
            log.info("Retrieved student with username: {}", username);
            return student;
        } catch (StudentNotFoundException e) {
            log.error("Student not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving student with username: {}", e.getMessage());
            throw new StudentNotFoundException("Error retrieving student with username: " + username);
        }
    }

    /**
     * Retrieves the student with the specified classroom ID.
     *
     * @param classroomId the ID of the classroom.
     * @return the student with the specified ID.
     * @throws StudentNotFoundException if the student is not found.
     */

    @Override
    public List<StudentGetUsernameAndIdDTO> findStudentUsernamesAndIdsByClassroomId(Long classroomId) {
        try{
            log.debug("Retrieving student usernames and ids by classroom id: {}", classroomId);
            List<StudentGetUsernameAndIdDTO> studentGetUsernameAndIdDTOS = studentRepository.findStudentUsernamesAndIdsByClassroomId(classroomId);
            log.info("Retrieved student usernames and ids by classroom id: {}", classroomId);
            return studentGetUsernameAndIdDTOS;
        }catch (Exception e){
            log.error("Error retrieving student usernames and ids by classroom id: {}", e.getMessage());
            throw e;
        }
    }
}
