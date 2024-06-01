package gr.aueb.cf.schoolappspringbootmvc.dto.classroom;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.FindMeetingMeetingDateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassroomFindMeetingsDTO {

    private Long id;

    private String name;

    private String description;

    private String classroomUrl;

    private String imageUrl;

    private List<FindMeetingMeetingDateDTO> meetingDates;
}
