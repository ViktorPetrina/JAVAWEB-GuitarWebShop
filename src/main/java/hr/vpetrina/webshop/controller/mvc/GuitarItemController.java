package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.model.GuitarCategory;
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

    private static final String LIST_GUITARS = "listGuitars";
    private static final String GUITAR_ATTRIBUTE_KEY = "guitar";
    private static final String GUITARS_ATTRIBUTE_KEY = "guitars";
    private static final String CATEGORIES_ATTRIBUTE_KEY = "categories";

    private final GuitarService guitarService;
    private final CategoryService categoryService;

    @GetMapping("/mainPage")
    public String showMainPageList(
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "search", required = false) String search,
            Model model
    ) {
        List<GuitarItemDto> guitars = guitarService.getFilteredGuitars(categoryId, search);
        List<GuitarCategory> categories = categoryService.getAll();

        model.addAttribute(GUITARS_ATTRIBUTE_KEY, guitars);
        model.addAttribute(CATEGORIES_ATTRIBUTE_KEY, categories);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("searchQuery", search);

        return "mainPage";
    }

    @GetMapping("/list")
    public String listGuitars(Model model) {
        model.addAttribute(GUITARS_ATTRIBUTE_KEY, guitarService.getAll());
        return LIST_GUITARS;
    }

    @GetMapping("/details/{id}")
    public String guitarDetails(@PathVariable String id, Model model) {
        Optional<GuitarItemDto> guitar = guitarService.getById(Integer.parseInt(id));

        if(guitar.isEmpty()) {
            return "redirect:/GuitarStore/guitars/list";
        }

        model.addAttribute(GUITAR_ATTRIBUTE_KEY, guitar.get());

        return "detailsGuitar";
    }

    @GetMapping("/add")
    public String addGuitar(Model model) {
        model.addAttribute(GUITAR_ATTRIBUTE_KEY, new GuitarItemDto());
        model.addAttribute(CATEGORIES_ATTRIBUTE_KEY, categoryService.getAll());
        return "addGuitar";
    }

    @PostMapping("/add")
    public String addGuitar(@ModelAttribute GuitarItemDto guitarItemDto) {
        guitarService.insert(guitarItemDto);
        return "redirect:/GuitarStore/guitars/list";
    }

    @GetMapping("/edit/{id}")
    public String editGuitar(@PathVariable Integer id, Model model) {
        model.addAttribute(GUITAR_ATTRIBUTE_KEY, guitarService.getById(id));
        model.addAttribute(CATEGORIES_ATTRIBUTE_KEY, categoryService.getAll());
        return "editGuitar";
    }

    @PostMapping("/update")
    public String updateGuitar(@ModelAttribute GuitarItemDto guitar, Model model) {
        guitarService.update(guitar.getId(), guitar);
        model.addAttribute(GUITARS_ATTRIBUTE_KEY, guitarService.getAll());
        return LIST_GUITARS;
    }

    @GetMapping("/delete/{id}")
    public String deleteGuitar(@PathVariable Integer id, Model model) {
        guitarService.delete(id);
        model.addAttribute(GUITARS_ATTRIBUTE_KEY, guitarService.getAll());
        return LIST_GUITARS;
    }
}