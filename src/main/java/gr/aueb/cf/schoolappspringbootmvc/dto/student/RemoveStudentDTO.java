package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a Data Transfer Object (DTO) class for removing a student from a classroom.
 * It is used to pass data between processes.
 * It is annotated with @NoArgsConstructor, @AllArgsConstructor, @Getter and @Setter from the Lombok library to automatically generate constructors, getters and setters.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A data transfer object (DTO) for removing a student from a classroom.")
public class RemoveStudentDTO {

    /**
     * This is the ID of the classroom from which a student is to be removed.
     * It is a Long type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */
    @NotNull
    @Schema(description = "The unique identifier of the classroom. It is a mandatory field.")
    private Long classroomId;

    /**
     * This is the ID of the student to be removed from a classroom.
     * It is a Long type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */
    @NotNull
    @Schema(description = "The unique identifier of the student. It is a mandatory field.")
    private Long studentId;
}