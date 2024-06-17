package gr.aueb.cf.schoolappspringbootmvc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an Admin entity in the application.
 * Extends {@link AbstractEntity}.
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "An admin entity.")
public class Admin extends AbstractEntity {

    /**
     * The unique identifier of the admin.
     * It is generated automatically when a new admin is created.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the admin.")
    private Long id;

    /**
     * The first name of the admin.
     */

    @Schema(description = "The first name of the admin.")
    private String firstname;

    /**
     * The last name of the admin.
     */

    @Schema(description = "The last name of the admin.")
    private String lastname;

    /**
     * The user associated with this admin.
     * It is a one-to-one relationship.
     * It is cascaded to persist, update, and remove operations.
     */

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Schema(description = "The user associated with this admin.")
    private User user;

    /**
     * Constructs an Admin with the specified first name and last name.
     *
     * @param firstname the first name of the admin
     * @param lastname  the last name of the admin
     */

    public Admin(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.setIsActive(true);
    }

    /**
     * Associates a user with this admin.
     *
     * @param user the user to associate with this admin
     */
    public void addUser(User user) {
        this.user = user;
        user.setAdmin(this);
    }

    /**
     * Returns a string representation of the admin.
     *
     * @return a string representation of the admin
     */
    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", user=" + user +
                '}';
    }
}
