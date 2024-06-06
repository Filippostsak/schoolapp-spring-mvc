package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.notification.*;
import gr.aueb.cf.schoolappspringbootmvc.service.INotificationService;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.NotificationNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling notification-related requests.
 * Provides endpoints for managing notifications.
 */

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationRestController {

    private final INotificationService notificationService;

    /**
     * Retrieves the list of notifications for a specific receiver by their ID.
     *
     * @param receiverId the ID of the receiver
     * @return a ResponseEntity containing the list of GetNotificationDTO
     */
    @Operation(summary = "Get notifications by receiver ID", description = "Retrieves the list of notifications for a specific receiver by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notifications",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetNotificationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notifications not found", content = @Content)
    })
    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<GetNotificationDTO>> getNotificationsByReceiverId(@PathVariable Long receiverId) {
        try {
            List<GetNotificationDTO> notifications = notificationService.getNotificationsByReceiverId(receiverId);
            return ResponseEntity.ok(notifications);
        } catch (NotificationNotFoundException e) {
            log.error("Error fetching notifications for receiver id: {}", receiverId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Creates a new notification.
     *
     * @param sendNotificationDTO the notification data
     * @return a ResponseEntity containing the created GetNotificationDTO
     */
    @Operation(summary = "Create a new notification", description = "Creates a new notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created notification",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetNotificationDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<GetNotificationDTO> createNotification(@RequestBody SendNotificationDTO sendNotificationDTO) {
        try {
            GetNotificationDTO notification = notificationService.createNotification(sendNotificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        } catch (NotificationNotFoundException e) {
            log.error("Error creating notification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Updates an existing notification.
     *
     * @param updateNotificationDTO the notification data to update
     * @return a ResponseEntity containing the updated GetNotificationDTO
     */
    @Operation(summary = "Update an existing notification", description = "Updates an existing notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated notification",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetNotificationDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<GetNotificationDTO> updateNotification(@RequestBody UpdateNotification updateNotificationDTO) {
        try {
            GetNotificationDTO updatedNotification = notificationService.updateNotification(updateNotificationDTO);
            return ResponseEntity.ok(updatedNotification);
        } catch (NotificationNotFoundException e) {
            log.error("Error updating notification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Deletes a notification.
     *
     * @param deleteNotificationDTO the notification data to delete
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Delete a notification", description = "Deletes a notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted notification", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteNotification(@RequestBody DeleteNotification deleteNotificationDTO) {
        try {
            notificationService.deleteNotification(deleteNotificationDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotificationNotFoundException e) {
            log.error("Error deleting notification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the ID of the notification to mark as read
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Mark a notification as read", description = "Marks a notification as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully marked notification as read", content = @Content),
            @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
    })
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
        try {
            notificationService.markNotificationAsRead(notificationId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotificationNotFoundException e) {
            log.error("Error marking notification as read: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Marks all notifications as read for a specific receiver.
     *
     * @param receiverId the ID of the receiver
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Mark all notifications as read", description = "Marks all notifications as read for a specific receiver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully marked all notifications as read", content = @Content),
            @ApiResponse(responseCode = "404", description = "Notifications not found", content = @Content)
    })
    @PutMapping("/read/all/{receiverId}")
    public ResponseEntity<Void> markAllNotificationsAsRead(@PathVariable Long receiverId) {
        try {
            notificationService.markAllNotificationsAsRead(receiverId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotificationNotFoundException e) {
            log.error("Error marking all notifications as read: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
