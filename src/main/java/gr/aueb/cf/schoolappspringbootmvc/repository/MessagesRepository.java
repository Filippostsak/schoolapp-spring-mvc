package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing Message entities.
 * Provides methods for querying messages by sender, receiver, and specific criteria.
 */

public interface MessagesRepository extends JpaRepository<Message, Long> {

    /**
     * Finds messages by the ID of the sender.
     *
     * @param senderId the ID of the sender
     * @return a list of messages sent by the specified sender
     */
    List<Message> findBySenderId(Long senderId);

    /**
     * Finds messages by the ID of the receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a list of messages received by the specified receiver
     */
    List<Message> findByReceiverId(Long receiverId);

    /**
     * Finds messages by the IDs of both the sender and the receiver.
     *
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @return a list of messages between the specified sender and receiver
     */
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    /**
     * Finds messages by the ID of the student who is the receiver.
     *
     * @param studentId the ID of the student
     * @return a list of messages received by the specified student
     */
    @Query("SELECT m FROM Message m WHERE m.receiver.id = :studentId")
    List<Message> findMessagesByStudentId(@Param("studentId") Long studentId);

}
