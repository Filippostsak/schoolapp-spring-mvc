package gr.aueb.cf.schoolappspringbootmvc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a Message.
 * Contains information about the message content, timestamp, sender, receiver, and associated notifications.
 */

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A message entity.")
public class Message {

    /**
     * The unique ID of the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the message.")
    private Long id;

    /**
     * The content of the message.
     * Must not be null.
     */
    @Column(nullable = false)
    @Schema(description = "The content of the message.")
    private String content;

    /**
     * The timestamp when the message was sent.
     * Must not be null.
     */
    @Column(nullable = false)
    @Schema(description = "The timestamp when the message was sent.")
    private LocalDateTime timestamp;

    /**
     * The user who sent the message.
     * Must not be null.
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "The user who sent the message.")
    private User sender;

    /**
     * The user who received the message.
     * Must not be null.
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "The user who received the message.")
    private User receiver;

    /**
     * The list of notifications associated with the message.
     */
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    @Schema(description = "The list of notifications associated with the message.")
    private List<Notification> notifications;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
