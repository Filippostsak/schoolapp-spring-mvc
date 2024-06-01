package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FindMeetingMeetingDateDTO {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
