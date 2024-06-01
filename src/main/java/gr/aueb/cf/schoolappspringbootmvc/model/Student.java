package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "studentsOfClassroom")
    @JsonBackReference
    private List<Classroom> classrooms = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToMany(mappedBy = "students")
    @JsonManagedReference
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
