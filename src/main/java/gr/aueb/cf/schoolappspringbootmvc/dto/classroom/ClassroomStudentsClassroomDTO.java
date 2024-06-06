package gr.aueb.cf.schoolappspringbootmvc.dto.classroom;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingDateReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.TeacherReadOnlyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "A classroom entity with associated students.")
public class ClassroomStudentsClassroomDTO {

    /**
     * The unique identifier of the classroom.
     */

    @Schema(description = "The unique identifier of the classroom.")
    private Long id;

    /**
     * The name of the classroom.
     */

    @Schema(description = "The name of the classroom.")
    private String name;

    /**
     * A description of the classroom.
     */

    @Schema(description = "A description of the classroom.")
    private String description;

    /**
     * The URL of the classroom.
     */

    @Schema(description = "The URL of the classroom.")
    private String classroomUrl;

    /**
     * The image URL of the classroom.
     */

    @Schema(description = "The image URL of the classroom.")
    private String imageUrl;

    /**
     * Indicates whether the classroom is active.
     */

    @Schema(description = "Indicates whether the classroom is active.")
    private boolean isActive;

    /**
     * The creator of the classroom.
     */

    @Schema(description = "The creator of the classroom.")
    private TeacherReadOnlyDTO creator;

    /**
     * A list of teachers associated with the classroom.
     */

    @Schema(description = "A list of teachers associated with the classroom.")
    private List<TeacherReadOnlyDTO> teachers;

    /**
     * A list of students enrolled in the classroom.
     */

    @Schema(description = "A list of students enrolled in the classroom.")
    private List<StudentReadOnlyDTO> studentsOfClassroom;

    /**
     * A list of meeting dates associated with the classroom.
     */

    @Schema(description = "A list of meeting dates associated with the classroom.")
    private List<MeetingDateReadOnlyDTO> meetingDates;

    /**
     * A list of extra teachers associated with the classroom.
     */

    @Schema(description = "A list of extra teachers associated with the classroom.")
    private List<TeacherReadOnlyDTO> extraTeachers;
}

