package gr.aueb.cf.schoolappspringbootmvc.dto.user;

import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDTO {

    @Schema(description = "The unique identifier of the user.")
    private Long id;

    @Schema(description = "The first name of the user.")
    private String firstname;

    @Schema(description = "The last name of the user.")
    private String lastname;

    /**
     * The role of the user.
     * It can be {@link Role#TEACHER}, {@link Role#STUDENT}, or {@link Role#ADMIN}.
     */

    @Enumerated(EnumType.STRING)
    @Schema(description = "The role of the user.")
    private Role role;

    /**
     * The status of the user.
     * It can be {@link Status#PENDING}, {@link Status#APPROVED}, or {@link Status#REJECTED}.
     */

    @Enumerated(EnumType.STRING)
    @Schema(description = "The status of the user.")
    private Status status;

    /**
     * The username of the user.
     * It is unique and cannot be null.
     */

    @NotBlank
    @Schema(description = "The username of the user.")
    private String username;

    /**
     * The email of the user.
     * It is unique and cannot be null.
     */

    @NotBlank
    @Schema(description = "The email of the user.")
    private String email;

    /**
     * The password of the user.
     * It cannot be null.
     */

    @Schema(description = "The password of the user.")
    private String password;

    /**
     * The confirmation password of the user.
     * It is used to confirm the password.
     */

    @Transient
    @Schema(description = "The confirmation password of the user.")
    private String confirmPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The birthdate of the user.")
    private LocalDate birthDate;

    /**
     * The country of the user.
     * It cannot be null.
     */

    @Schema(description = "The country of the user.")
    private String country;

    /**
     * The city of the user.
     * It cannot be null.
     */

    @Schema(description = "The city of the user.")
    private String city;

    @Schema(description = "The address of the user.")
    private Boolean isActive;

    @Schema(description = "The date and time the user was created.")
    private LocalDateTime createdAt;
}
