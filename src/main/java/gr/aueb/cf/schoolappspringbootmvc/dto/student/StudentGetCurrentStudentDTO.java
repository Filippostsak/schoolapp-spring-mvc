package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for getting the current student.
 * Contains information about the student's ID and username.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentGetCurrentStudentDTO {

    /**
     * The ID of the student.
     */

    @NotNull
    private Long id;

    /**
     * The username of the student.
     */

    private String username;
}
