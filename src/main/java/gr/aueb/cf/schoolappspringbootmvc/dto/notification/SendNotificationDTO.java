package gr.aueb.cf.schoolappspringbootmvc.dto.notification;

import gr.aueb.cf.schoolappspringbootmvc.enums.Notify;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for sending a notification.
 * Contains information about the notification content, sender, receiver, and related message.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for sending a notification.")
public class SendNotificationDTO {

    /**
     * The ID of the notification.
     */
    @Schema(description = "The ID of the notification.")
    private Long id;

    /**
     * The content of the notification.
     * Must not be null and must be between 1 and 500 characters.
     */
    @NotNull(message = "Content must not be null")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    @Schema(description = "The content of the notification.")
    private String content;

    /**
     * The ID of the sender.
     * Must not be null.
     */
    @NotNull(message = "Sender ID must not be null")
    @Schema(description = "The ID of the sender.")
    private Long senderId;

    /**
     * The ID of the receiver.
     * Must not be null.
     */
    @NotNull(message = "Receiver ID must not be null")
    @Schema(description = "The ID of the receiver.")
    private Long receiverId;

    /**
     * The ID of the related message.
     * Must not be null.
     */
    @NotNull(message = "Message ID must not be null")
    @Schema(description = "The ID of the related message.")
    private Long messageId;

    /**
     * The notification status.
     * Defaults to {@link Notify#UNREAD}.
     */
    @Schema(description = "The notification status.")
    private Notify notify = Notify.UNREAD;
}
