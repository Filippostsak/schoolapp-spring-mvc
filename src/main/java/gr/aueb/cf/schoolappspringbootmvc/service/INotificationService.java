package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.notification.DeleteNotification;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.GetNotificationDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.SendNotificationDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.notification.UpdateNotification;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.NotificationNotFoundException;

import java.util.List;

/**
 * Service interface for managing notifications.
 * Provides methods for retrieving, creating, updating, and deleting notifications.
 */
public interface INotificationService {

    /**
     * Retrieves notifications by the ID of the receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a list of GetNotificationDTO
     * @throws NotificationNotFoundException if no notifications are found
     */
    List<GetNotificationDTO> getNotificationsByReceiverId(Long receiverId) throws NotificationNotFoundException;

    /**
     * Creates a new notification.
     *
     * @param sendNotificationDTO the notification data
     * @return the created GetNotificationDTO
     * @throws NotificationNotFoundException if the notification cannot be created
     */
    GetNotificationDTO createNotification(SendNotificationDTO sendNotificationDTO) throws NotificationNotFoundException;

    /**
     * Updates an existing notification.
     *
     * @param updateNotificationDTO the notification data to update
     * @return the updated GetNotificationDTO
     * @throws NotificationNotFoundException if the notification cannot be updated
     */
    GetNotificationDTO updateNotification(UpdateNotification updateNotificationDTO) throws NotificationNotFoundException;

    /**
     * Deletes a notification.
     *
     * @param deleteNotificationDTO the notification data to delete
     * @throws NotificationNotFoundException if the notification cannot be deleted
     */
    void deleteNotification(DeleteNotification deleteNotificationDTO) throws NotificationNotFoundException;

    /**
     * Marks a notification as read.
     *
     * @param notificationId the ID of the notification to mark as read
     * @throws NotificationNotFoundException if the notification cannot be found
     */
    void markNotificationAsRead(Long notificationId) throws NotificationNotFoundException;

    /**
     * Marks a notification as unread.
     *
     * @param notificationId the ID of the notification to mark as unread
     * @throws NotificationNotFoundException if the notification cannot be found
     */
    void MarkNotificationAsUnread(Long notificationId) throws NotificationNotFoundException;

    /**
     * Marks all notifications as read for a specific receiver.
     *
     * @param receiverId the ID of the receiver
     * @throws NotificationNotFoundException if the notifications cannot be found
     */
    void markAllNotificationsAsRead(Long receiverId) throws NotificationNotFoundException;
}
