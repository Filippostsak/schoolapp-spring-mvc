package gr.aueb.cf.schoolappspringbootmvc.dto.classroom;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.FindMeetingMeetingDateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for transferring classroom information along with associated meeting dates.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A classroom entity with associated meeting dates.")
public class ClassroomFindMeetingsDTO {

    /**
     * The unique identifier of the classroom.
     */

    @Schema(description = "The unique identifier of the classroom.")
    private Long id;

    /**
     * The name of the classroom.
     */

    @Schema(description = "The name of the classroom.")
    private String name;

    /**
     * A description of the classroom.
     */

    @Schema(description = "A description of the classroom.")
    private String description;

    /**
     * The URL of the classroom.
     */

    @Schema(description = "The URL of the classroom.")
    private String classroomUrl;

    /**
     * The image URL of the classroom.
     */

    @Schema(description = "The image URL of the classroom.")
    private String imageUrl;

    /**
     * A list of meeting dates associated with the classroom.
     */

    @Schema(description = "A list of meeting dates associated with the classroom.")
    private List<FindMeetingMeetingDateDTO> meetingDates;
}
