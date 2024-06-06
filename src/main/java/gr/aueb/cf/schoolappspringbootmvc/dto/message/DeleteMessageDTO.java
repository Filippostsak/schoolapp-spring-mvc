package gr.aueb.cf.schoolappspringbootmvc.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for deleting a message.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for deleting a message.")
public class DeleteMessageDTO {

    /**
     * The ID of the message to be deleted.
     * Must not be null.
     */
    @NotNull(message = "Message ID cannot be null")
    @Schema(description = "The ID of the message to be deleted.")
    private Long id;
}
