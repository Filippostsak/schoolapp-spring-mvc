package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.FindMeetingMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import org.springframework.stereotype.Component;
/**
 * Mapper class for converting between MeetingDate-related DTOs and entities.
 */

@Component
public class MeetingDateMapper {

    /**
     * Converts a CreateMeetingDateDTO to a MeetingDate entity.
     *
     * @param dto the CreateMeetingDateDTO
     * @return the MeetingDate entity
     */

    public void updateMeetingDateFromDTO(UpdateMeetingDateDTO dto, MeetingDate meetingDate) {
        meetingDate.setDate(dto.getDate());
        meetingDate.setTime(dto.getTime());
        meetingDate.setEndDate(dto.getEndDate());
        meetingDate.setEndTime(dto.getEndTime());
    }

    /**
     * Converts a MeetingDate entity to a FindMeetingMeetingDateDTO.
     *
     * @param meetingDate the MeetingDate entity
     * @return the FindMeetingMeetingDateDTO
     */


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
