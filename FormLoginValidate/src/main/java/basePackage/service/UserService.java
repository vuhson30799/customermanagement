package basePackage.service;

import basePackage.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    User findById(Long id);
    void delete(Long id);
    void save(User user);
}
