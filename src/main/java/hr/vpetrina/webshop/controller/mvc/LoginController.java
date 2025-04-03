package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.model.LoginRequest;
import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.service.JwtService;
import hr.vpetrina.webshop.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore")
public class LoginController {

    private final UserService userService;
    private final JwtService jwtService;

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
            HttpServletResponse response
    ) {
        Optional<User> userOpt = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (userOpt.isPresent()) {
            String jwt = jwtService.generateToken(userOpt.get());

            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(jwtService.getExpirationTime());

            response.addCookie(cookie);

            session.setAttribute("user", userOpt.get());
            return "redirect:/GuitarStore/guitars/list";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("user");
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/GuitarStore/guitars/list";
    }
}
