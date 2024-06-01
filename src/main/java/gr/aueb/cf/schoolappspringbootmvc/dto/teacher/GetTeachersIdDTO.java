package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTeachersIdDTO {

    private Long id;

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
}
