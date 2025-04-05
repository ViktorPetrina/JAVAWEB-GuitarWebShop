package hr.vpetrina.webshop.controller.mvc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin";
    }

}
