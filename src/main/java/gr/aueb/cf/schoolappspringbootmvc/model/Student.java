package gr.aueb.cf.schoolappspringbootmvc.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a student in the application with fields for first name, last name,
 * associated user, and classroom. Extends {@link AbstractEntity}.
 */
@Entity
@Table(name = "students")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    private Classroom classroom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
        this.classroom = classroom;
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
                ", classroom=" + classroom +
                '}';
    }
}
