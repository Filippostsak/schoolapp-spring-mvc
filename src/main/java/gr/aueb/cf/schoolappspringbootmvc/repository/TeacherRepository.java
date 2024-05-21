package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Teacher} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
