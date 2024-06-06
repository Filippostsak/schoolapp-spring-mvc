package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.notification.DeleteNotification;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.GetNotificationDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.SendNotificationDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.UpdateNotification;
import gr.aueb.cf.schoolappspringbootmvc.enums.Notify;
import gr.aueb.cf.schoolappspringbootmvc.mapper.NotificationMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Notification;
import gr.aueb.cf.schoolappspringbootmvc.repository.NotificationRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.NotificationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link INotificationService} interface.
 * Provides methods for retrieving, creating, updating, and deleting notifications.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    /**
     * Retrieves notifications by the ID of the receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a list of GetNotificationDTO
     * @throws NotificationNotFoundException if no notifications are found
     */

    @Override
    public List<GetNotificationDTO> getNotificationsByReceiverId(Long receiverId) throws NotificationNotFoundException {
        try {
            List<Notification> notifications = notificationRepository.findByReceiverId(receiverId);
            return notificationMapper.notificationsToGetNotificationDTOs(notifications);
        } catch (Exception e) {
            log.error("Error while fetching notifications by receiver id: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while fetching notifications by receiver id: " + e.getMessage());
        }
    }

    /**
     * Creates a new notification.
     *
     * @param sendNotificationDTO the notification data
     * @return the created GetNotificationDTO
     * @throws NotificationNotFoundException if the notification cannot be created
     */

    @Override
    public GetNotificationDTO createNotification(SendNotificationDTO sendNotificationDTO) throws NotificationNotFoundException {
        try {
            Notification notification = notificationMapper.sendNotificationDTOToNotification(sendNotificationDTO);
            notification.setNotify(Notify.UNREAD); // Ensure notifications are created as UNREAD
            notificationRepository.save(notification);
            return notificationMapper.notificationToGetNotificationDTO(notification);
        } catch (Exception e) {
            log.error("Error while creating notification: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while creating notification: " + e.getMessage());
        }
    }

    /**
     * Updates an existing notification.
     *
     * @param updateNotificationDTO the notification data to update
     * @return the updated GetNotificationDTO
     * @throws NotificationNotFoundException if the notification cannot be updated
     */

    @Override
    public GetNotificationDTO updateNotification(UpdateNotification updateNotificationDTO) throws NotificationNotFoundException {
        try {
            Notification notification = notificationRepository.findById(updateNotificationDTO.getId())
                    .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
            notification.setContent(updateNotificationDTO.getContent());
            notification.setNotify(updateNotificationDTO.getNotify());
            notificationRepository.save(notification);
            return notificationMapper.notificationToGetNotificationDTO(notification);
        } catch (Exception e) {
            log.error("Error while updating notification: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while updating notification: " + e.getMessage());
        }
    }

    /**
     * Deletes a notification.
     *
     * @param deleteNotificationDTO the notification data to delete
     * @throws NotificationNotFoundException if the notification cannot be deleted
     */

    @Override
    public void deleteNotification(DeleteNotification deleteNotificationDTO) throws NotificationNotFoundException {
        try {
            Notification notification = notificationRepository.findById(deleteNotificationDTO.getId())
                    .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
            notificationRepository.delete(notification);
        } catch (Exception e) {
            log.error("Error while deleting notification: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while deleting notification: " + e.getMessage());
        }
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the ID of the notification to mark as read
     * @throws NotificationNotFoundException if the notification cannot be found
     */

    @Override
    public void markNotificationAsRead(Long notificationId) throws NotificationNotFoundException {
        try {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
            notification.setNotify(Notify.READ); // Mark as READ
            notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Error while marking notification as read: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while marking notification as read: " + e.getMessage());
        }
    }

    /**
     * Marks a notification as unread.
     *
     * @param notificationId the ID of the notification to mark as unread
     * @throws NotificationNotFoundException if the notification cannot be found
     */

    @Override
    public void MarkNotificationAsUnread(Long notificationId) throws NotificationNotFoundException {
        try {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
            notification.setNotify(Notify.UNREAD); // Mark as UNREAD
            notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Error while marking notification as unread: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while marking notification as unread: " + e.getMessage());
        }
    }

    /**
     * Marks all notifications as read for a specific receiver.
     *
     * @param receiverId the ID of the receiver
     * @throws NotificationNotFoundException if the notifications cannot be found
     */

    @Override
    public void markAllNotificationsAsRead(Long receiverId) throws NotificationNotFoundException {
        try {
            List<Notification> notifications = notificationRepository.findByReceiverId(receiverId);
            notifications.forEach(notification -> notification.setNotify(Notify.READ));
            notificationRepository.saveAll(notifications);
        } catch (Exception e) {
            log.error("Error while marking all notifications as read: {}", e.getMessage());
            throw new NotificationNotFoundException("Error while marking all notifications as read: " + e.getMessage());
        }
    }
}
