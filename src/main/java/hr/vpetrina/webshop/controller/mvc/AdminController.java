package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.service.UserLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/admin")
public class AdminController {

    private final UserLoginService userLoginService;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin";
    }

    @GetMapping("/logIns")
    public String showUserLogIns(Model model) {
        model.addAttribute("userLogins", userLoginService.getAll());
        return "userLoggins";
    }
}
