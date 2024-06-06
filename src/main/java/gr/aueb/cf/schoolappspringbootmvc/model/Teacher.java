package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Teacher entity in the application.
 * Extends {@link AbstractEntity}.
 * It is used to store information about a teacher, such as the first name, last name, classrooms, students, and user associated with the teacher.
 * It is also used to define relationships with other entities, such as classrooms, students, and users.
 */

@Entity
@Table(name = "teachers")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A teacher entity.")
public class Teacher extends AbstractEntity {

    /**
     * The unique identifier of the teacher.
     * It is generated automatically when a new teacher is created.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the teacher.")
    private Long id;

    /**
     * The first name of the teacher.
     */

    @Schema(description = "The first name of the teacher.")
    private String firstname;

    /**
     * The last name of the teacher.
     */

    @Schema(description = "The last name of the teacher.")
    private String lastname;

    /**
     * The user associated with this teacher.
     * It is a one-to-one relationship.
     * It is cascaded to persist, update, and remove operations.
     */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    @Schema(description = "The user associated with this teacher.")
    private User user;

    /**
     * The classrooms managed by this teacher.
     * It is a many-to-many relationship.
     * It is mapped by the "teachers" field in the {@link Classroom} entity.
     */

    @ManyToMany(mappedBy = "teachers")
    @JsonBackReference
    @Schema(description = "The classrooms managed by this teacher.")
    private List<Classroom> classrooms = new ArrayList<>();

    /**
     * The extra classrooms managed by this teacher.
     * It is a many-to-many relationship.
     * It is mapped by the "extraTeachers" field in the {@link Classroom} entity.
     */

    @ManyToMany(mappedBy = "extraTeachers")
    @JsonBackReference
    @Schema(description = "The extra classrooms managed by this teacher.")
    private List<Classroom> extraClassrooms = new ArrayList<>();

    /**
     * The students associated with this teacher.
     * It is a many-to-many relationship.
     * It is mapped by the "students" field in the {@link Student} entity.
     */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_students",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    @Schema(description = "The students associated with this teacher.")
    private List<Student> students = new ArrayList<>();

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
        if (!classrooms.contains(classroom)) {
            classrooms.add(classroom);
            classroom.addTeacher(this);
        }
    }

    /**
     * Removes a classroom from the list of classrooms managed by this teacher.
     *
     * @param classroom the classroom to remove
     */
    public void removeClassroom(Classroom classroom) {
        if (classrooms.contains(classroom)) {
            classrooms.remove(classroom);
            classroom.removeTeacher(this);
        }
    }

    /**
     * Adds an extra classroom to the list of extra classrooms managed by this teacher.
     *
     * @param classroom the extra classroom to add
     */
    public void addExtraClassroom(Classroom classroom) {
        if (!extraClassrooms.contains(classroom)) {
            extraClassrooms.add(classroom);
            classroom.addExtraTeacher(this);
        }
    }

    /**
     * Removes an extra classroom from the list of extra classrooms managed by this teacher.
     *
     * @param classroom the extra classroom to remove
     */
    public void removeExtraClassroom(Classroom classroom) {
        if (extraClassrooms.contains(classroom)) {
            extraClassrooms.remove(classroom);
            classroom.removeExtraTeacher(this);
        }
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
                ", extraClassrooms=" + extraClassrooms +
                '}';
    }
}
