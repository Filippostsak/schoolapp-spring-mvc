package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTeacherToClassroomDTO {

    private Long classroomId;
    @NotNull
    private String teacherUsername;
}
