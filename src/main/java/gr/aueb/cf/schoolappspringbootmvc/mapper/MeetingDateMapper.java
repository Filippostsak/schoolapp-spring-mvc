package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.FindMeetingMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import org.springframework.stereotype.Component;

@Component
public class MeetingDateMapper {

    public void updateMeetingDateFromDTO(UpdateMeetingDateDTO dto, MeetingDate meetingDate) {
        meetingDate.setDate(dto.getDate());
        meetingDate.setTime(dto.getTime());
        meetingDate.setEndDate(dto.getEndDate());
        meetingDate.setEndTime(dto.getEndTime());
    }


    public FindMeetingMeetingDateDTO toMeetingDateFindMeetingsDTO(MeetingDate meetingDate) {
        FindMeetingMeetingDateDTO dto = new FindMeetingMeetingDateDTO();
        dto.setId(meetingDate.getId());
        dto.setDate(meetingDate.getDate());
        dto.setTime(meetingDate.getTime());
        dto.setEndTime(meetingDate.getEndTime());
        dto.setEndDate(meetingDate.getEndDate());
        return dto;
    }
}
