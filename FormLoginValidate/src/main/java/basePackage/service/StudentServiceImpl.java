package basePackage.service;

import basePackage.model.Student;
import basePackage.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Student findById(Long id) {
        return studentRepository.findOne(id);
    }

    public void delete(Long id) {
        studentRepository.delete(id);
    }

    public void save(Student student) {
        studentRepository.save(student);
    }
}
