package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a meeting date with fields for starting and ending dates and times,
 * as well as an associated classroom. Extends {@link AbstractEntity}.
 */
@Entity
@Table(name = "meeting_dates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingDate extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starting_date")
    private LocalDate date;

    @Column(name = "starting_time")
    private LocalTime time;

    @Column(name = "ending_time")
    private LocalTime endTime;

    @Column(name = "ending_date")
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    @JsonBackReference
    private Classroom classroom;

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
