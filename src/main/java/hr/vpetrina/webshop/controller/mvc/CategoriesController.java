package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "listCategories";
    }


    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new GuitarCategory());
        return "addCategory";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute GuitarCategory dto, Model model) {
        categoryService.insert(dto);
        model.addAttribute("categories", categoryService.getAll());
        return "redirect:/GuitarStore/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        if (categoryService.hasGuitars(id)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Cannot delete category: it is assigned to one or more guitars."
            );
        } else {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Category deleted successfully."
            );
        }

        model.addAttribute("categories", categoryService.getAll());
        return "redirect:/GuitarStore/categories/list";
    }
}
