package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserCreateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
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

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified username is not found.
     */

    Optional<User> findByUsername(String username);

    /**
     * Retrieves a user by their username.
     *
     * @param username the email of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified username is not found.
     */

    Optional<UserDTO> findUserByUsername(String username);

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to be retrieved.
     * @return the user with the specified email.
     * @throws UsernameNotFoundException if a user with the specified email is not found.
     */

    Optional<UserDTO> findUserByEmail(String email);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return the user with the specified ID.
     * @throws UsernameNotFoundException if a user with the specified ID is not found.
     */

    UserDTO findUserById(Long id);

    /**
     * Retrieves a user by their role.
     *
     * @param role the role of the user to be retrieved.
     * @return the user with the specified role.
     * @throws UsernameNotFoundException if a user with the specified role is not found.
     */

    List<UserDTO> findUserByRole(Role role);

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */

    Page<UserDTO> findAllUsers(Pageable pageable);

    /**
     * Retrieves a user by their status.
     *
     * @param status the status of the user to be retrieved.
     * @return the user with the specified status.
     * @throws UsernameNotFoundException if a user with the specified status is not found.
     */

    List<UserDTO> findUserByStatus(Status status);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     */

    void deleteUserById(Long id);

    /**
     * Updates a user.
     *
     * @param dto the user to be updated.
     * @return the updated user.
     */

    Optional<User> updateUser(UserUpdateDTO dto);

    /**
     * Creates a user.
     *
     * @param userCreateDTO the user to be created.
     */

    void createUser(UserCreateDTO userCreateDTO);


    /**
     * Searches for users based on a keyword.
     *
     * @param keyword the keyword to search for.
     * @return a list of users that match the keyword.
     */
    Page<UserDTO> searchUsers(String keyword, Pageable pageable);
}
