package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * Service interface for managing users.
 */
public interface IUserService {

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified username is not found.
     */
    User getUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves a teacher by their username.
     *
     * @param username the username of the teacher to be retrieved.
     * @return the teacher with the specified username.
     * @throws UsernameNotFoundException if a teacher with the specified username is not found.
     */
    User getTeacherIdByUsername(String username);

    /**
     * Retrieves a student by their email.
     *
     * @param email the email of the student to be retrieved.
     * @return the student with the specified email.
     * @throws UsernameNotFoundException if a student with the specified email is not found.
     */
    User getStudentIdByEmail(String email);

    /**
     * Retrieves a user by their ID.
     *
     * @param username the username of the user to be retrieved.
     * @return the user with the specified ID.
     * @throws UsernameNotFoundException if a user with the specified ID is not found.
     */
    User getUserIdByUsername(String username);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return the user with the specified ID.
     * @throws UsernameNotFoundException if a user with the specified ID is not found.
     */
    Optional<UserGetUsernameDTO> getUsernameById(Long id);




}
