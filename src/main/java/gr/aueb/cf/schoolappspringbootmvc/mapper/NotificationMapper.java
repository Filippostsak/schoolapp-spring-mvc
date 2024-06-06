package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.notification.*;
import gr.aueb.cf.schoolappspringbootmvc.enums.Notify;
import gr.aueb.cf.schoolappspringbootmvc.model.Message;
import gr.aueb.cf.schoolappspringbootmvc.model.Notification;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.MessagesRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the Notification entity.
 * Contains methods for converting a Notification entity to a DTO and vice versa.
 */

@Component
public class NotificationMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessagesRepository messageRepository;

    /**
     * Converts a Notification entity to a GetNotificationDTO.
     *
     * @param notification the Notification entity to convert
     * @return the converted GetNotificationDTO
     */
    public GetNotificationDTO toGetNotificationDTO(Notification notification) {
        GetNotificationDTO getNotificationDTO = new GetNotificationDTO();
        getNotificationDTO.setId(notification.getId());
        getNotificationDTO.setReceiverId(notification.getReceiver().getId());
        getNotificationDTO.setSenderId(notification.getSender().getId());
        getNotificationDTO.setSenderUsername(notification.getSender().getUsername());
        getNotificationDTO.setMessageId(notification.getMessage().getId());
        getNotificationDTO.setContent(notification.getContent());
        getNotificationDTO.setTimestamp(notification.getTimestamp());
        getNotificationDTO.setNotify(notification.getNotify());
        return getNotificationDTO;
    }

    /**
     * Converts a list of Notification entities to a list of GetNotificationDTOs.
     *
     * @param notifications the list of Notification entities to convert
     * @return the converted list of GetNotificationDTOs
     */
    public List<GetNotificationDTO> notificationsToGetNotificationDTOs(List<Notification> notifications) {
        return notifications.stream()
                .map(this::toGetNotificationDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a SendNotificationDTO to a Notification entity.
     *
     * @param sendNotificationDTO the SendNotificationDTO to convert
     * @return the converted Notification entity
     */
    public Notification sendNotificationDTOToNotification(SendNotificationDTO sendNotificationDTO) {
        Notification notification = new Notification();
        notification.setContent(sendNotificationDTO.getContent());
        notification.setTimestamp(LocalDateTime.now());

        User sender = userRepository.findById(sendNotificationDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(sendNotificationDTO.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        Message message = messageRepository.findById(sendNotificationDTO.getMessageId())
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setMessage(message);
        notification.setNotify(Notify.UNREAD);
        return notification;
    }

    /**
     * Converts a Notification entity to a GetNotificationDTO.
     *
     * @param notification the Notification entity to convert
     * @return the converted GetNotificationDTO
     */
    public GetNotificationDTO notificationToGetNotificationDTO(Notification notification) {
        GetNotificationDTO getNotificationDTO = new GetNotificationDTO();
        getNotificationDTO.setId(notification.getId());
        getNotificationDTO.setReceiverId(notification.getReceiver().getId());
        getNotificationDTO.setSenderId(notification.getSender().getId());
        getNotificationDTO.setSenderUsername(notification.getSender().getUsername());
        getNotificationDTO.setMessageId(notification.getMessage().getId());
        getNotificationDTO.setContent(notification.getContent());
        getNotificationDTO.setTimestamp(notification.getTimestamp());
        getNotificationDTO.setNotify(notification.getNotify());
        return getNotificationDTO;
    }

    /**
     * Converts a Message entity and content to a SendNotificationDTO.
     *
     * @param message the Message entity related to the notification
     * @param content the content of the notification
     * @return the converted SendNotificationDTO
     */
    public SendNotificationDTO toSendNotificationDTO(Message message, String content) {
        SendNotificationDTO sendNotificationDTO = new SendNotificationDTO();
        sendNotificationDTO.setContent(content);
        sendNotificationDTO.setSenderId(message.getSender().getId());
        sendNotificationDTO.setReceiverId(message.getReceiver().getId());
        sendNotificationDTO.setMessageId(message.getId());
        sendNotificationDTO.setNotify(Notify.UNREAD);
        return sendNotificationDTO;
    }
}
