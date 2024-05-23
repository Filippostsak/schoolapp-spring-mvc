package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Student} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByLastnameContainingOrderByLastname(String lastName);
    List<Student> findByLastnameContainingIgnoreCase(String lastname);
}
