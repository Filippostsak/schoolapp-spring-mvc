package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a MeetingDate entity in the application.
 * Extends {@link AbstractEntity}.
 * It is used to store information about a meeting date, such as the date, time, and classroom associated with it.
 * It is also used to define relationships with other entities, such as classrooms.
 */

@Entity
@Table(name = "meeting_dates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A meeting date entity.")
public class MeetingDate extends AbstractEntity {

    /**
     * The unique identifier of the meeting date.
     * It is generated automatically when a new meeting date is created.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the meeting date.")
    private Long id;

    /**
     * The starting date of the meeting.
     */

    @Column(name = "starting_date")
    @Schema(description = "The starting date of the meeting.")
    private LocalDate date;

    /**
     * The starting time of the meeting.
     */

    @Column(name = "starting_time")
    @Schema(description = "The starting time of the meeting.")
    private LocalTime time;

    /**
     * The ending time of the meeting.
     */

    @Column(name = "ending_time")
    @Schema(description = "The ending time of the meeting.")
    private LocalTime endTime;

    /**
     * The ending date of the meeting.
     */

    @Column(name = "ending_date")
    @Schema(description = "The ending date of the meeting.")
    private LocalDate endDate;

    /**
     * The active status of the meeting date.
     * It is set to true by default.
     */

    @Column(name = "is_active", nullable = false)
    @Schema(description = "The active status of the meeting date.")
    private boolean isActive = true;

    /**
     * The classroom associated with this meeting date.
     * It is a many-to-one relationship.
     * It is fetched lazily.
     * It is also annotated with @JsonBackReference to prevent infinite recursion when serializing to JSON.
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    @JsonBackReference
    @Schema(description = "The classroom associated with this meeting date.")
    private Classroom classroom;

    /**
     *
     * @return a string representation of the meeting date
     *
     */

    @Override
    public String toString() {
        return "MeetingDate{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", endTime=" + endTime +
                ", endDate=" + endDate +
                ", classroom=" + classroom +
                ", isActive=" + isActive +
                '}';
    }
}
