package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.ClassroomMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.MeetingDateMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.repository.ClassroomRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.MeetingDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link IMeetingDateService} interface.
 * Provides methods for creating, updating and deleting meeting dates.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingDateServiceImpl implements IMeetingDateService {


    /**
     * The repository for managing meeting dates.
     */
    private final MeetingDateRepository meetingDateRepository;

    /**
     * The repository for managing classrooms.
     */

    private final ClassroomRepository classroomRepository;

    /**
     * The service for managing teachers.
     */
    private final ITeacherService teacherService;

    /**
     * The mapper for converting between meeting date data transfer objects and entities.
     */
    private final MeetingDateMapper meetingDateMapper;

    /**
     * The mapper for converting between classroom data transfer objects and entities.
     */
    private final ClassroomMapper classroomMapper;

    /**
     * Creates a meeting date.
     *
     * @param classroomId the id of the classroom.
     * @param createMeetingDateDTO the data for creating the meeting date.
     * @return the created meeting date.
     */

    @Override
    @Transactional
    public MeetingDate createMeetingDate(Long classroomId, CreateMeetingDateDTO createMeetingDateDTO) {
        Teacher currentTeacher = teacherService.getCurrentAuthenticatedTeacher()
                .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        if (!classroom.getCreator().equals(currentTeacher)) {
            throw new RuntimeException("Teacher not authorized to create meeting dates for this classroom");
        }
        MeetingDate meetingDate = classroomMapper.toMeetingDate(createMeetingDateDTO);
        meetingDate.setClassroom(classroom);

        return meetingDateRepository.save(meetingDate);
    }

    /**
     * Deletes a meeting date.
     *
     * @param meetingDateId the id of the meeting date.
     */

    @Override
    @Transactional
    public void deleteMeetingDate(Long meetingDateId) {
       MeetingDate meetingDate = meetingDateRepository.findById(meetingDateId)
               .orElseThrow(() -> new RuntimeException("Meeting date not found"));
       meetingDateRepository.delete(meetingDate);
    }

    /**
     * Updates a meeting date.
     *
     * @param classroomId the id of the classroom.
     * @param meetingDateId the id of the meeting date.
     * @param dto the data for updating the meeting date.
     * @return the updated meeting date.
     */

    @Override
    public MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO dto) {
        MeetingDate meetingDate = meetingDateRepository.findById(meetingDateId)
                .orElseThrow(() -> new RuntimeException("Meeting date not found"));
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        if (!meetingDate.getClassroom().equals(classroom)) {
            throw new RuntimeException("Meeting date does not belong to the specified classroom");
        }
        meetingDateMapper.updateMeetingDateFromDTO(dto, meetingDate);
        meetingDate.setClassroom(classroom);
        return meetingDateRepository.save(meetingDate);
    }

}
