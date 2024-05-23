package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Teacher} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserUsername(String username);
    List<Teacher> findByClassrooms_Id(Long classroomId);
    Teacher findByFirstname(String firstname);

}
