package gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateMeetingDateDTO {

    private Long id;

    @NotNull(message = "Date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Time cannot be null")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public Collection<Object> getMeetingDate() {
        return null;
    }
}
