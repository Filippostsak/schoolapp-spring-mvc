package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their role.
     *
     * @param role the role of the user.
     * @return an {@link Optional} containing the user with the specified role, or {@link Optional#empty()} if no user found.
     */
    Optional<User> findByRole(Role role);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user.
     * @return an {@link Optional} containing the user with the specified username, or {@link Optional#empty()} if no user found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Counts the number of users with the specified role.
     *
     * @param role the role of the users to count.
     * @return the number of users with the specified role.
     */
    Long countByRole(Role role);

    /**
     * Counts the number of users with the specified role and status.
     *
     * @param role   the role of the users to count.
     * @param status the status of the users to count.
     * @return the number of users with the specified role and status.
     */

}
