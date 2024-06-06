package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for reading teacher information.
 * The fields are read-only and cannot be modified.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A read-only teacher entity.")
public class TeacherReadOnlyDTO {

    /**
     * The unique identifier of the teacher.
     */

    @Schema(description = "The unique identifier of the teacher.")
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

    /**
     * The email address of the teacher.
     */

    @Schema(description = "The email address of the teacher.")
    private String email;

    /**
     * The country of the teacher.
     */

    @Schema(description = "The country of the teacher.")
    private String username;
}
