package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for finding meeting dates.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for finding meeting dates.")
public class FindMeetingMeetingDateDTO {

    /**
     * The unique identifier for the meeting date.
     */
    @Schema(description = "The unique identifier for the meeting date.")
    private Long id;

    /**
     * The date of the meeting.
     * It must follow the format "yyyy-MM-dd".
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The date of the meeting. It must follow the format 'yyyy-MM-dd'.")
    private LocalDate date;

    /**
     * The starting time of the meeting.
     * It must follow the format "HH:mm".
     */
    @DateTimeFormat(pattern = "HH:mm")
    @Schema(description = "The starting time of the meeting. It must follow the format 'HH:mm'.")
    private LocalTime time;

    /**
     * The ending time of the meeting.
     * It must follow the format "HH:mm".
     */
    @DateTimeFormat(pattern = "HH:mm")
    @Schema(description = "The ending time of the meeting. It must follow the format 'HH:mm'.")
    private LocalTime endTime;

    /**
     * The ending date of the meeting.
     * It must follow the format "yyyy-MM-dd".
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The ending date of the meeting. It must follow the format 'yyyy-MM-dd'.")
    private LocalDate endDate;
}
