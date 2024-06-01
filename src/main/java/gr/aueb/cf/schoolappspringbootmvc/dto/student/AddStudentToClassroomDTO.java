package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddStudentToClassroomDTO {

    private Long studentId;

    private Long classroomId;
}
