package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    boolean existsByName(String name);
    Page<Classroom> findByTeachers_Id(Long teacherId, Pageable pageable);
    Optional<Classroom> findById(Long classroomId);
    List<Classroom> findByCreatorOrTeachersContaining(Teacher creator, Teacher teacher);
    boolean existsByIdAndStudentsOfClassroom_Id(Long classroomId, Long studentId);
}
