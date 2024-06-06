package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * Data Transfer Object (DTO) for searching students.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for searching students.")
public class SearchStudentDTO {

    /**
     * The unique identifier for the student.
     */
    @Schema(description = "The unique identifier for the student.")
    private Long id;

    /**
     * The first name of the student.
     */
    @Schema(description = "The first name of the student.")
    private String firstname;

    /**
     * The last name of the student.
     */
    @Schema(description = "The last name of the student.")
    private String lastname;

    /**
     * A list of classroom IDs associated with the student.
     */
    @Schema(description = "A list of classroom IDs associated with the student.")
    private List<Long> classroomIds;

    /**
     * The email address of the student.
     */
    @Schema(description = "The email address of the student.")
    private String email;

    /**
     * The country of the student.
     */
    @Schema(description = "The country of the student.")
    private String country;
}
