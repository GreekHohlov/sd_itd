package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.service.LocationService;

import java.util.List;

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
    //Рисует форму создания
    @GetMapping("/add")
    public String create() {
        return "locations/addLocation";
    }

    // Примит данные о созданном *** и передаст в БД
    // Потом вернёт нас на страницу со всеми ***
    @PostMapping("/add")
    public String create(@ModelAttribute("locationForm") LocationDTO locationDTO) {
        locationService.create(locationDTO);
        return "redirect:/locations";
    }
}
