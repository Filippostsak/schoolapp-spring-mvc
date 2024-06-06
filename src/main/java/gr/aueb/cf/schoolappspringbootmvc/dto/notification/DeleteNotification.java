package gr.aueb.cf.schoolappspringbootmvc.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for deleting a notification.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Data Transfer Object for deleting a notification.")
public class DeleteNotification {

    /**
     * The ID of the notification to be deleted.
     * Must not be null.
     */

    @NotNull(message = "Notification ID cannot be null")
    @Schema(description = "The ID of the notification to be deleted.")
    private Long id;
}
