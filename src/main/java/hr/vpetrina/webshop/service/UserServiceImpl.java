package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.dto.UserPurchaseDto;
import hr.vpetrina.webshop.model.*;
import hr.vpetrina.webshop.repository.CategoryRepository;
import hr.vpetrina.webshop.repository.GuitarRepository;
import hr.vpetrina.webshop.repository.UserPurchaseRepository;
import hr.vpetrina.webshop.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final GuitarRepository guitarRepository;
    private final CategoryRepository categoryRepository;
    private final UserPurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(
            GuitarRepository guitarRepository, CategoryRepository categoryRepository,
            UserRepository userRepository,
            UserPurchaseRepository purchaseRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.guitarRepository = guitarRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void registerUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setRole(user.getRole());

        userRepository.insert(user);
    }

    @Override
    public Optional<User> loginUser(String username, String password, HttpServletResponse response) {
        var user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {

            String jwt = jwtService.generateToken(user.get());

            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(jwtService.getExpirationTime());

            response.addCookie(cookie);

            return user;
        }
        return Optional.empty();
    }

    @Override
    public void logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Override
    public Boolean isLoggedIn(HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    var user = (User) session.getAttribute("user");

                    if (user != null) {
                        return jwtService.isTokenValid(token, user);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<UserPurchaseDto> getShoppingHistory(Integer userId) {
        return purchaseRepository.getByUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void insertPurchase(UserPurchaseDto dto) {
        var entity = toEntity(dto);
        purchaseRepository.insert(entity);
    }

    private UserPurchaseDto toDto(UserPurchase purchase) {
        var guitarOpt = guitarRepository.getById(purchase.getGuitarId());
        var user = userRepository.findById(purchase.getUserId());

        if (guitarOpt.isPresent() && user.isPresent()) {

            var guitar = guitarOpt.get();
            Optional<GuitarCategory> category = categoryRepository.getById(guitar.getCategoryId());

            if (!category.isPresent()) {
                return null;
            }

            return new UserPurchaseDto(
                    purchase.getQuantity(),
                    purchase.getTotalPrice(),
                    purchase.getDate(),
                    purchase.getPaymentType(),
                    new GuitarItemDto(
                            guitar.getId(),
                            guitar.getTitle(),
                            guitar.getDescription(),
                            guitar.getPrice(),
                            guitar.getBody(),
                            guitar.getNeck(),
                            guitar.getPickups(),
                            category.get(),
                            guitar.getImageUrl()
                    ),
                    user.get()
            );
        }

        return null;
    }

    private UserPurchase toEntity(UserPurchaseDto dto) {
        return new UserPurchase(
                dto.getQuantity(),
                dto.getTotalPrice(),
                dto.getDate(),
                dto.getPaymentType(),
                dto.getGuitar().getId(),
                dto.getUser().getId()
        );
    }
}
