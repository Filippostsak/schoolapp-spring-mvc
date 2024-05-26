package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;

import java.util.List;

public interface IMeetingDateService {
    MeetingDate createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO);
    MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, MeetingUpdateDTO meetingUpdateDTO);
    List<MeetingDate> findMeetingDatesByClassroomId(Long classroomId);
}
