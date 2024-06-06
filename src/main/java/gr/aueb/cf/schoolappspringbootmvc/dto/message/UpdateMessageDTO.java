package gr.aueb.cf.schoolappspringbootmvc.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for updating a message.
 * Contains information about the message ID, content, sender, and receiver.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for updating a message.")
public class UpdateMessageDTO {

    /**
     * The ID of the message to be updated.
     * Must not be null.
     */
    @NotNull(message = "Message ID cannot be null")
    @Schema(description = "The ID of the message to be updated.")
    private Long id;

    /**
     * The updated content of the message.
     * Must not be blank.
     */
    @NotBlank(message = "Content cannot be blank")
    @Schema(description = "The updated content of the message.")
    private String content;

    /**
     * The ID of the sender.
     * Must not be null.
     */
    @NotNull(message = "Sender ID cannot be null")
    @Schema(description = "The ID of the sender.")
    private Long senderId;

    /**
     * The ID of the receiver.
     * Must not be null.
     */
    @NotNull(message = "Receiver ID cannot be null")
    @Schema(description = "The ID of the receiver.")
    private Long receiverId;
}
