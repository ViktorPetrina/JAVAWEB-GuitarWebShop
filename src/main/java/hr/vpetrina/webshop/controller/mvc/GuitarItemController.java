package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.repository.CategoryRepository;
import hr.vpetrina.webshop.service.CategoryService;
import hr.vpetrina.webshop.service.GuitarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/guitars")
public class GuitarItemController {

    private final GuitarService guitarService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String listGuitars(
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "search", required = false) String search,
            Model model
    ) {
        List<GuitarItemDto> guitars = guitarService.getFilteredGuitars(categoryId, search);
        List<GuitarCategory> categories = categoryService.getAll();

        model.addAttribute("guitars", guitars);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("searchQuery", search);

        return "guitarList";
    }


    @GetMapping("/details/{id}")
    public String guitarDetails(@PathVariable String id, Model model) {
        Optional<GuitarItemDto> guitar = guitarService.getById(Integer.parseInt(id));

        if(guitar.isEmpty()) {
            return "redirect:/GuitarStore/guitars/list";
        }

        model.addAttribute("guitar", guitar.get());

        return "guitarDetails";
    }

    @GetMapping("/add")
    public String addGuitar(Model model) {
        model.addAttribute("guitar", new GuitarItemDto());
        return "addGuitar";
    }

    /*@PostMapping("/add")
    public String addGuitar(@ModelAttribute GuitarItemDto guitarItemDto) {

    }*/
}