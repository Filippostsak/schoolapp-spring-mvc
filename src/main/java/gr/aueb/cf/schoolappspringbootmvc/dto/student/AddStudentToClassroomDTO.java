package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a Data Transfer Object (DTO) class for adding a student to a classroom.
 * It is used to pass data between processes.
 * It is annotated with @NoArgsConstructor, @AllArgsConstructor, @Getter and @Setter from the Lombok library to automatically generate constructors, getters and setters.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for adding a student to a classroom.")
public class AddStudentToClassroomDTO {

    /**
     * This is the ID of the student to be added to a classroom.
     * It is a Long type.
     */

    @Schema(description = "The unique identifier of the student.")
    private Long studentId;

    /**
     * This is the ID of the classroom to which a student is to be added.
     * It is a Long type.
     */

    @Schema(description = "The unique identifier of the classroom.")
    private Long classroomId;
}