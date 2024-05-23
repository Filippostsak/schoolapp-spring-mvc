package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link MeetingDate} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
public interface MeetingDateRepository extends JpaRepository<MeetingDate, Long> {
}