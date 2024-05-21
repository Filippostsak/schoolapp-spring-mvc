package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Admin} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
