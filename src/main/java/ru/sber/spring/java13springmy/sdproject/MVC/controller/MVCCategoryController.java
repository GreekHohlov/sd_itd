package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.CategoryMapper;
import ru.sber.spring.java13springmy.sdproject.service.CategoryService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("category")
public class MVCCategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    public MVCCategoryController(CategoryService categoryService,
                                 CategoryMapper categoryMapper){
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("")
    public String getAll(Model model){
        List<CategoryDTO> categoryDTOS = categoryService.listAll();
        model.addAttribute("category", categoryDTOS);
        return "category/viewAllCategory";
    }

    @GetMapping("/add")
    public String create(Model model){
        List<CategoryDTO> categoryDTO = categoryService.listAll();
        model.addAttribute("categoryForm", categoryDTO);
        return "category/addCategory";
    }
    @PostMapping("add")
    public String create(@ModelAttribute("categoryForm") CategoryDTO categoryDTO){
        categoryService.create(categoryDTO);
        return "redirect:category";
    }
}
