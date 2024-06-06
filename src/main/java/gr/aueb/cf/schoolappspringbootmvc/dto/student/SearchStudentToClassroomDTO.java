package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for searching students to classrooms.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A data transfer object (DTO) for searching students to classrooms.")
public class SearchStudentToClassroomDTO {

    /**
     * The unique identifier for the student.
     */
    @NotNull
    @Schema(description = "The unique identifier for the student. It is a mandatory field.")
    private Long studentId;

    /**
     * The first name of the student.
     */
    @Schema(description = "The first name of the student.")
    private String studentFirstname;

    /**
     * The last name of the student.
     */
    @Schema(description = "The last name of the student.")
    private String studentLastname;

    /**
     * A list of classrooms associated with the student.
     */
    @Schema(description = "A list of classrooms associated with the student.")
    private List<ClassroomDTO> classrooms;

    /**
     * Data Transfer Object (DTO) for classroom details.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Schema(description = "A data transfer object (DTO) for classroom details.")
    public static class ClassroomDTO {
        /**
         * The unique identifier for the classroom.
         */
        @Schema(description = "The unique identifier for the classroom.")
        private Long id;

        /**
         * The name of the classroom.
         */
        @Schema(description = "The name of the classroom.")
        private String name;
    }
}
