package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.message.*;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.MessageDontFoundException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing messages.
 * Provides methods for retrieving, creating, updating, and deleting messages.
 */
public interface IMessageService {

    /**
     * Retrieves messages by the ID of the sender.
     *
     * @param senderId the ID of the sender
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */
    List<GetMessageDTO> getMessagesBySenderId(Long senderId) throws MessageDontFoundException;

    /**
     * Retrieves messages by the ID of the receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */
    List<GetMessageDTO> getMessagesByReceiverId(Long receiverId) throws MessageDontFoundException;

    /**
     * Retrieves messages by the IDs of both the sender and the receiver.
     *
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */
    List<GetMessageDTO> getMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId) throws MessageDontFoundException;

    /**
     * Creates a new message.
     *
     * @param sendMessageDTO the message data
     * @return the created SendMessageDTO
     * @throws MessageDontFoundException if the message cannot be created
     */
    SendMessageDTO createMessage(SendMessageDTO sendMessageDTO) throws MessageDontFoundException;

    /**
     * Updates an existing message.
     *
     * @param updateMessageDTO the message data to update
     * @return the updated GetMessageDTO
     * @throws MessageDontFoundException if the message cannot be updated
     */
    GetMessageDTO updateMessage(UpdateMessageDTO updateMessageDTO) throws MessageDontFoundException;

    /**
     * Deletes a message.
     *
     * @param deleteMessageDTO the message data to delete
     * @throws MessageDontFoundException if the message cannot be deleted
     */
    void deleteMessage(DeleteMessageDTO deleteMessageDTO) throws MessageDontFoundException;

    /**
     * Retrieves the current teacher's ID.
     *
     * @return the ID of the current teacher
     * @throws MessageDontFoundException if the teacher's ID cannot be retrieved
     */
    Long getCurrentTeacherId() throws MessageDontFoundException;

    /**
     * Retrieves a message by its ID.
     *
     * @param id the ID of the message
     * @return the retrieved GetMessageDTO
     * @throws EntityNotFoundException if the message is not found
     */
    GetMessageDTO getMessageById(Long id) throws EntityNotFoundException;

    /**
     * Retrieves messages by the ID of the student.
     *
     * @param studentId the ID of the student
     * @return a list of GetMessageDTO
     */
    List<GetMessageDTO> getMessagesByStudentId(Long studentId);
}
