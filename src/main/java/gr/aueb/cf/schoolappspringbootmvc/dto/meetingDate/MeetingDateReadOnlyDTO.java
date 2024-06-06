package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for reading meeting date information.
 * The fields are read-only and cannot be modified.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A read-only meeting date entity.")
public class MeetingDateReadOnlyDTO {

    /**
     * The unique identifier of the meeting date.
     */
    @Schema(description = "The unique identifier of the meeting date.")
    private Long id;
    /**
     * The date of the meeting.
     */
    @Schema(description = "The date of the meeting.")
    private LocalDate date;
    /**
     * The starting time of the meeting.
     */
    @Schema(description = "The starting time of the meeting.")
    private LocalTime time;
    /**
     * The ending date of the meeting.
     */
    @Schema(description = "The ending date of the meeting.")
    private LocalDate endDate;
    /**
     * The ending time of the meeting.
     */
    @Schema(description = "The ending time of the meeting.")
    private LocalTime endTime;
}
