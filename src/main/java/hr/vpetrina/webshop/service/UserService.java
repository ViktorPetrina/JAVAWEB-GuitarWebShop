package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.dto.UserPurchaseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.util.List;

public interface UserService {
    List<User> getAll();
    void registerUser(User user);
    User loginUser(String username, String password, HttpServletResponse response);
    void logoutUser(HttpServletResponse response);
    Boolean isLoggedIn(HttpServletRequest request, HttpSession session);
    List<UserPurchaseDto> getShoppingHistory(Integer userId);
    List<UserPurchaseDto> getAllShoppingHistory();
    List<UserPurchaseDto> getFilteredShoppingHistory(Integer userId, Date startDate, Date endDate);
    void insertPurchase(UserPurchaseDto purchase);
}
