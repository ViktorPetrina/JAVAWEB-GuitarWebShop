package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.dto.UserPurchaseDto;
import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/history")
public class PurchaseHistoryController {

    private final UserService userService;

    @GetMapping("/show")
    public String getPurchaseHistory(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        List<UserPurchaseDto> purchases = userService.getShoppingHistory(user.getId());
        model.addAttribute("purchases", purchases);

        return "purchase-history";
    }
}
