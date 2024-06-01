package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchStudentDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private List<Long> classroomIds;
    private String email;
    private String country;
}
