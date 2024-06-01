package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherReadOnlyDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
}
