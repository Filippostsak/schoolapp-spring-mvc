package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Classroom} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    /**
     * Checks if a classroom with the given name exists.
     *
     * @param name the name of the classroom
     * @return true if a classroom with the given name exists, false otherwise
     */

    boolean existsByName(String name);

    /**
     * Finds a classroom by the name.
     *
     * @param teacherId the name of the classroom
     * @return the classroom with the given name
     */

    Page<Classroom> findByTeachers_Id(Long teacherId, Pageable pageable);

    /**
     * Finds a classroom by the name.
     *
     * @param classroomId the name of the classroom
     * @return the classroom with the given name
     */

    Optional<Classroom> findById(Long classroomId);

    /**
     * Finds classrooms by the creator or teachers.
     *
     * @param creator the creator of the classroom
     * @param teacher the teacher of the classroom
     * @return the classrooms with the given creator or teacher
     */

    List<Classroom> findByCreatorOrTeachersContaining(Teacher creator, Teacher teacher);

    /**
     * Checks if a classroom with the given id and student exists.
     *
     * @param classroomId the id of the classroom
     * @param studentId the id of the student
     * @return true if a classroom with the given id and student exists, false otherwise
     */

    boolean existsByIdAndStudentsOfClassroom_Id(Long classroomId, Long studentId);

    /**
     * Finds classrooms by the students of the classroom.
     *
     * @param studentId the id of the student
     * @return the classrooms with the given student
     */

    @Query("SELECT c FROM Classroom c JOIN c.studentsOfClassroom s WHERE s.id = :studentId")
    List<Classroom> findClassroomsByStudentsOfClassroom_Id(@Param("studentId") Long studentId);


}
