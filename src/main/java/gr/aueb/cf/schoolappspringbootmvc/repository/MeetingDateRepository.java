package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link MeetingDate} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface MeetingDateRepository extends JpaRepository<MeetingDate, Long> {

    /**
     * Finds a meeting date by the id.
     *
     * @param id the id of the meeting date
     * @return the meeting date with the given id
     */

    Optional<MeetingDate> findById(Long id);

    /**
     * Finds meeting dates by the classroom.
     *
     * @param classroom the classroom of the meeting date
     * @return the meeting dates with the given classroom
     */

    List<MeetingDate> findByClassroom(Classroom classroom);

}
