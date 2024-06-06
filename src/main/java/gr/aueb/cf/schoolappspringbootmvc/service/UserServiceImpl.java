package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link IUserService} interface.
 * Provides methods for retrieving user details by username.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;

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
}
