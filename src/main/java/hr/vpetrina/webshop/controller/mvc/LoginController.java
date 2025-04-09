package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.model.LoginRequest;
import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.service.UserLoginService;
import hr.vpetrina.webshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore")
public class LoginController {

    private final UserService userService;
    private final UserLoginService userLoginService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute LoginRequest loginRequest,
            Model model,
            HttpSession session,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        try {
            User user = userService.loginUser(
                    loginRequest.getUsername(),
                    loginRequest.getPassword(),
                    response
            );
            session.setAttribute("user", user);
            return "redirect:/GuitarStore/guitars/mainPage";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpSession session, HttpServletResponse response) {
        userService.logoutUser(response);
        session.removeAttribute("user");
        return "redirect:/GuitarStore/guitars/mainPage";
    }
}
