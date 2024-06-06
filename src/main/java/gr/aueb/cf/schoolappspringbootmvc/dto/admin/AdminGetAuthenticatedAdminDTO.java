package gr.aueb.cf.schoolappspringbootmvc.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminGetAuthenticatedAdminDTO {

    @NotNull
    private Long id;

    private String username;
}
