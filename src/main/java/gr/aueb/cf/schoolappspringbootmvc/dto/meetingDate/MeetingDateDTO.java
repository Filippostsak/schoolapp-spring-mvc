package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for a meeting date.
 * This class contains fields that are required for a meeting date,
 * along with the necessary validation annotations.
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for a meeting date.")
public class MeetingDateDTO {

    /**
     * The unique identifier of the meeting date.
     */
    @Schema(description = "The unique identifier of the meeting date.")
    private Long id;

    /**
     * The date of the meeting.
     * It must follow the format "yyyy-MM-dd".
     * It is a mandatory field.
     */

    @NotNull(message = "Date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The date of the meeting. It must follow the format 'yyyy-MM-dd'.")
    private LocalDate date;

    /**
     * The starting time of the meeting.
     * It must follow the format "HH:mm".
     * It is a mandatory field.
     */

    @NotNull(message = "Time cannot be null")
    @DateTimeFormat(pattern = "HH:mm")
    @Schema(description = "The starting time of the meeting. It must follow the format 'HH:mm'.")
    private LocalTime time;

    /**
     * The ending time of the meeting.
     * It must follow the format "HH:mm".
     * It is a mandatory field.
     */

    @NotNull(message = "End time cannot be null")
    @DateTimeFormat(pattern = "HH:mm")
    @Schema(description = "The ending time of the meeting. It must follow the format 'HH:mm'.")
    private LocalTime endTime;

    /**
     * The ending date of the meeting.
     * It must follow the format "yyyy-MM-dd".
     * It is a mandatory field.
     */

    @NotNull(message = "End date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The ending date of the meeting. It must follow the format 'yyyy-MM-dd'.")
    private LocalDate endDate;
}
