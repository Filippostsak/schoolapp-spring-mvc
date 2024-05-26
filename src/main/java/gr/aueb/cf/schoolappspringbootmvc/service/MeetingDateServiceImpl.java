package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.ClassroomMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.repository.ClassroomRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.MeetingDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingDateServiceImpl implements IMeetingDateService {

    private final MeetingDateRepository meetingDateRepository;
    private final ClassroomRepository classroomRepository;
    private final ITeacherService teacherService;
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
    public MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, MeetingUpdateDTO meetingUpdateDTO) {
        Teacher currentTeacher = teacherService.getCurrentAuthenticatedTeacher()
                .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (!classroom.getCreator().equals(currentTeacher)) {
            throw new RuntimeException("Teacher not authorized to update meeting dates for this classroom");
        }

        MeetingDate meetingDate = meetingDateRepository.findById(meetingDateId)
                .orElseThrow(() -> new RuntimeException("Meeting date not found"));

        classroomMapper.updateMeetingDateFromDTO(meetingUpdateDTO, meetingDate);

        return meetingDateRepository.save(meetingDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingDate> findMeetingDatesByClassroomId(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        return classroom.getMeetingDates();
    }
}
