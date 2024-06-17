package gr.aueb.cf.schoolappspringbootmvc.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminUpdateDTO {

    @Schema(description = "The first name of the admin.")
    private String firstname;

    @Schema(description = "The last name of the admin.")
    private String lastname;

    @Size(max = 50, message = "Username cannot exceed 50 characters.")
    @Schema(description = "The username of the user.")
    private String username;

    @Email(message = "Email should be valid.")
    @Schema(description = "The email of the user.")
    private String email;

    @Schema(description = "The password of the user.")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The birthdate of the user.")
    private LocalDate birthDate;

    @Schema(description = "The country of the user.")
    private String country;

    @Schema(description = "The city of the user.")
    private String city;
}
