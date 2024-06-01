package gr.aueb.cf.schoolappspringbootmvc.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentReadOnlyDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String country;
    private String city;
}
