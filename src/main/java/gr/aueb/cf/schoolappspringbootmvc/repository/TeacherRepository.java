package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Teacher} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    /**
     * Finds a teacher by the first name.
     *
     * @param username the first name of the teacher
     * @return the teacher with the given first name
     */

    Optional<Teacher> findByUserUsername(String username);

    /**
     * Finds teachers by the first name.
     *
     * @param username the first name of the teacher
     * @return the teachers with the given first name
     */

    List<Teacher> findByUserUsernameContaining(String username);

    /**
     * Finds a teacher by the first name.
     *
     * @param firstname the first name of the teacher
     * @return the teacher with the given first name
     */

    Teacher findByFirstname(String firstname);

    /**
     * Finds a teacher by the last name.
     *
     * @param teacherId the id of the teacher
     * @return the teacher with the given id
     */
    @Query("SELECT t.user.username FROM Teacher t WHERE t.id = :teacherId")
    String findUsernameByTeacherId(Long teacherId);

    /**
     * Finds a teacher by the last name.
     *
     * @param teacherId the id of the teacher
     * @return the teacher with the given id
     */
    @Query("SELECT t.user.id FROM Teacher t WHERE t.id = :teacherId")
    Long findUserIdByTeacherId(Long teacherId);

}
