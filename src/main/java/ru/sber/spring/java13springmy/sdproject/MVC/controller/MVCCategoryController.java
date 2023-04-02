package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.service.CategoryService;

import java.util.List;

@Hidden
@Controller
@RequestMapping("category")
public class MVCCategoryController {

    private final CategoryService categoryService;

    public MVCCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<CategoryDTO> categoryDTOS = categoryService.listAll();
        model.addAttribute("category", categoryDTOS);
        return "category/viewAllCategory";
    }

    @GetMapping("/add")
    public String create(Model model) {
        List<CategoryDTO> categoryDTO = categoryService.listAll();
        model.addAttribute("categoryForm", categoryDTO);
        return "category/addCategory";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("categoryForm") CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return "redirect:/category";
    }

    @GetMapping("/deleteSoft/{id}")
    public String deleteSoft(@PathVariable Long id) throws MyDeleteException {
        categoryService.deleteSoft(id);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        categoryService.delete(id);
        return "redirect:/category";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        categoryService.restore(id);
        return "redirect:/category";
    }
}
