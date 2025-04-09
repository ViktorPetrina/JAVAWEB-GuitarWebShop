package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.dto.UserPurchaseDto;
import hr.vpetrina.webshop.service.UserLoginService;
import hr.vpetrina.webshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/admin")
public class AdminController {

    private final UserLoginService userLoginService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session) {
        return "admin";
    }

    @GetMapping("/logIns")
    public String showUserLogIns(Model model) {
        model.addAttribute("userLogins", userLoginService.getAll());
        return "listLogin";
    }

    @GetMapping("/orders")
    public String showOrders(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  endDate,
            Model model
    ) {
        Date sqlStartDate = (startDate != null) ? Date.valueOf(startDate) : null;
        Date sqlEndDate = (endDate != null) ? Date.valueOf(endDate) : null;

        List<UserPurchaseDto> purchases = userService.getFilteredShoppingHistory(userId, sqlStartDate, sqlEndDate);

        model.addAttribute("users", userService.getAll());
        model.addAttribute("purchases", purchases);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "purchase-history-admin";
    }
}
