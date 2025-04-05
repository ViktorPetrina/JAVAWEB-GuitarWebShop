package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.UserLoginDto;
import hr.vpetrina.webshop.model.UserLogin;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UserLoginService {
    List<UserLoginDto> getAll();
    Optional<UserLoginDto> getById(Integer id);
    UserLoginDto insert(UserLoginDto userLogin);
    String getIpAddress(HttpServletRequest request);
}
