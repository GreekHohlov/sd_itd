package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.service.LocationService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("locations")
public class MVCLocationController {
    private final LocationService locationService;

    public MVCLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<LocationDTO> result = locationService.listAll();
        model.addAttribute("locations", result);
        return "locations/viewAllLocation";
    }

    @GetMapping("/add")
    public String create() {
        return "locations/addLocation";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("locationForm") LocationDTO locationDTO) {
        locationService.create(locationDTO);
        return "redirect:/locations";
    }

    @GetMapping("/deleteSoft/{id}")
    public String deleteSoft(@PathVariable Long id) throws MyDeleteException {
        locationService.deleteSoft(id);
        return "redirect:/locations";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        locationService.delete(id);
        return "redirect:/locations";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        locationService.restore(id);
        return "redirect:/locations";
    }
}
