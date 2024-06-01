package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchStudentToClassroomDTO {
    @NotNull
    private Long studentId;
    private String studentFirstname;
    private String studentLastname;
    private List<ClassroomDTO> classrooms;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ClassroomDTO {
        private Long id;
        private String name;
    }
}

