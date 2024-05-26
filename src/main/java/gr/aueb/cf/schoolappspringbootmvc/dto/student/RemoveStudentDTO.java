package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveStudentDTO {
    @NotNull
    private Long classroomId;
    @NotNull
    private Long studentId;
}
