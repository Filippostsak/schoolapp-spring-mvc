package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.message.*;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.SendNotificationDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.MessageMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.NotificationMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Message;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.MessagesRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.NotificationRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.MessageDontFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link IMessageService} interface.
 * Provides methods for managing messages.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

    private final MessagesRepository messageRepository;
    private final NotificationRepository notificationRepository;
    private final MessageMapper messageMapper;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    /**
     * Retrieves messages by the ID of the sender.
     *
     * @param senderId the ID of the sender
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */

    @Override
    public List<GetMessageDTO> getMessagesBySenderId(Long senderId) throws MessageDontFoundException {
        try {
            List<Message> messages = messageRepository.findBySenderId(senderId);
            return messageMapper.toGetMessageDTOList(messages);
        } catch (Exception e) {
            log.error("Error while fetching messages by sender id: {}", e.getMessage());
            throw new MessageDontFoundException("Error while fetching messages by sender id: " + e.getMessage());
        }
    }
    /**
     * Retrieves messages by the ID of the receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */

    @Override
    public List<GetMessageDTO> getMessagesByReceiverId(Long receiverId) {
        try{
            List<Message> messages = messageRepository.findByReceiverId(receiverId);
            log.info("Messages fetched successfully by receiver id: {}", receiverId);
            return messageMapper.toGetMessageDTOList(messages);
        } catch (Exception e) {
            log.error("Error while fetching messages by receiver id: {}", e.getMessage());
            throw new MessageDontFoundException("Error while fetching messages by receiver id: " + e.getMessage());
        }
    }

    /**
     * Retrieves messages by the IDs of both the sender and the receiver.
     *
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */

    @Override
    public List<GetMessageDTO> getMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId) throws MessageDontFoundException {
        try {
            List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
            return messageMapper.toGetMessageDTOList(messages);
        } catch (Exception e) {
            log.error("Error while fetching messages by sender and receiver id: {}", e.getMessage());
            throw new MessageDontFoundException("Error while fetching messages by sender and receiver id: " + e.getMessage());
        }
    }

    /**
     * Creates a new message.
     *
     * @param sendMessageDTO the message data
     * @return the created SendMessageDTO
     * @throws MessageDontFoundException if the message cannot be created
     */

    @Override
    @Transactional
    public SendMessageDTO createMessage(SendMessageDTO sendMessageDTO) throws MessageDontFoundException {
        try {
            Long senderId = resolveCurrentAuthenticatedUserId();
            sendMessageDTO.setSenderId(senderId);
            Message message = messageMapper.toMessage(sendMessageDTO);
            messageRepository.save(message);
            SendNotificationDTO sendNotificationDTO = notificationMapper.toSendNotificationDTO(message, "You have a new message");
            notificationRepository.save(notificationMapper.sendNotificationDTOToNotification(sendNotificationDTO));

            return sendMessageDTO;
        } catch (Exception e) {
            log.error("Error while creating message: {}", e.getMessage());
            throw new MessageDontFoundException("Error while creating message: " + e.getMessage());
        }
    }

    /**
     * Updates an existing message.
     *
     * @param updateMessageDTO the message data to update
     * @return the updated GetMessageDTO
     * @throws MessageDontFoundException if the message cannot be updated
     */

    @Override
    @Transactional
    public GetMessageDTO updateMessage(UpdateMessageDTO updateMessageDTO) throws MessageDontFoundException {
        try {
            Message message = messageRepository.findById(updateMessageDTO.getId())
                    .orElseThrow(() -> new MessageDontFoundException("Message not found"));
            message.setContent(updateMessageDTO.getContent());
            messageRepository.save(message);
            return messageMapper.toGetMessageDTO(message);
        } catch (Exception e) {
            log.error("Error while updating message: {}", e.getMessage());
            throw new MessageDontFoundException("Error while updating message: " + e.getMessage());
        }
    }

    /**
     * Deletes a message.
     *
     * @param deleteMessageDTO the message data to delete
     * @throws MessageDontFoundException if the message cannot be deleted
     */

    @Override
    @Transactional
    public void deleteMessage(DeleteMessageDTO deleteMessageDTO) throws MessageDontFoundException {
        try {
            Message message = messageRepository.findById(deleteMessageDTO.getId())
                    .orElseThrow(() -> new MessageDontFoundException("Message not found"));
            messageRepository.delete(message);
        } catch (Exception e) {
            log.error("Error while deleting message: {}", e.getMessage());
            throw new MessageDontFoundException("Error while deleting message: " + e.getMessage());
        }
    }

    /**
     * Retrieves the ID of the current authenticated teacher.
     *
     * @return the ID of the current authenticated teacher
     * @throws MessageDontFoundException if the teacher cannot be found
     */

    @Override
    public Long getCurrentTeacherId() throws MessageDontFoundException {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Teacher teacher = teacherRepository.findByUserUsername(username)
                    .orElseThrow(() -> new MessageDontFoundException("Teacher not found"));
            return teacher.getId();
        } catch (Exception e) {
            log.error("Error while fetching current teacher id: {}", e.getMessage());
            throw new MessageDontFoundException("Error while fetching current teacher id: " + e.getMessage());
        }
    }

    /**
     * Retrieves the ID of the current authenticated user.
     *
     * @return the ID of the current authenticated user
     * @throws MessageDontFoundException if the user cannot be found
     */

    // Helper method to resolve the current authenticated user's ID
    private Long resolveCurrentAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId();
    }

    /**
     * Retrieves a message by its creatorUsername.
     *
     * @param teacherUsername the ID of the message
     * @return the GetMessageDTO
     * @throws EntityNotFoundException if the message cannot be found
     */

    // Helper method to resolve the teacher's ID based on their username
    private Long resolveTeacherIdByUsername(String teacherUsername) {
        Optional<Teacher> teacherOptional = teacherRepository.findByUserUsername(teacherUsername);
        if (teacherOptional.isPresent()) {
            return teacherOptional.get().getId();
        } else {
            throw new IllegalArgumentException("Teacher not found with username: " + teacherUsername);
        }
    }

    /**
     * Retrieves messages by the ID of the sender.
     *
     * @param studentId the ID of the sender
     * @return a list of GetMessageDTO
     * @throws MessageDontFoundException if no messages are found
     */

    @Override
    public List<GetMessageDTO> getMessagesByStudentId(Long studentId) {
        try{
            List<Message> messages = messageRepository.findByReceiverId(studentId);
            log.info("Messages fetched successfully by student id: {}", studentId);
            return messageMapper.toGetMessageDTOList(messages);
        } catch (Exception e) {
            log.error("Error while fetching messages by student id: {}", e.getMessage());
            throw new MessageDontFoundException("Error while fetching messages by student id: " + e.getMessage());
        }
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param id the ID of the message
     * @return the retrieved GetMessageDTO
     * @throws EntityNotFoundException if the message is not found
     */

    @Override
    public GetMessageDTO getMessageById(Long id) throws EntityNotFoundException {
        try{
            Message message = messageRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Message not found"));
            return messageMapper.toGetMessageDTO(message);
        } catch (Exception e) {
            log.error("Error while fetching message by id: {}", e.getMessage());
            throw new EntityNotFoundException("Error while fetching message by id: " + e.getMessage());
        }
    }
}
