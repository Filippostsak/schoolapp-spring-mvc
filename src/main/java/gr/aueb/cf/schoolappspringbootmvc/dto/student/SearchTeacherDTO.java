package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for adding a student to a classroom.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for searching teachers.")
public class SearchTeacherDTO {

    /**
     * The unique identifier for the teacher.
     */

    @Schema(description = "The unique identifier for the teacher.")
    private Long id;

    /**
     * The first name of the teacher.
     */

    @Schema(description = "The first name of the teacher.")
    private String firstname;

    /**
     * The last name of the teacher.
     */

    @Schema(description = "The last name of the teacher.")
    private String lastname;
}
