package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
}
