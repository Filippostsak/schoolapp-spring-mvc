package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Student} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
