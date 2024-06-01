package gr.aueb.cf.schoolappspringbootmvc.dto;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingDateReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.TeacherReadOnlyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassroomReadOnlyDTO {

    private Long id;
    private String name;
    private String description;
    private String classroomUrl;
    private String imageUrl;
    private boolean isActive;
    private TeacherReadOnlyDTO creator;
    private List<TeacherReadOnlyDTO> teachers;
    private List<StudentReadOnlyDTO> studentsOfClassroom;
    private List<MeetingDateReadOnlyDTO> meetingDates;
    private List<TeacherReadOnlyDTO> extraTeachers;
}
