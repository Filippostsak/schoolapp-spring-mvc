package gr.aueb.cf.schoolappspringbootmvc.dto.notification;

import gr.aueb.cf.schoolappspringbootmvc.enums.Notify;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for retrieving a notification.
 * Contains information about the notification content, timestamp, sender, and receiver.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Data Transfer Object for retrieving a notification.")
public class GetNotificationDTO {

    /**
     * The ID of the notification.
     */

    @Schema(description = "The ID of the notification.")
    private Long id;

    /**
     * The content of the notification.
     */

    @Schema(description = "The content of the notification.")
    private String content;

    /**
     * The sender of the notification.
     */

    @Schema(description = "The sender of the notification.")
    private String senderUsername;

    /**
     * The timestamp when the notification was sent.
     */

    @Schema(description = "The timestamp when the notification was sent.")
    private LocalDateTime timestamp;

    /**
     * The sender of the notification.
     */

    @Schema(description = "The sender of the notification.")
    private Long senderId;

    /**
     * The receiver of the notification.
     */

    @Schema(description = "The receiver of the notification.")
    private Long receiverId;

    /**
     * The message ID of the notification.
     */

    @Schema(description = "The message ID of the notification.")
    private Long messageId;

    /**
     * The notification status.
     */

    @Schema(description = "The notification status.")
    private Notify notify = Notify.UNREAD;
}
