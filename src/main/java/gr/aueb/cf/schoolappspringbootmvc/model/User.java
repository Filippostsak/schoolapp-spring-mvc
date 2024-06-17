package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the application with fields for username, email, password, role, status,
 * personal information, and associated entities (teacher, student, admin).
 * Implements {@link UserDetails} for integration with Spring Security.
 */

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A user entity.")
public class User extends AbstractEntity implements UserDetails {

    /**
     * The unique identifier of the user.
     * It is generated automatically when a new user is created.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the user.")
    private Long id;

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

    @Column(nullable = false, unique = true)
    @Schema(description = "The username of the user.")
    private String username;

    /**
     * The email of the user.
     * It is unique and cannot be null.
     */

    @Column(nullable = false, unique = true)
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

    /**
     * The teacher associated with this user.
     * It is a one-to-one relationship.
     * It is mapped by the "user" field in the {@link Teacher} entity.
     */

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "The teacher associated with this user.")
    @JsonManagedReference
    private Teacher teacher;

    /**
     * The student associated with this user.
     * It is a one-to-one relationship.
     * It is mapped by the "user" field in the {@link Student} entity.
     */

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "The student associated with this user.")
    @JsonManagedReference
    private Student student;

    /**
     * The admin associated with this user.
     * It is a one-to-one relationship.
     * It is mapped by the "user" field in the {@link Admin} entity.
     */

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "The admin associated with this user.")
    @JsonManagedReference
    private Admin admin;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> receivedNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> sentNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages = new ArrayList<>();

    /**
     * The birthdate of the user.
     * It cannot be null.
     */

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The birthdate of the user.")
    private LocalDate birthDate;

    /**
     * The country of the user.
     * It cannot be null.
     */

    @Column(nullable = false)
    @Schema(description = "The country of the user.")
    private String country;

    /**
     * The city of the user.
     * It cannot be null.
     */

    @Column(nullable = false)
    @Schema(description = "The city of the user.")
    private String city;

    /**
     * Creates a new {@link User} instance with the role of {@link Role#TEACHER}.
     *
     * @param username  the username
     * @param email     the email
     * @param password  the password
     * @param birthDate the birthdate
     * @param country   the country
     * @param city      the city
     * @return a new {@link User} instance with the role of teacher
     */
    public static User NEW_TEACHER(String username, String email, String password, LocalDate birthDate, String country, String city) {
        User user = new User();
        user.setIsActive(true);
        user.setRole(Role.TEACHER);
        user.setStatus(Status.APPROVED);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);
        user.setCountry(country);
        user.setCity(city);
        return user;
    }

    /**
     * Creates a new {@link User} instance with the role of {@link Role#STUDENT}.
     *
     * @param username  the username
     * @param email     the email
     * @param password  the password
     * @param birthDate the birthdate
     * @param country   the country
     * @param city      the city
     * @return a new {@link User} instance with the role of student
     */
    public static User NEW_STUDENT(String username, String email, String password, LocalDate birthDate, String country, String city) {
        User user = new User();
        user.setIsActive(true);
        user.setRole(Role.STUDENT);
        user.setStatus(Status.APPROVED);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);
        user.setCountry(country);
        user.setCity(city);
        return user;
    }

    /**
     * Creates a new {@link User} instance with the role of {@link Role#ADMIN}.
     *
     * @param username  the username
     * @param email     the email
     * @param password  the password
     * @param birthDate the birthdate
     * @param country   the country
     * @param city      the city
     * @return a new {@link User} instance with the role of admin
     */

    public static User NEW_ADMIN(String username, String email, String password, LocalDate birthDate, String country, String city) {
        User user = new User();
        user.setIsActive(true);
        user.setRole(Role.ADMIN);
        user.setStatus(Status.APPROVED);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);
        user.setCountry(country);
        user.setCity(city);
        return user;
    }

    /**
     * Returns the authorities granted to the user. In this case, it is based on the user's role.
     *
     * @return a collection of granted authorities
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return {@code true} if the user's account is valid (non-expired), {@code false} if no longer valid (expired)
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return {@code true} if the user is not locked, {@code false} otherwise
     */

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired. Expired credentials prevent authentication.
     *
     * @return {@code true} if the user's credentials are valid (non-expired), {@code false} if no longer valid (expired)
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return {@code true} if the user is enabled, {@code false} otherwise
     */

    @Override
    public boolean isEnabled() {
        return this.getIsActive() == null || this.getIsActive();
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", birthDate=" + birthDate +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
