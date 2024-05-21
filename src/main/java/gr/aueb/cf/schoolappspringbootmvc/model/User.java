package gr.aueb.cf.schoolappspringbootmvc.model;

import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
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
public class User extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Transient
    private String confirmPassword;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Admin admin;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    /**
     * Creates a new {@link User} instance with the role of {@link Role#TEACHER}.
     *
     * @param username  the username
     * @param email     the email
     * @param password  the password
     * @param birthDate the birth date
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
     * @param birthDate the birth date
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
     * @param birthDate the birth date
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
