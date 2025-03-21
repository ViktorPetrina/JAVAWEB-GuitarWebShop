package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.service.GuitarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("mvc/guitars")
public class GuitarItemMvcController {

    private final GuitarService guitarService;

    @GetMapping("/list")
    public String listGuitars(
            @RequestParam(name = "category", required = false) GuitarCategory category,
            @RequestParam(name = "search", required = false) String search,
            Model model
    ) {
        List<GuitarItemDto> guitars = guitarService.getFilteredGuitars(category, search);

        model.addAttribute("guitars", guitars);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchQuery", search);
        return "listGuitars";
    }
}