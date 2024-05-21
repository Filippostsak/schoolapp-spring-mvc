package gr.aueb.cf.schoolappspringbootmvc.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a teacher in the application with fields for first name, last name,
 * associated user, and list of classrooms. Extends {@link AbstractEntity}.
 */
@Entity
@Table(name = "teachers")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Classroom> classrooms = new ArrayList<>();

    /**
     * Constructs a new {@link Teacher} instance with the specified first name and last name.
     *
     * @param firstname the first name of the teacher
     * @param lastname  the last name of the teacher
     */
    public Teacher(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.setIsActive(true);
    }

    /**
     * Associates the given {@link User} with this teacher.
     *
     * @param user the user to associate with this teacher
     */
    public void addUser(User user) {
        this.user = user;
        user.setTeacher(this);
    }

    /**
     * Adds a classroom to the list of classrooms managed by this teacher.
     *
     * @param classroom the classroom to add
     */
    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
        classroom.setTeacher(this);
    }

    /**
     * Removes a classroom from the list of classrooms managed by this teacher.
     *
     * @param classroom the classroom to remove
     */
    public void removeClassroom(Classroom classroom) {
        classrooms.remove(classroom);
        classroom.setTeacher(null);
    }

    /**
     * Returns a string representation of the teacher.
     *
     * @return a string representation of the teacher
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", user=" + user +
                ", classrooms=" + classrooms +
                '}';
    }
}
