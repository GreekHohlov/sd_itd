package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;
@Hidden
@Controller
@Slf4j
public class MVCErrorController
        implements ErrorController {
    //TODO:  При активации сыпит ошибку 404, хотя всё работает

        @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest,
                              Model model) {
        log.error("Случилась беда! Ошибка {}",
                httpServletRequest.getAttribute(ERROR_STATUS_CODE));
        model.addAttribute("exception",
                "Ошибка " + httpServletRequest.getAttribute(ERROR_STATUS_CODE) + " в маппинге " +
                        httpServletRequest.getAttribute(ERROR_REQUEST_URI));
        return "error";
    }
}
