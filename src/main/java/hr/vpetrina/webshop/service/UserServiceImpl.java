package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.model.UserRole;
import hr.vpetrina.webshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(UserRole.REGULAR);

        return userRepository.insert(user);
    }

    @Override
    public Optional<User> loginUser(String username, String password) {
        var user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
