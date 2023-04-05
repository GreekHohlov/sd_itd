package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Hidden
@Slf4j
@Controller
public class MVCLoginController {
    @GetMapping("/login")
    public String login() {
        if (
                SecurityContextHolder.getContext().getAuthentication() != null &&
                        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        ) {
            return "redirect:/logout";
        } else {
            return "login";
        }
    }
}

