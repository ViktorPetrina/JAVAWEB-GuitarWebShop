package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.UserLogin;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository {
    List<UserLogin> getAll();
    Optional<UserLogin> getById(Integer id);
    UserLogin insert(UserLogin userLogin);
}
