package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserCreateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import gr.aueb.cf.schoolappspringbootmvc.mapper.UserMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.AdminRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.StudentRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link IUserService} interface.
 * Provides methods for retrieving user details by username.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified username is not found.
     */
    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        try{
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        } catch (UsernameNotFoundException e) {
            log.error("User with username {} not found", username);
            throw e;
        }
    }

    /**
     * Retrieves a teacher by their username.
     *
     * @param username the username of the teacher to be retrieved.
     * @return the teacher with the specified username.
     * @throws UsernameNotFoundException if a teacher with the specified username is not found.
     */

    @Override
    public User getTeacherIdByUsername(String username) {
        try{
            return userRepository.findTeacherIdByUsername(username);
        } catch (UsernameNotFoundException e) {
            log.error("Teacher with username {} not found", username);
            throw e;
        }
    }

    /**
     * Retrieves a student by their email.
     *
     * @param email the email of the student to be retrieved.
     * @return the student with the specified email.
     * @throws UsernameNotFoundException if a student with the specified email is not found.
     */

    @Override
    public User getStudentIdByEmail(String email) {
        try{
            return userRepository.findStudentIdByEmail(email);
        } catch (UsernameNotFoundException e) {
            log.error("Student with email {} not found", email);
            throw e;
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified username is not found.
     */

    @Override
    public User getUserIdByUsername(String username) {
        try{
            return userRepository.findUserIdByUsername(username);
        } catch (UsernameNotFoundException e) {
            log.error("User with username {} not found", username);
            throw e;
        }
    }

    /**
     * Retrieves a user by their id.
     *
     * @param id the id of the user to be retrieved.
     * @return the user with the specified id.
     * @throws UsernameNotFoundException if a user with the specified id is not found.
     */

    @Override
    public Optional<UserGetUsernameDTO> getUsernameById(Long id) {
        try{
            return userRepository.findUsernameById(id);
        } catch (UsernameNotFoundException e) {
            log.error("User with id {} not found", id);
            throw e;
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the id of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified id is not found.
     */

    @Override
    public Optional<User> findByUsername(String username) {
        try{
            log.info("Retrieving user with username {}", username);
            return userRepository.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            log.error("User with username {} not found", username);
            throw new RuntimeException("User with username " + username + " not found", e);
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the email of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified email is not found.
     */

    @Override
    public Optional<UserDTO> findUserByUsername(String username) {
        log.info("Retrieving user with username {}", username);
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.error("User with username {} not found", username);
            throw new RuntimeException("User with username " + username + " not found");
        }
        return Optional.ofNullable(userMapper.mapUserToUserDTO(user));
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to be retrieved.
     * @return the user with the specified email.
     * @throws UsernameNotFoundException if a user with the specified email is not found.
     */

    @Override
    public Optional<UserDTO> findUserByEmail(String email) {
        try{
            log.info("Retrieving user with email {}", email);
            return Optional.ofNullable(userMapper.mapUserToUserDTO(userRepository.findUserByEmail(email)));
        } catch (UsernameNotFoundException e) {
            log.error("User with email {} not found", email);
            throw new RuntimeException("User with email " + email + " not found", e);
        }
    }

    /**
     * Retrieves a user by their id.
     *
     * @param id the id of the user to be retrieved.
     * @return the user with the specified id.
     * @throws UsernameNotFoundException if a user with the specified id is not found.
     */

    @Override
    public UserDTO findUserById(Long id) {
        try{
            log.info("Retrieving user with id {}", id);
            return userMapper.mapUserToUserDTO(userRepository.findUserById(id));
        } catch (UsernameNotFoundException e) {
            log.error("User with id {} not found", id);
            throw new RuntimeException("User with id " + id + " not found", e);
        }
    }

    /**
     * Retrieves a user by their role.
     *
     * @param role the role of the user to be retrieved.
     * @return the user with the specified role.
     * @throws UsernameNotFoundException if a user with the specified role is not found.
     */

    @Override
    public List<UserDTO> findUserByRole(Role role) {
        try{
            log.info("Retrieving users with role {}", role);
            return userRepository.findUserByRole(role).stream()
                    .map(userMapper::mapUserToUserDTO)
                    .collect(Collectors.toList());
        } catch (UsernameNotFoundException e) {
            log.error("Users with role {} not found", role);
            throw new RuntimeException("Users with role " + role + " not found", e);
        }
    }

    /**
     * Retrieves a user by their status.
     *
     * @param status the status of the user to be retrieved.
     * @return the user with the specified status.
     * @throws UsernameNotFoundException if a user with the specified status is not found.
     */

    @Override
    public List<UserDTO> findUserByStatus(Status status) {
        try {
            log.info("Retrieving users with status {}", status);
            return userRepository.findUserByStatus(status).stream()
                    .map(userMapper::mapUserToUserDTO)
                    .collect(Collectors.toList());
        } catch (UsernameNotFoundException e) {
            log.error("Users with status {} not found", status);
            throw new RuntimeException("Users with status " + status + " not found", e);
        }
    }

    /**
     * Retrieves a user by their id.
     * @param userId the id of the user to be retrieved.
     * @throws UsernameNotFoundException if a user with the specified id is not found.
     */

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getAdmin() != null) {
            adminRepository.delete(user.getAdmin());
        }
        if (user.getTeacher() != null) {
            teacherRepository.delete(user.getTeacher());
        }
        if (user.getStudent() != null) {
            studentRepository.delete(user.getStudent());
        }
        userRepository.deleteById(userId);
    }


    /**
     * Updates a user based on the provided data transfer object.
     * The user is saved to the database.
     */

    @Override
    public Optional<User> updateUser(UserUpdateDTO dto) {
        try {
            log.info("Updating user with id {}", dto.getId());
            User existingUser = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userMapper.updateUserFromDto(dto, existingUser);

            log.info("User with id {} updated", dto.getId());
            return Optional.of(userRepository.save(existingUser));
        } catch (Exception e) {
            log.error("Error updating user with id {}", dto.getId(), e);
            throw new RuntimeException("User with id " + dto.getId() + " not found", e);
        }
    }

    /**
     * Creates a new user based on the provided data transfer object.
     * The user is saved to the database along with the corresponding role.
     * The password is encoded before saving.
     */

    @Override
    @Transactional
    public void createUser(UserCreateDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username " + dto.getUsername() + " already exists");
        }

        User user = UserMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        switch (dto.getRole()) {
            case TEACHER:
                Teacher teacher = UserMapper.toTeacher(user, dto);
                teacherRepository.save(teacher);
                break;
            case STUDENT:
                Student student = UserMapper.toStudent(user, dto);
                studentRepository.save(student);
                break;
            case ADMIN:
                Admin admin = UserMapper.toAdmin(user, dto);
                adminRepository.save(admin);
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + dto.getRole());
        }
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     * @throws UsernameNotFoundException if there are no users.
     */

    @Override
    public Page<UserDTO> findAllUsers(Pageable pageable) {
        try {
            log.info("Retrieving all users");
            return userRepository.findAll(pageable)
                    .map(userMapper::mapUserToUserDTO);
        } catch (UsernameNotFoundException e) {
            log.error("No users found");
            throw new RuntimeException("No users found", e);
        }
    }

    /**
     * Searches for users based on a keyword.
     *
     * @param keyword the keyword to search for in usernames.
     * @return a list of users whose usernames contain the specified keyword.
     * @throws UsernameNotFoundException if no users are found.
     */
    @Override
    public Page<UserDTO> searchUsers(String keyword, Pageable pageable) {
        try{
            log.info("Searching users with keyword {}", keyword);
            return userRepository.searchUsers(keyword, pageable).map(userMapper::mapUserToUserDTO);
        }catch (UsernameNotFoundException e) {
            log.error("No users found");
            throw new RuntimeException("No users found", e);
        }
    }
}
