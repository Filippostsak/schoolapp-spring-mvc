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

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingDateServiceImpl implements IMeetingDateService {

    private final MeetingDateRepository meetingDateRepository;
    private final ClassroomRepository classroomRepository;
    private final ITeacherService teacherService;
    private final MeetingDateMapper meetingDateMapper;
    private final ClassroomMapper classroomMapper;

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

    @Override
    @Transactional
    public void deleteMeetingDate(Long meetingDateId) {
       MeetingDate meetingDate = meetingDateRepository.findById(meetingDateId)
               .orElseThrow(() -> new RuntimeException("Meeting date not found"));
       meetingDateRepository.delete(meetingDate);
    }

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
