package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;


public interface IMeetingDateService {
    MeetingDate createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO);
    void deleteMeetingDate(Long meetingDateId);
    MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto);

}
