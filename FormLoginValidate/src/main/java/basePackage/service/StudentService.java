package basePackage.service;

import basePackage.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Page<Student> findAll(Pageable pageable);
    Student findById(Long id);
    void delete(Long id);
    void save(Student student);
}
