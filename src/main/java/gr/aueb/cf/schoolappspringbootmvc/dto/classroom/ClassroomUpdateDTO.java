package gr.aueb.cf.schoolappspringbootmvc.dto.classroom;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for updating classroom information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A data transfer object (DTO) for updating classroom information.")
public class ClassroomUpdateDTO {

    /**
     * The unique identifier of the classroom.
     */

    @Schema(description = "The unique identifier of the classroom.")
    private Long id;

    /**
     * The name of the classroom.
     * It is a mandatory field and cannot exceed 100 characters.
     */

    @NotNull
    @NotBlank(message = "Classroom name is required.")
    @Size(max = 100, message = "Classroom name cannot exceed 100 characters.")
    @Schema(description = "The name of the classroom. It is a mandatory field and cannot exceed 100 characters.")
    private String name;

    /**
     * A description of the classroom.
     * It is a mandatory field and cannot exceed 500 characters.
     */
    @NotBlank(message = "Classroom description is required.")
    @Size(max = 500, message = "Classroom description cannot exceed 500 characters.")
    @Schema(description = "A description of the classroom. It is a mandatory field and cannot exceed 500 characters.")
    private String description;

    /**
     * The URL of the classroom.
     * This field is optional but cannot exceed 200 characters if provided.
     */
    @Size(max = 200, message = "Classroom URL cannot exceed 200 characters.")
    @Schema(description = "The URL of the classroom. It is an optional field but cannot exceed 200 characters.")
    private String classroomUrl;

    /**
     * The image URL of the classroom.
     * This field is optional but cannot exceed 200 characters if provided.
     */
    @Size(max = 200, message = "Image URL cannot exceed 200 characters.")
    @Schema(description = "The image URL of the classroom. It is an optional field but cannot exceed 200 characters.")
    private String imageUrl;

    /**
     * Indicates whether the classroom is active.
     * Default value is true.
     */

    @Schema(description = "Indicates whether the classroom is active. Default value is true.")
    private boolean isActive = true;
}
