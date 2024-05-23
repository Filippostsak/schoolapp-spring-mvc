package gr.aueb.cf.schoolappspringbootmvc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"creator", "teachers", "studentsOfClassroom", "meetingDates", "extraTeachers"})
public class Classroom extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "classroom_url")
    private String classroomUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Teacher creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "classroom_teachers",
            joinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    )
    private List<Teacher> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> studentsOfClassroom = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingDate> meetingDates = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "extra_classroom_teachers",
            joinColumns = @JoinColumn(name = "classroom_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    )
    private List<Teacher> extraTeachers = new ArrayList<>();

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            teacher.getClassrooms().add(this);
        }
    }

    public void removeTeacher(Teacher teacher) {
        if (teachers.contains(teacher)) {
            teachers.remove(teacher);
            teacher.getClassrooms().remove(this);
        }
    }

    public void addExtraTeacher(Teacher teacher) {
        if (!extraTeachers.contains(teacher)) {
            extraTeachers.add(teacher);
            teacher.getExtraClassrooms().add(this);
        }
    }

    public void removeExtraTeacher(Teacher teacher) {
        if (extraTeachers.contains(teacher)) {
            extraTeachers.remove(teacher);
            teacher.getExtraClassrooms().remove(this);
        }
    }

    public List<Student> getStudentsOfClassroom() {
        return studentsOfClassroom;
    }

    public void addStudent(Student student) {
        studentsOfClassroom.add(student);
        student.setClassroom(this);
    }

    public void removeStudent(Student student) {
        studentsOfClassroom.remove(student);
        student.setClassroom(null);
    }

    public void addMeetingDate(MeetingDate meetingDate) {
        meetingDates.add(meetingDate);
        meetingDate.setClassroom(this);
    }

    public void removeMeetingDate(MeetingDate meetingDate) {
        meetingDates.remove(meetingDate);
        meetingDate.setClassroom(null);
    }
}
