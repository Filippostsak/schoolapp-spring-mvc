package gr.aueb.cf.schoolappspringbootmvc.repository;

import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findByTeachers_Id (Long teacherId);
    List<Classroom> findByStudentsOfClassroom_Id(Long studentId);
    boolean existsByName(String name);
    Page<Classroom> findByTeachers_Id(Long teacherId, Pageable pageable);

}
