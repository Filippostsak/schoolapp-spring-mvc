package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingDateReadOnlyDTO {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private LocalDate endDate;
    private LocalTime endTime;
}
