package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.dto.UserPurchaseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(User user);
    Optional<User> loginUser(String username, String password, HttpServletResponse response);
    void logoutUser(HttpServletResponse response);
    Boolean isLoggedIn(HttpServletRequest request, HttpSession session);
    List<UserPurchaseDto> getShoppingHistory(Integer userId);
    void insertPurchase(UserPurchaseDto purchase);
}
