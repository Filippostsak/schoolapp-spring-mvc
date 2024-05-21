package gr.aueb.cf.schoolappspringbootmvc.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a classroom with a teacher, students, and meeting dates.
 * Extends {@link AbstractEntity}.
 */
@Entity
@Table(name = "classrooms")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Classroom extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingDate> meetingDates = new ArrayList<>();

    /**
     * Adds a teacher to the classroom and updates the teacher's list of classrooms.
     *
     * @param teacher the teacher to add
     */
    public void addTeacher(Teacher teacher) {
        this.teacher = teacher;
        if (teacher.getClassrooms() == null) {
            teacher.setClassrooms(new ArrayList<>());
        }
        teacher.getClassrooms().add(this);
    }

    /**
     * Removes the teacher from the classroom and updates the teacher's list of classrooms.
     */
    public void removeTeacher() {
        if (this.teacher != null) {
            this.teacher.getClassrooms().remove(this);
            this.teacher = null;
        }
    }

    /**
     * Adds a student to the classroom and sets the student's classroom.
     *
     * @param student the student to add
     */
    public void addStudent(Student student) {
        students.add(student);
        student.setClassroom(this);
    }

    /**
     * Removes a student from the classroom and sets the student's classroom to null.
     *
     * @param student the student to remove
     */
    public void removeStudent(Student student) {
        students.remove(student);
        student.setClassroom(null);
    }

    /**
     * Adds a meeting date to the classroom and sets the meeting date's classroom.
     *
     * @param meetingDate the meeting date to add
     */
    public void addMeetingDate(MeetingDate meetingDate) {
        meetingDates.add(meetingDate);
        meetingDate.setClassroom(this);
    }

    /**
     * Removes a meeting date from the classroom and sets the meeting date's classroom to null.
     *
     * @param meetingDate the meeting date to remove
     */
    public void removeMeetingDate(MeetingDate meetingDate) {
        meetingDates.remove(meetingDate);
        meetingDate.setClassroom(null);
    }

    /**
     * Returns a string representation of the classroom.
     *
     * @return a string representation of the classroom
     */
    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                ", meetingDates=" + meetingDates +
                '}';
    }
}
