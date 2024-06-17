package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserGetUsernameDTO;
import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserById(Long id);

    List<User> findUserByRole(Role role);

    List<User> findUserByStatus(Status status);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteById(@Param("id") Long id);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN u.teacher t " +
            "LEFT JOIN u.student s " +
            "LEFT JOIN u.admin a " +
            "WHERE " +
            "(:keyword IS NULL OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(CAST(u.role AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(CAST(u.status AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.lastname) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);

}

