package gr.aueb.cf.schoolappspringbootmvc.model;

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
public class Admin extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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
