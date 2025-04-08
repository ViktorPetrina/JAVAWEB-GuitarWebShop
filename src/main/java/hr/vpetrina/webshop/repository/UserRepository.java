package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    User insert(User user);
    Optional<User> update(Integer id, User user);
    void delete(Integer id);
    List<User> getAll();
}
