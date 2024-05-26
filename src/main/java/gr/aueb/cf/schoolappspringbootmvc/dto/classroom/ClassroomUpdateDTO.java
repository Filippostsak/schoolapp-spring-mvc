package gr.aueb.cf.schoolappspringbootmvc.dto.classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomUpdateDTO {
    private Long id;
    @NotNull
    @NotBlank(message = "Classroom name is required.")
    @Size(max = 100, message = "Classroom name cannot exceed 100 characters.")
    private String name;

    @NotBlank(message = "Classroom description is required.")
    @Size(max = 500, message = "Classroom description cannot exceed 500 characters.")
    private String description;

    @Size(max = 200, message = "Classroom URL cannot exceed 200 characters.")
    private String classroomUrl;

    @Size(max = 200, message = "Image URL cannot exceed 200 characters.")
    private String imageUrl;

    private boolean isActive = true;
}
