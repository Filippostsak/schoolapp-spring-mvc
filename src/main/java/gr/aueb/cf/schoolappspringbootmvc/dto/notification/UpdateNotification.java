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
 * Data Transfer Object for updating a notification.
 * Contains information about the notification ID, content, and status.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Data Transfer Object for updating a notification.")
public class UpdateNotification {

    /**
     * The ID of the notification to be updated.
     * Must not be null.
     */
    @NotNull(message = "Notification ID cannot be null")
    @Schema(description = "The ID of the notification to be updated.")
    private Long id;

    /**
     * The updated content of the notification.
     * Must not be null and must be between 1 and 500 characters.
     */
    @NotNull(message = "Content must not be null")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    @Schema(description = "The updated content of the notification.")
    private String content;

    /**
     * The status of the notification.
     * Defaults to {@link Notify#READ}.
     */
    @Schema(description = "The status of the notification.")
    private Notify notify = Notify.READ;
}
