package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Student entity in the application.
 * Extends {@link AbstractEntity}.
 * It is used to store information about a student, such as the first name, last name, classrooms, teachers, and user associated with the student.
 * It is also used to define relationships with other entities, such as classrooms, teachers, and users.
 */

@Entity
@Table(name = "students")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A student entity.")
public class Student extends AbstractEntity {

    /**
     * The unique identifier of the student.
     * It is generated automatically when a new student is created.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the student.")
    private Long id;

    /**
     * The first name of the student.
     */

    @Schema(description = "The first name of the student.")
    private String firstname;

    /**
     * The last name of the student.
     */

    @Schema(description = "The last name of the student.")
    private String lastname;

    /**
     * The classrooms associated with this student.
     * It is a many-to-many relationship.
     * It is mapped by the "studentsOfClassroom" field in the {@link Classroom} entity.
     */

    @ManyToMany(mappedBy = "studentsOfClassroom")
    @JsonBackReference
    @Schema(description = "The classrooms associated with this student.")
    private List<Classroom> classrooms = new ArrayList<>();

    /**
     * The user associated with this student.
     * It is a one-to-one relationship.
     * It is cascaded to persist, update, and remove operations.
     */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    @Schema(description = "The user associated with this student.")
    private User user;

    /**
     * The teachers associated with this student.
     * It is a many-to-many relationship.
     * It is mapped by the "students" field in the {@link Teacher} entity.
     */

    @ManyToMany(mappedBy = "students")
    @JsonManagedReference
    @Schema(description = "The teachers associated with this student.")
    private List<Teacher> teachers = new ArrayList<>();

    /**
     * Constructs a new {@link Student} instance with the specified first name and last name.
     *
     * @param firstname the first name of the student
     * @param lastname  the last name of the student
     */
    public Student(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.setIsActive(true);
    }

    /**
     * Associates the given {@link User} with this student.
     *
     * @param user the user to associate with this student
     */
    public void addUser(User user) {
        this.user = user;
        user.setStudent(this);
    }

    /**
     * Sets the classroom for this student and ensures bidirectional relationship is maintained.
     *
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classrooms.add(classroom);
        if (classroom != null && !classroom.getStudentsOfClassroom().contains(this)) {
            classroom.getStudentsOfClassroom().add(this);
        }
    }

    /**
     * Returns a string representation of the student.
     *
     * @return a string representation of the student
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", user=" + user +
                '}';
    }
}
