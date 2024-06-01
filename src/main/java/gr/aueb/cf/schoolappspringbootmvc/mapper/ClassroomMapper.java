package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.ClassroomReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomFindMeetingsDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.CreateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingDateReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component

public class ClassroomMapper {

    @Autowired
    private TeacherRepository teacherRepository;

    public Classroom toClassroom(CreateClassroomDTO dto, Long creatorId) {
        Classroom classroom = new Classroom();
        classroom.setName(dto.getName());
        classroom.setDescription(dto.getDescription());
        classroom.setClassroomUrl(dto.getClassroomUrl());
        classroom.setImageUrl(dto.getImageUrl());
        classroom.setIsActive(dto.isActive());

        // Set creator
        Optional<Teacher> creatorOpt = teacherRepository.findById(creatorId);
        if (creatorOpt.isPresent()) {
            classroom.setCreator(creatorOpt.get());
        } else {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        return classroom;
    }

    public void updateClassroomFromDTO(ClassroomUpdateDTO dto, Classroom classroom) {
        classroom.setName(dto.getName());
        classroom.setDescription(dto.getDescription());
        classroom.setClassroomUrl(dto.getClassroomUrl());
        classroom.setImageUrl(dto.getImageUrl());
        classroom.setActive(dto.isActive());
    }

    public MeetingDate toMeetingDate(CreateMeetingDateDTO dto) {
        MeetingDate meetingDate = new MeetingDate();
        meetingDate.setDate(dto.getDate());
        meetingDate.setTime(dto.getTime());
        meetingDate.setEndTime(dto.getEndTime());
        meetingDate.setEndDate(dto.getEndDate());
        return meetingDate;
    }

    public ClassroomFindMeetingsDTO toClassroomFindMeetingsDTO(Classroom classroom) {
        ClassroomFindMeetingsDTO dto = new ClassroomFindMeetingsDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());
        dto.setDescription(classroom.getDescription());
        dto.setClassroomUrl(classroom.getClassroomUrl());
        dto.setImageUrl(classroom.getImageUrl());
        return dto;
    }

    public static ClassroomReadOnlyDTO toReadOnlyDTO(Classroom classroom) {
        ClassroomReadOnlyDTO dto = new ClassroomReadOnlyDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());
        dto.setDescription(classroom.getDescription());
        dto.setClassroomUrl(classroom.getClassroomUrl());
        dto.setImageUrl(classroom.getImageUrl());
        dto.setCreator(new TeacherReadOnlyDTO(
                classroom.getCreator().getId(),
                classroom.getCreator().getFirstname(),
                classroom.getCreator().getLastname(),
                classroom.getCreator().getUser().getEmail(),
                classroom.getCreator().getUser().getUsername()
        ));
        dto.setTeachers(classroom.getTeachers().stream().map(teacher -> new TeacherReadOnlyDTO(
                teacher.getId(),
                teacher.getFirstname(),
                teacher.getLastname(),
                teacher.getUser().getEmail(),
                teacher.getUser().getUsername()
        )).collect(Collectors.toList()));
        dto.setStudentsOfClassroom(classroom.getStudentsOfClassroom().stream().map(student -> new StudentReadOnlyDTO(
                student.getId(),
                student.getFirstname(),
                student.getLastname(),
                student.getUser().getEmail(),
                student.getUser().getCountry(),
                student.getUser().getCity()
        )).collect(Collectors.toList()));
        dto.setMeetingDates(classroom.getMeetingDates().stream().map(meetingDate -> new MeetingDateReadOnlyDTO(
                meetingDate.getId(),
                meetingDate.getDate(),
                meetingDate.getTime(),
                meetingDate.getEndDate(),
                meetingDate.getEndTime()
        )).collect(Collectors.toList()));
        dto.setExtraTeachers(classroom.getExtraTeachers().stream().map(teacher -> new TeacherReadOnlyDTO(
                teacher.getId(),
                teacher.getFirstname(),
                teacher.getLastname(),
                teacher.getUser().getEmail(),
                teacher.getUser().getUsername()
        )).collect(Collectors.toList()));
        return dto;
    }
}
