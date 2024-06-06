package gr.aueb.cf.schoolappspringbootmvc.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for sending a message.
 * Contains information about the message content, sender, and receiver.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for sending a message.")
public class SendMessageDTO {

    /**
     * The content of the message.
     * Must not be null and must be between 1 and 500 characters.
     */
    @NotNull(message = "Content must not be null")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    @Schema(description = "The content of the message.")
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

}
