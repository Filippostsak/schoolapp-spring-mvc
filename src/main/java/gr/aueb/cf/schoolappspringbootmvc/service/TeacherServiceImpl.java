package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * Registers a new teacher.
     *
     * @param dto the data transfer object containing teacher registration information.
     * @return the registered teacher.
     * @throws TeacherAlreadyExistsException if a teacher with the specified username already exists.
     */
    @Override
    public Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException {
        Teacher teacher;
        User user;
        try {
            teacher = Mapper.extractTeacherFromRegisterTeacherDTO(dto);
            user = Mapper.extractUserFromRegisterTeacherDTO(dto);
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                throw new TeacherAlreadyExistsException(user.getUsername());
            }
            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            teacher.addUser(user);
            teacherRepository.save(teacher);
        } catch (TeacherAlreadyExistsException e) {
            throw e;
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
        return teacherRepository.findAll();
    }
}
