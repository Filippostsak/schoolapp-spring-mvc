package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by the username.
     *
     * @param username the username of the user
     * @return the user with the given username
     */

    Optional<User> findByUsername(String username);

    /**
     * Finds a user by the email.
     *
     * @param username the username of the user
     * @return the id with the given username
     */

    User findTeacherIdByUsername(String username);

    /**
     * Finds a user by the email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    User findStudentIdByEmail(String email);

    /**
     * Finds a user by the email.
     *
     * @param username the username of the user
     * @return the user id with the given username
     */
    User findUserIdByUsername(String username);

    /**
     * Finds a user by the email.
     *
     * @param id the id of the user
     * @return the username with the given id
     */
    @Query("SELECT new gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO(u.id, u.username) FROM User u WHERE u.id = :id")
    Optional<UserGetUsernameDTO> findUsernameById(Long id);

}

