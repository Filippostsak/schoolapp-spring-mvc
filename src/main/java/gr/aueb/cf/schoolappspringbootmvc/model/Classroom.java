package gr.aueb.cf.schoolappspringbootmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Classroom entity in the application.
 * Extends {@link AbstractEntity}.
 * It is used to store information about a classroom, such as its name, description, teachers, students, and meeting dates.
 * It is also used to define relationships with other entities, such as teachers, students, and meeting dates.
 *
 */

@Entity
@Table(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"creator", "teachers", "studentsOfClassroom", "meetingDates", "extraTeachers"})
@Schema(description = "A classroom entity.")
public class Classroom extends AbstractEntity {

    /**
     * The unique identifier of the classroom.
     * It is generated automatically when a new classroom is created.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the classroom.")
    private Long id;

    /**
     * The name of the classroom.
     * It is a required field and must be unique.
     */

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the classroom.")
    private String name;

    /**
     * The description of the classroom.
     * It is a required field.
     */

    @Column(nullable = false)
    @Schema(description = "The description of the classroom.")
    private String description;

    /**
     * The URL of the classroom.
     */

    @Column(name = "classroom_url")
    @Schema(description = "The URL of the classroom.")
    private String classroomUrl;

    /**
     * The image URL of the classroom.
     */

    @Column(name = "image_url")
    @Schema(description = "The image URL of the classroom.")
    private String imageUrl;

    /**
     * The active status of the classroom.
     * It is set to true by default.
     */

    @Column(name = "is_active", nullable = false)
    @Schema(description = "The active status of the classroom.")
    private boolean isActive = true;

    /**
     * The teacher who created the classroom.
     * It is a many-to-one relationship.
     * It is mapped by the "creator_id" column in the "classrooms" table.
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @JsonBackReference
    @Schema(description = "The teacher who created the classroom.")
    private Teacher creator;

    /**
     * The list of teachers who are assigned to the classroom.
     * It is a many-to-many relationship.
     * It is mapped by the "classroom_teachers" join table.
     */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "classroom_teachers",
            joinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    @Schema(description = "The list of teachers who are assigned to the classroom.")
    private List<Teacher> teachers = new ArrayList<>();

    /**
     * The list of students who are assigned to the classroom.
     * It is a many-to-many relationship.
     * It is mapped by the "classroom_students" join table.
     */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "classroom_students",
            joinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    @Schema(description = "The list of students who are assigned to the classroom.")
    private List<Student> studentsOfClassroom = new ArrayList<>();

    /**
     * The list of meeting dates for the classroom.
     * It is a one-to-many relationship.
     * It is mapped by the "classroom" field in the "meeting_dates" table.
     */

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "The list of meeting dates for the classroom.")
    private List<MeetingDate> meetingDates = new ArrayList<>();

    /**
     * The list of extra teachers who are assigned to the classroom.
     * It is a many-to-many relationship.
     * It is mapped by the "extra_classroom_teachers" join table.
     */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "extra_classroom_teachers",
            joinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    @Schema(description = "The list of extra teachers who are assigned to the classroom.")
    private List<Teacher> extraTeachers = new ArrayList<>();

   /**
     * Adds a teacher to the classroom.
     *
     * @param teacher the teacher to add
     */

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            teacher.getClassrooms().add(this);
        }
    }

    /**
     * Removes a teacher from the classroom.
     *
     * @param teacher the teacher to remove
     */

    public void removeTeacher(Teacher teacher) {
        if (teachers.contains(teacher)) {
            teachers.remove(teacher);
            teacher.getClassrooms().remove(this);
        }
    }

    /**
     * Adds an extra teacher to the classroom.
     *
     * @param teacher the extra teacher to add
     */

    public void addExtraTeacher(Teacher teacher) {
        if (!extraTeachers.contains(teacher)) {
            extraTeachers.add(teacher);
            teacher.getExtraClassrooms().add(this);
        }
    }

    /**
     * Removes an extra teacher from the classroom.
     *
     * @param teacher the extra teacher to remove
     */

    public void removeExtraTeacher(Teacher teacher) {
        if (extraTeachers.contains(teacher)) {
            extraTeachers.remove(teacher);
            teacher.getExtraClassrooms().remove(this);
        }
    }

    /**
     * Adds a student to the classroom.
     *
     * @param student the student to add
     */

    public void addStudent(Student student) {
        studentsOfClassroom.add(student);
        student.getClassrooms().add(this);
    }

    /**
     * Removes a student from the classroom.
     *
     * @param student the student to remove
     */

    public void removeStudent(Student student) {
        studentsOfClassroom.remove(student);
        student.getClassrooms().remove(this);
    }

    /**
     * Adds a meeting date to the classroom.
     *
     * @param meetingDate the meeting date to add
     */

    public void addMeetingDate(MeetingDate meetingDate) {
        meetingDates.add(meetingDate);
        meetingDate.setClassroom(this);
    }

    /**
     * Removes a meeting date from the classroom.
     *
     * @param meetingDate the meeting date to remove
     */

    public void removeMeetingDate(MeetingDate meetingDate) {
        meetingDates.remove(meetingDate);
        meetingDate.setClassroom(null);
    }

    /**
     * Returns the list of students in the classroom.
     *
     * @return the list of students
     */

    public List<Student> getStudents(){
        return studentsOfClassroom;
    }
}
