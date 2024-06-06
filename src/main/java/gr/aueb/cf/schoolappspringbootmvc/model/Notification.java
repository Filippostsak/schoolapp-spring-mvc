package gr.aueb.cf.schoolappspringbootmvc.model;

import gr.aueb.cf.schoolappspringbootmvc.enums.Notify;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a Notification.
 * Contains information about the notification content, timestamp, sender, receiver, and related message.
 */

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A notification entity.")
public class Notification {

    /**
     * The unique ID of the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the notification.")
    private Long id;

    /**
     * The user who receives the notification.
     * Must not be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    @Schema(description = "The user who receives the notification.")
    private User receiver;

    /**
     * The message related to the notification.
     * Must not be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    @Schema(description = "The message related to the notification.")
    private Message message;

    /**
     * The user who sent the notification.
     * Must not be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @Schema(description = "The user who sent the notification.")
    private User sender;

    /**
     * The status of the notification.
     * Defaults to {@link Notify#UNREAD}.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "The status of the notification.")
    private Notify notify = Notify.UNREAD;

    /**
     * The content of the notification.
     * Must not be null.
     */
    @Column(nullable = false)
    @Schema(description = "The content of the notification.")
    private String content;

    /**
     * The timestamp when the notification was created.
     * Must not be null.
     */
    @Column(nullable = false)
    @Schema(description = "The timestamp when the notification was created.")
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "Notification{" +
                "message=" + message +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
