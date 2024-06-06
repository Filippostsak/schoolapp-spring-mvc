package gr.aueb.cf.schoolappspringbootmvc.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for retrieving a message.
 * Contains information about the message content, timestamp, sender, and receiver.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for retrieving a message.")
public class GetMessageDTO {

    /**
     * The ID of the message.
     */
    @Schema(description = "The ID of the message.")
    private Long id;

    /**
     * The content of the message.
     */
    @Schema(description = "The content of the message.")
    private String content;

    /**
     * The timestamp when the message was sent.
     */
    @Schema(description = "The timestamp when the message was sent.")
    private LocalDateTime timestamp;

    /**
     * The ID of the sender.
     */
    @Schema(description = "The ID of the sender.")
    private Long senderId;

    /**
     * The username of the sender.
     */
    @Schema(description = "The username of the sender.")
    private String senderUsername;

    /**
     * The ID of the receiver.
     */
    @Schema(description = "The ID of the receiver.")
    private Long receiverId;

    /**
     * The username of the receiver.
     */
    @Schema(description = "The username of the receiver.")
    private String receiverUsername;
}
