package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a Data Transfer Object (DTO) class for adding a teacher to a classroom.
 * It is used to pass data between processes.
 * It is annotated with @NoArgsConstructor, @AllArgsConstructor, @Getter and @Setter from the Lombok library to automatically generate constructors, getters and setters.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "A data transfer object (DTO) for adding a teacher to a classroom.")
public class AddTeacherToClassroomDTO {

    /**
     * This is the ID of the classroom to which a teacher is to be added.
     * It is a Long type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */

    @NotNull
    @Schema(description = "The unique identifier of the classroom. It is a mandatory field.")
    private Long classroomId;

    /**
     * This is the username of the teacher to be added to a classroom.
     * It is a String type.
     * It is annotated with @NotNull from the Jakarta Validation API to indicate that this field must not be null.
     */

    @NotNull
    @Schema(description = "The username of the teacher. It is a mandatory field.")
    private String teacherUsername;
}
