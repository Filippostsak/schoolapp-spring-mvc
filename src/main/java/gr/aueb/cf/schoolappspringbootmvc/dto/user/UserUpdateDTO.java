package gr.aueb.cf.schoolappspringbootmvc.dto.user;

import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {

    @Schema(description = "The ID of the user.")
    private Long id;

    @Schema(description = "The first name of the user.")
    private String firstname;

    @Schema(description = "The last name of the user.")
    private String lastname;

    @Schema(description = "The role of the user.")
    private Role role;

    @Schema(description = "The status of the user.")
    private Status status;

    @Schema(description = "Whether the user is active.")
    private Boolean active;

    @Schema(description = "The username of the user.")
    private String username;

    @Schema(description = "The email of the user.")
    private String email;
}
