package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.StudentRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    /**
     * Registers a new student.
     *
     * @param dto the data transfer object containing student registration information.
     * @return the registered student.
     * @throws StudentAlreadyExistsException if a student with the specified username already exists.
     */
    @Override
    public Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException {
        Student student;
        User user;
        try {
            student = Mapper.extractStudentFromRegisterStudentDTO(dto);
            user = Mapper.extractUserFromRegisterStudentDTO(dto);
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                throw new StudentAlreadyExistsException(user.getUsername());
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            student.addUser(user);
            studentRepository.save(student);
        } catch (StudentAlreadyExistsException e) {
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
        return studentRepository.findAll();
    }
}
