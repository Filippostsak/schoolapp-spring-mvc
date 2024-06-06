package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a Data Transfer Object (DTO) class for getting
 * the unique identifier of a teacher.
 * It is used to pass data between processes.
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A data transfer object (DTO) for getting the unique identifier of a teacher.")
public class GetTeachersIdDTO {

    /**
     * This is the unique identifier of the teacher.
     * It is a Long type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */
    @NotNull
    @Schema(description = "The unique identifier of the teacher. It is a mandatory field.")
    private Long id;

    /**
     * This is the first name of the teacher.
     * It is a String type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */

    @NotNull
    @Schema(description = "The first name of the teacher. It is a mandatory field.")
    private String firstname;

/**
     * This is the last name of the teacher.
     * It is a String type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */

    @NotNull
    @Schema(description = "The last name of the teacher. It is a mandatory field.")
    private String lastname;
}
