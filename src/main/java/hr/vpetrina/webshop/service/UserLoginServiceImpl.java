package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.UserLoginDto;
import hr.vpetrina.webshop.model.UserLogin;
import hr.vpetrina.webshop.repository.UserLoginRepository;
import hr.vpetrina.webshop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserLoginDto> getAll() {
        return userLoginRepository.getAll().stream().map(this::toDto).toList();
    }

    @Override
    public Optional<UserLoginDto> getById(Integer id) {
        var opt = userLoginRepository.getById(id);
        return opt.map(this::toDto);
    }

    @Override
    public UserLoginDto insert(UserLoginDto userLogin) {
        userLoginRepository.insert(toEntity(userLogin));
        return userLogin;
    }

    @Override
    public String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    private UserLoginDto toDto(UserLogin userLogin) {
        var user = userRepository.findById(userLogin.getId());

        if (user.isPresent()) {
            return new UserLoginDto(
                    userLogin.getId(),
                    user.get(),
                    userLogin.getLoginDate(),
                    userLogin.getIpAddress()
            );
        }

        return null;
    }

    private UserLogin toEntity(UserLoginDto userLoginDto) {
        return new UserLogin(
                userLoginDto.getId(),
                userLoginDto.getUser().getId(),
                userLoginDto.getLoginDate(),
                userLoginDto.getIpAddress()
        );
    }
}
