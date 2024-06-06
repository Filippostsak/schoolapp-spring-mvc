package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Notification entities.
 * Provides methods for querying notifications by receiver ID.
 */

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Finds notifications by the ID of the receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a list of notifications received by the specified receiver
     */
    List<Notification> findByReceiverId(Long receiverId);
}
