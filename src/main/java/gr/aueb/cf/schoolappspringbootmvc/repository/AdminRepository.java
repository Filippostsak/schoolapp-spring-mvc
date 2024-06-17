package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Admin} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Finds an admin by the username.
     *
     * @param username the username of the admin
     * @return the admin with the given username
     */

    Optional<Admin> findByUserUsername(String username);



}
