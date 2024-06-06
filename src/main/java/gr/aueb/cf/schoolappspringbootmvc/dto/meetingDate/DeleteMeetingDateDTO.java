package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for deleting a meeting date.
 * The id field is the unique identifier of the meeting date.
 * It is used to delete the meeting date from the database.
 * The id field is mandatory.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for deleting a meeting date.")
public class DeleteMeetingDateDTO {

    /**
     * The unique identifier of the meeting date.
     */

    @Schema(description = "The unique identifier of the meeting date.")
    private Long id;
}
