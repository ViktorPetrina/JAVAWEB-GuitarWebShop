package hr.vpetrina.webshop.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mvc/guitars")
public class GuitarItemMvcController {

    @GetMapping("/list")
    public String listGuitars(Model model) {
        return "listGuitars";
    }
}
