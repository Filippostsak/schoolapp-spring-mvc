package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;

/**
 * Service for managing meeting dates.
 */

public interface IMeetingDateService {

    /**
     * Creates a meeting date.
     *
     * @param classroomId the id of the classroom.
     * @param createMeetingDateDTO the data for creating the meeting date.
     * @return the created meeting date.
     */

    MeetingDate createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO);

    /**
     * Deletes a meeting date.
     *
     * @param meetingDateId the id of the meeting date.
     */

    void deleteMeetingDate(Long meetingDateId);

    /**
     * Updates a meeting date.
     *
     * @param classroomId the id of the classroom.
     * @param meetingDateId the id of the meeting date.
     * @param dto the data for updating the meeting date.
     * @return the updated meeting date.
     */

    MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto);

}
