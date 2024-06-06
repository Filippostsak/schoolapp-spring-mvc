package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for reading student information.
 * The fields are read-only and cannot be modified.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A read-only student entity.")
public class StudentReadOnlyDTO {

    /**
     * The unique identifier of the student.
     */

    @Schema(description = "The unique identifier of the student.")
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
     * The email address of the student.
     */

    @Schema(description = "The email address of the student.")
    private String email;

    /**
     * The country of the student.
     */

    @Schema(description = "The country of the student.")
    private String country;

    /**
     * The city of the student.
     */

    @Schema(description = "The city of the student.")
    private String city;

    public StudentReadOnlyDTO(Long id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
