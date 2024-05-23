package gr.aueb.cf.schoolappspringbootmvc.dto.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for registering a teacher.
 * This class contains fields that are required for registering a teacher,
 * along with the necessary validation annotations.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterTeacherDTO {

    /**
     * The username of the teacher.
     * It must be between 3 and 32 characters long and cannot be null.
     */
    @NotNull
    @Size(min = 3, max = 32)
    private String username;

    /**
     * The email address of the teacher.
     * It must be a valid email format and cannot be null.
     */
    @NotNull
    @Email
    private String email;

    /**
     * The password of the teacher.
     * It must be between 5 and 32 characters long, include at least one uppercase letter,
     * one lowercase letter, one number, and one special character. It cannot be null.
     */
    @NotNull
    @Size(min = 5, max = 32)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{5,32}$",
            message = "Password must be between 5 and 32 characters, include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;

    /**
     * The password confirmation field.
     * It must be between 5 and 32 characters long and cannot be null.
     */
    @NotNull
    @Size(min = 5, max = 32)
    private String confirmPassword;

    /**
     * The first name of the teacher.
     * It must be between 3 and 32 characters long and cannot be null.
     */
    @NotNull
    @Size(min = 3, max = 32)
    private String firstname;

    /**
     * The last name of the teacher.
     * It must be between 3 and 32 characters long and cannot be null.
     */
    @NotNull
    @Size(min = 3, max = 32)
    private String lastname;

    /**
     * The birth date of the teacher.
     * It must be in the format "yyyy-MM-dd" and cannot be null.
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * The country of the teacher.
     * It cannot be null.
     */
    @NotNull
    private String country;

    /**
     * The city of the teacher.
     * It cannot be null.
     */
    @NotNull
    private String city;
}
