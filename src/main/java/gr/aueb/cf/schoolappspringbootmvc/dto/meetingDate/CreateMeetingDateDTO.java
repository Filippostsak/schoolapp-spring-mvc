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
 * Data Transfer Object (DTO) for creating a new meeting date.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for creating a new meeting date.")
public class CreateMeetingDateDTO {

    /**
     * The date of the meeting.
     * It is a mandatory field and must follow the format "yyyy-MM-dd".
     */
    @NotNull(message = "Date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The date of the meeting. It is a mandatory field and must follow the format 'yyyy-MM-dd'.")
    private LocalDate date;

    /**
     * The starting time of the meeting.
     * It is a mandatory field and must follow the format "HH:mm".
     */
    @NotNull(message = "Time cannot be null")
    @DateTimeFormat(pattern = "HH:mm")
    @Schema(description = "The starting time of the meeting. It is a mandatory field and must follow the format 'HH:mm'.")
    private LocalTime time;

    /**
     * The ending time of the meeting.
     * It is a mandatory field and must follow the format "HH:mm".
     */
    @NotNull(message = "End time cannot be null")
    @DateTimeFormat(pattern = "HH:mm")
    @Schema(description = "The ending time of the meeting. It is a mandatory field and must follow the format 'HH:mm'.")
    private LocalTime endTime;

    /**
     * The ending date of the meeting.
     * It is a mandatory field and must follow the format "yyyy-MM-dd".
     */
    @NotNull(message = "End date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The ending date of the meeting. It is a mandatory field and must follow the format 'yyyy-MM-dd'.")
    private LocalDate endDate;
}
