package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetUsernameAndIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Student} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Finds a student by the last name.
     *
     * @param lastName the last name of the student
     * @return the student with the given last name
     */

    List<Student> findByLastnameContainingOrderByLastname(String lastName);

    /**
     * Finds students by the last name.
     *
     * @param lastname the last name of the student
     * @return the students with the given last name
     */

    List<Student> findByLastnameContainingIgnoreCase(String lastname);

    /**
     * Finds a student by the username.
     *
     * @param username the username of the student
     * @return the student with the given username
     */
    Optional<Student> findByUserUsername(String username);

    /**
     * Finds students by the classroom ID.
     *
     * @param classroomId the ID of the classroom
     * @return the students in the classroom with the given ID
     */
    @Query("SELECT new gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetUsernameAndIdDTO(s.id, s.user.username) " +
            "FROM Student s JOIN s.classrooms c WHERE c.id = :classroomId")
    List<StudentGetUsernameAndIdDTO> findStudentUsernamesAndIdsByClassroomId(Long classroomId);


}
